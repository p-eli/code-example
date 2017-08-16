/*
 * Autors: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 4.5.2016
 * File:  createBMP.h
 * Method save rgb as bmp file
 */

#ifndef MUL_PROJEKT_CREATEBMP_H
#define MUL_PROJEKT_CREATEBMP_H

#include "jpegDecoder.h"

class CreateBMP {
public:
    CreateBMP(jpegStruct *js);
    void save(FileAccess *file, vector <vector<int>> rgbData);
private:
    jpegStruct *js;

};

struct BitMapFileHeader {
    int16_t bfType;
    int32_t bfSize;
    int16_t bfReserved1;
    int16_t bfReserved2;
    int32_t bfOffBits;
};

struct BitMapFileInfoHeader {
    int32_t biSize;
    int32_t biWidth;
    int32_t biHeight;
    int16_t biPlanes;
    int16_t biBitCount;
    int32_t biCompression;
    int32_t biSizeImage;
    int32_t biXPelsPerMeter;
    int32_t biYPelsPerMeter;
    int32_t biClrUsed;
    int32_t biClrImportant;
};

#endif //MUL_PROJEKT_CREATEBMP_H
