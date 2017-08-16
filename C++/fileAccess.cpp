/*
 * Autors: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 28.2.2016
 * File:  fileAccess.cpp
 * Class for manipulate with input output file
 */

#include <bitset>
#include "fileAccess.h"

FileAccess::FileAccess(string input_name, string outputName){
    openFileForRead(input_name);
    openFileForWrite(outputName);
}

// method open input file
void FileAccess::openFileForRead(string inputFileName) {
    inputStruct = new openFileStructure;
    inputStruct->file = new ifstream(inputFileName, ios::binary | ios::in);
    if  (not ((ifstream *)inputStruct->file)->is_open()){
        throw string("Error, can't open input file" );
    }
    inputStruct->nextNum = ((*(ifstream*)inputStruct->file).get()<<8) + (*(ifstream*)inputStruct->file).get();
    inputStruct->pos =7;
    getBit();
}

// method open output file
void FileAccess::openFileForWrite(string outputFileName) {
    outputStruct = new openFileStructure;
    outputStruct->file = new ofstream(outputFileName, ios::binary | ios::out);
    if  (not ((ofstream *)outputStruct->file)->is_open()){
        throw string("Error, can't open output file" );
    }
}

// method get one byte from input file
int FileAccess::getAsciiChar(){
    int ch = 0;
    for(int x=0;x<8;x++){
        ch = (ch << 1) | getBit();
    }
    return(ch);
}

// method get one bit form input file
int FileAccess::getBit(){
    int res = 0;
    if (((1 << 7) & inputStruct->buffer) == (1 << 7)){
        res = 1;
    } else {
        res = 0;
    }
    inputStruct->pos++;
    inputStruct->buffer = inputStruct->buffer << 1 ;
    if ((inputStruct->pos == 8)){
        inputStruct->buffer = (inputStruct->nextNum>>8)&0x00ff;
        if (eof){
            inputStruct->nextNum =((inputStruct->nextNum<<8)&0xff00) + 0xff;
        }else {
            inputStruct->nextNum = ((inputStruct->nextNum<<8)&0xff00) + (*(ifstream*)inputStruct->file).get();
            if ((((inputStruct->nextNum>>8) & 0x00ff) == 0xff)){
                if (((inputStruct->nextNum & 0x00ff)== 0x00)){
                    if (removeZero){
                        inputStruct->nextNum = (inputStruct->nextNum&0xff00) + (*(ifstream*)inputStruct->file).get();
                    }
                } else if(((inputStruct->nextNum & 0x00ff)== 0x00d9)){
                    endOfImg = true;
                }
            }
            inputStruct->pos = 0;
            if (((*(ifstream*)inputStruct->file).eof())){
                this->eof = true;
            }
        }
    }
    return res;
}

// test if is end of image
bool FileAccess::endOfImage(){
    return endOfImg;
}

// method clear input buffer
void FileAccess::clearBuffer(){
    if (inputStruct->pos != 0){
        inputStruct->buffer = (inputStruct->nextNum>>8)&0x00ff;
        inputStruct->nextNum =((inputStruct->nextNum<<8)&0xff00) + 0x00;
        inputStruct->pos = 0;
    }
}

// method enable remove zero in 0xFF00 from huffman codding
void FileAccess::enableRemoveZero(){
removeZero = true;
}

// method disable remove zero in 0xFF00 from huffman codding
void FileAccess::disableRemoveZero(){
removeZero=false;
}

// method save header and data to output bmp file
void FileAccess::saveOutput(vector <vector<int>> output, uint8_t *header, uint8_t *headerInfo, int headerSize, int headerInfoSize){
    (*(ofstream *) outputStruct->file).write((const char*) header, headerSize);
    (*(ofstream *) outputStruct->file).write((const char*) headerInfo, headerInfoSize);

    unsigned char pixel[4];
    for(unsigned int i = 0; i < output.size(); i++) {
        pixel[0] = (unsigned char) output[i][2];
        pixel[1] = (unsigned char) output[i][1];
        pixel[2] = (unsigned char) output[i][0];
        pixel[3] = 0;
        (*(ofstream *) outputStruct->file).write((const char*) pixel, 3);
    }
    (*(ofstream *) outputStruct->file).close();
}