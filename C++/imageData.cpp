/*
 * Autors: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 30.4.2016
 * File:  imageData.cpp
 * Class read image data
 */

#include "imageData.h"
#include "baseLineDCT.h"
#include "jpegDecoder.h"
#include <math.h>
#include <stdlib.h>
#include "optargs.h"

#define M_PI 3.14159265358979323846

ImageData::ImageData(FileAccess *input, jpegStruct *jstr) {
    in_out_file = input;
    js = jstr;
}

// read SOS  header
void ImageData::readHeader(){
    int length = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    h.Ns = in_out_file->getAsciiChar();
    data_in.resize((unsigned long)h.Ns);

    data_out = { new bl, new bl, new bl};
    data_out[0]->data.resize((unsigned int)js->baseLineDCT->bs.sample*js->baseLineDCT->bs.nlines,0);
    data_out[1]->data.resize((unsigned int)js->baseLineDCT->bs.sample*js->baseLineDCT->bs.nlines,0);
    data_out[2]->data.resize((unsigned int)js->baseLineDCT->bs.sample*js->baseLineDCT->bs.nlines,0);

    h.Csj = in_out_file->getAsciiChar();
    h.Tdj_Taj = in_out_file->getAsciiChar();
    h.Ss = in_out_file->getAsciiChar();
    h.Se = in_out_file->getAsciiChar();
    h.Ah_Ai = in_out_file->getAsciiChar();
    length -=8;
    for(int x=0; x<length;x++){
        in_out_file->getAsciiChar();
    }
}

// initialize reading data
bool  ImageData::init(){
    readHeader();
    in_out_file->enableRemoveZero();
    vector <int> dc_value_sum((unsigned int)h.Ns,0);
    // same quantization table for Cr and Cb
    if (js->quanTable[2]==NULL)
        js->quanTable[2] = js->quanTable[1];
    // same huffman table for Cr and Cb
    if (js->huffTable[2][0]==NULL){
        js->huffTable[2][0] = js->huffTable[1][0];
        js->huffTable[2][1] = js->huffTable[1][1];
    }
    // find bigger component
    int itemX = 0; // pocet sample na X
    int itemY = 0; // pocet sample na Y
    for (int x=0;x<2;x++){
        if (js->baseLineDCT->bs.components[x].sX > itemX)
            itemX = js->baseLineDCT->bs.components[x].sX;
        if (js->baseLineDCT->bs.components[x].sY > itemY)
            itemY = js->baseLineDCT->bs.components[x].sY;
    }
    int position = 0;
    int col = 0;
    int index = 0;
    // read image data
    while(not in_out_file->endOfImage()){
        decodeData(&dc_value_sum, position, itemX*itemY);
        col+=itemX;
        position += 8*itemX;
        if (col*8==js->baseLineDCT->bs.sample){
            for (int x=0;x<itemY;x++,index += js->baseLineDCT->bs.sample*8);
            position = index;
            col = 0;
        }
    }
    in_out_file->clearBuffer();
    in_out_file->disableRemoveZero();
    return true;
}

// decode data
void ImageData::decodeData(vector<int> *dc_value_sum, int position, int samplingType){
    int dc_length;
    int ac_length;
    vector<int> &dc_sum = *dc_value_sum;
    // for each component
    for(int com=0;com<h.Ns;com++){
        // vertical subsample
        for (int h=0;h<(js->baseLineDCT->bs.components[com].sY);h++){
            // horizontal subsample
            for (int i=0;i<(js->baseLineDCT->bs.components[com].sX);i++){
                vector <int> block(64,0);
                // read DC component
                dc_length = js->huffTable[com][0]->getDecodedInt8();
                dc_sum[com] += readNBits(dc_length);
                // add value and apply quantization matrix
                block[0] = ((dc_sum[com]) * js->quanTable[com]->qt[0]);
                // read 63 AC component
                for (int y=0;y<63;) {
                    ac_length = js->huffTable[com][1]->getDecodedInt8();
                    if ((ac_length == 0x00)){
                        break;
                    }
                    y += ((ac_length>>4)) +1;
                    // read value and apply quantization matrix
                    block[zigZag[y]] = (readNBits(ac_length) * js->quanTable[com]->qt[y]);
                }
                // block IDCT
                invDisCosTransform(block,com, h,i, position, samplingType);
            }
        }
    }
}

