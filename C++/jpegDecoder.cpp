/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 1.4.2016
 * File:  jpegDecoder.cpp
 * Method read image blocks.
 */

#include "jpegDecoder.h"
#include "imageData.h"
#include "createBMP.h"

JpegDecoder::JpegDecoder(string inputFileName, string outputFileName){
    in_out_file = new FileAccess(inputFileName,outputFileName);
    js = new jpegStruct;
    for (int x=0;x <3;x++){
        js->quanTable[x] = NULL;
        js->huffTable[x][0] = NULL;
        js->huffTable[x][1] = NULL;
    }
}

// start of decoding input file
void JpegDecoder::decodeJpegFile(){
    if ((in_out_file->getAsciiChar() == 0xFF) // Start Of Image
        && (in_out_file->getAsciiChar() == 0xD8)){
        int code;
        while (not in_out_file->eof){
            if (in_out_file->getAsciiChar() != 0xFF){
                throw string("jpeg parsing error");
            }
            switch (code = in_out_file->getAsciiChar()){
                case 0xC0: readBaselineDCT(); break; // read baseline DCT
                case 0xC2: skipData(); break; //readProgressiveDCT();
                case 0xC4: readHuffmanTable(); break;  // read huffman table
                case 0xDD: skipData(); break; // define restart interval
                case 0xDB: readQuantizationTable(); break; // quantization table
                case 0xDA: readImageData(); break; // start of scan
                case 0xFE: skipData(); break; // commnet skip data
                case 0xD9: break; // end of image
                default: if ((code & 0xF0) == 0xE0) skipData(); else
                    throw string("wrong file header, unexcepted symbol");
            }
        }
    } else {
        throw string("wrong file header, no jpeg format");
    }
}

// method initialize save data to output file
void JpegDecoder::saveData(){
    CreateBMP *bmp = new CreateBMP(js);
    bmp->save(in_out_file, js->imageData->getRgb());
}

// method skip non implement or comment block data
void JpegDecoder::skipData() {
    int length = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    for (int x=2;x<length;x++)
        in_out_file->getAsciiChar();
}

// method read quantization table
void JpegDecoder::readQuantizationTable(){
    QuantizationTable *qt = new QuantizationTable(in_out_file);
    qt->init();
    js->quanTable[qt->getId()] = qt;
}

// method read baseline DCT
void JpegDecoder::readBaselineDCT(){
    js->baseLineDCT = new BaseLineDCT(in_out_file);
    js->baseLineDCT->init();
}

// method read huffman table
void JpegDecoder::readHuffmanTable(){
    HuffmanTable *ht = new HuffmanTable(in_out_file);
    ht->init();
    js->huffTable[ht->getId()][ht->getType()] = ht;
}

// method read image data
void JpegDecoder::readImageData(){
    ImageData *img = new ImageData(in_out_file, js);
    img->init();
    js->imageData = img;
}