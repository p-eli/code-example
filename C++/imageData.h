/*
 * Autors: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 30.4.2016
 * File:  imageData.cpp
 * Class read image data
 */

#ifndef MUL_PROJEKT_IMAGEDATA_H
#define MUL_PROJEKT_IMAGEDATA_H


#include "fileAccess.h"
#include "jpegDecoder.h"

typedef struct {
    int Ns;  //Number of image components
    int Csj; //Scan Component Selector
    int Tdj_Taj; // DC Coding Table Selector ___ AC Coding Table Selector
    int Ss; // Start of spectral selection
    int Se; // End of  spectral selection
    int Ah_Ai; // Successive Approximation Bit High ___  Successive Approximation Bit Low
} header;

// array for zigzag decoding
static const int zigZag[64] = {0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40, 48, 41,
                               34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30,
                               37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63};

typedef struct {
    int index = 0;
    int line = 0;
    int item  =0;
   vector <unsigned char> data;
}bl;

class ImageData {
public:
    ImageData(FileAccess *input, jpegStruct *js);
    bool init();
    vector <vector<int>> getRgb();

private:
    vector <vector <vector <unsigned int>>> data_in;
    void subsampling(vector<unsigned char> block, int com, int pos);
    void subsampling2(vector<unsigned char> block, int com, int pos);
    vector <bl*> data_out;
    vector <unsigned char> invDisCosTransform(vector<int> block, int com, int posX, int posY, int position, int samplingType);
    header h;
    void decodeData(vector<int> *dc_value_sum, int position, int samplingType);
    void readHeader();
    int readNBits(int nbits);
    FileAccess *in_out_file;
    jpegStruct *js;
};

#endif //MUL_PROJEKT_IMAGEDATA_H