// method read N bits from file
int ImageData::readNBits(int nbits){
    int value = 0;
    for (int x=0;x<(nbits & 0x0f);x++){
        value = (value<<1) | in_out_file->getBit();
    }
    nbits = nbits & 15;
    if (value < (1 << (nbits - 1))){
        value += ((-1) << nbits) + 1;
    }
    return value;
}


// inverse discrete cosinus tranformation
vector <unsigned char> ImageData::invDisCosTransform(vector<int> block, int com, int posX, int posY, int position, int samplingType){
    vector <unsigned char> dst(64,0);
    vector <unsigned char> loadedBlock(64,0);
    int loadedBlockIndex = 0;
    float fU;
    float fV;
    float fCos = 0;
    float fSqrt = (float) 0.707106781;
    float sum;
    int pos = position+(posY*8)+(posX*8*js->baseLineDCT->bs.sample );
    float a = 0;
    for(int y = 0; y < 8; y++, pos+=js->baseLineDCT->bs.sample) {
        for(int x = 0; x < 8; x++) {
            sum = 0;
            for(int u = 0; u < 8; u++) {
                for(int v = 0; v < 8; v++) {
                    fU = (u == 0) ? fSqrt : 1;
                    fV = (v == 0) ? fSqrt : 1;
                    fCos = fU * fV * (block[v*8+u]) * (float)cos(((2*x+1)*u*M_PI)/(2*dctQuality)) *
                           (float)cos(((2*y+1)*v*M_PI)/(2*dctQuality));
                    sum += fCos;
                }
            }
            a = ((sum / 4) + 128); // 128 zrejme
            if (com != 0) {
                if(samplingType == 1)
                    data_out[com]->data[pos+x] = (unsigned char)((a < 0) ? 0 : ((a > 0xFF) ? 0xFF : a));
                else
                    loadedBlock[loadedBlockIndex++] = (unsigned char)((a < 0) ? 0 : ((a > 0xFF) ? 0xFF : (unsigned char) a));
            } else {
                data_out[com]->data[pos+x] = (unsigned char)((a < 0) ? 0 : ((a > 0xFF) ? 0xFF : a));
            }
        }
    }

    // in case of chromatic component, do detected subsampling of buffered block
    if(com != 0 && samplingType != 1) {
        if(samplingType == 4)
            subsampling(loadedBlock, com, position+(posY*8)+(posX*8*js->baseLineDCT->bs.sample));
        else
            subsampling2(loadedBlock, com, position+(posY*8)+(posX*8*js->baseLineDCT->bs.sample));
    }

    return dst;
}

