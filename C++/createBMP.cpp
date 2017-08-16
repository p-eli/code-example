/*
 * Autors: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 4.5.2016
 * File:  createBMP.cpp
 * Method save rgb as bmp file
 */

#include "createBMP.h"
#include "jpegDecoder.h"
#include "baseLineDCT.h"


// constructor
CreateBMP::CreateBMP(jpegStruct *jstruct){
    js = jstruct;
}

// method set BMP header information and call save into file
void CreateBMP::save(FileAccess *file, vector <vector<int>> rgbData){
    const int headerLen = 14;

    uint8_t bmpHeader[headerLen] = {
            0x42, 0x4d,
            0x00, 0x00, 0x00, 0x00,
            0x00, 0x00,
            0x00, 0x00,
            0x36, 0x00, 0x00, 0x00
    };

    uint8_t bmpHeaderInfo[40];
    BitMapFileInfoHeader *bmpHeaderInfoMask = (BitMapFileInfoHeader*)bmpHeaderInfo;

    bmpHeaderInfoMask->biSize = 0x00000028;
    bmpHeaderInfoMask->biWidth = (int32_t) js->baseLineDCT->bs.sample;
    bmpHeaderInfoMask->biHeight = (int32_t) -(js->baseLineDCT->bs.nlines);
    bmpHeaderInfoMask->biPlanes = 0x0001;
    bmpHeaderInfoMask->biBitCount = 0x0018;
    bmpHeaderInfoMask->biCompression = 0x00000000;
    bmpHeaderInfoMask->biSizeImage = 0x00000000;
    bmpHeaderInfoMask->biXPelsPerMeter = 0x00000000;
    bmpHeaderInfoMask->biYPelsPerMeter = 0x00000000;
    bmpHeaderInfoMask->biClrUsed = 0x00000000;
    bmpHeaderInfoMask->biClrImportant = 0x00000000;

    file->saveOutput(rgbData, bmpHeader, bmpHeaderInfo, headerLen, sizeof(BitMapFileInfoHeader));
}

