/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 1.4.2016
 * File:  jpegDecoder.cpp
 * Method read image blocks and create data class.
 */

#ifndef MUL_PROJEKT_JPEGDECODER_H
#define MUL_PROJEKT_JPEGDECODER_H
#include <stdint.h>
#include <bitset>
#include "treeNode.h"

#include "fileAccess.h"
#include "huffmanTable.h"
#include "quantizationTable.h"
#include "baseLineDCT.h"

using namespace std;

class ImageData;

typedef struct {
    QuantizationTable* quanTable[3]; // quantization table
    HuffmanTable* huffTable[3][2];  // huffman table
    BaseLineDCT* baseLineDCT;   // baseline data
    ImageData* imageData;   // image data
} jpegStruct;


class JpegDecoder {
public:
    JpegDecoder(string inputFileName, string outputFileName);
    void decodeJpegFile();
    void saveData();
private:
    jpegStruct *js;
    FileAccess *in_out_file=NULL;
    void readQuantizationTable();
    void readBaselineDCT();
    void readHuffmanTable();
    void readImageData();
    void skipData();

};

#endif //MUL_PROJEKT_JPEGDECODER_H