// subsampling 8x8 block into 16x16 block
// calculate average value from 4 neighbors pixels, first in horizontal direction then vertical
void ImageData::subsampling(vector<unsigned char> block, int com, int pos) {
    int subsamledBlock[16][16];

    for(int i = 0, sample = 0; i < 16; i+=2) {
        for(int j = 0; j < 16; j+=2) {
            subsamledBlock[i][j] = block[sample++];
        }
    }

    // horizontal averaged pixel
    for(int i = 0; i < 16; i+=2) {
        for(int j = 1; j < 16; j+=2) {
            if(j-2 >= 0 && j+3 < 16)
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j-1] + (unsigned int)subsamledBlock[i][j-2] +
                        (unsigned int)subsamledBlock[i][j+1] + (unsigned int)subsamledBlock[i][j+3]) / 4);
            else if(j-1 < 0 )
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j+1] + (unsigned int)subsamledBlock[i][j+3]) / 2);
            else if(j+1 >= 16)
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j-1] + (unsigned int)subsamledBlock[i][j-2]) / 2);
            else
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j - 1] + (unsigned int)subsamledBlock[i][j + 1]) / 2);
        }
    }

    // vertical averaged pixel
    for(int i = 1; i < 16; i+=2) {
        for(int j = 0; j < 16; j++) {
            if(i-2 >= 0 && i+3 < 16)
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i-1][j] + (unsigned int)subsamledBlock[i-2][j] +
                        (unsigned int)subsamledBlock[i+3][j] + (unsigned int)subsamledBlock[i+1][j]) / 4);
            else if(i-1 < 0 )
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i+1][j] + (unsigned int)subsamledBlock[i+3][j]) / 2);
            else if(i+1 >= 16)
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i-1][j] + (unsigned int)subsamledBlock[i-2][j]) / 2);
            else
                subsamledBlock[i][j] = (unsigned char)(( (unsigned int)subsamledBlock[i-1][j] + (unsigned int)subsamledBlock[i+1][j]) / 2);
        }
    }

    // copy subsampled data into image
    for(int i = 0; i < 16; i++) {
        for(int j = 0; j < 16; j++) {
            data_out[com]->data[pos+j] = (unsigned char)subsamledBlock[i][j];
        }
        pos+=js->baseLineDCT->bs.sample;
    }
}


// subsampling 8x8 block into 16x8 block
// calculate average value from 4 neighbors pixels in horizontal direction
void ImageData::subsampling2(vector<unsigned char> block, int com, int pos) {
    int subsamledBlock[8][16];

    for(int i = 0, sample = 0; i < 8; i++) {
        for(int j = 0; j < 16; j+=2) {
            subsamledBlock[i][j] = block[sample++];
        }
    }

    // horizontal averaged pixel
    for(int i = 0; i < 8; i++) {
        for(int j = 1; j < 16; j+=2) {
            if(j-2 >= 0 && j+3 < 16)
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j-1] + (unsigned int)subsamledBlock[i][j-2] +
                                                        (unsigned int)subsamledBlock[i][j+1] + (unsigned int)subsamledBlock[i][j+3]) / 4);
            else if(j-1 < 0 )
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j+1] + (unsigned int)subsamledBlock[i][j+3]) / 2);
            else if(j+1 >= 16)
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j-1] + (unsigned int)subsamledBlock[i][j-2]) / 2);
            else
                subsamledBlock[i][j] = (unsigned char)(((unsigned int)subsamledBlock[i][j - 1] + (unsigned int)subsamledBlock[i][j + 1]) / 2);
        }
    }

    // copy subsampled data into image
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 16; j++) {
            data_out[com]->data[pos+j] = (unsigned char)subsamledBlock[i][j];
        }
        pos+=js->baseLineDCT->bs.sample;
    }
}


// method convert YCrCb to RGB
vector <vector<int>> ImageData::getRgb(){
    vector <vector<int>> tmp;
    vector <int> rgb(3,0);

    int Y;
    int Cb;
    int Cr;
    double a;
    for(int j = 0; j < (js->baseLineDCT->bs.sample *js->baseLineDCT->bs.nlines); j++) {
        Cb = Cr = 0;
        Y = data_out[0]->data[j];
        if(data_out.size() > 1)
            Cb = data_out[1]->data[j] - 128;
        if(data_out.size() > 2)
            Cr = data_out[2]->data[j] - 128;
        if(grayscaleMode) {
            Cb = 0;
            Cr = 0;
        }
        a = (Y + 1.402 * Cr);
        rgb[0] = (unsigned char)((a < 0) ? 0 : ((a > 0xFF) ? 0xFF : (unsigned char) a));
        a = (Y - 0.34414 * Cb - 0.71414 * Cr);
        rgb[1] = (unsigned char)((a < 0) ? 0 : ((a > 0xFF) ? 0xFF : (unsigned char) a));
        a = (Y + 1.772 * Cb);
        rgb[2] = (unsigned char)((a < 0) ? 0 : ((a > 0xFF) ? 0xFF : (unsigned char) a));
        tmp.push_back(rgb);
    }
    return tmp;
}
