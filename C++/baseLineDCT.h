/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 30.4.2016
 * File:  baseLineDCT.h
 * Method implement reading and baseline data process.
 */


#ifndef MUL_PROJEKT_BASELINE_H
#define MUL_PROJEKT_BASELINE_H

#include <vector>
#include <stdint.h>
#include <iostream>
#include "fileAccess.h"


typedef struct{
    int id;
    int sX; // sample fac
    int sY; // sample fac
    int quantTable; // Quant Tbl Sel
}comp;

typedef struct{
    int precision;  //Precision
    int nlines; //Number of Lines
    int sample; //Samples per Line
    int imagesize; //Image Size XxY
    int orient; //Raw Image Orientation
    int ncomp; //Number of Img components
    vector<comp> components; // array of component
}data;


class BaseLineDCT {
public:
    BaseLineDCT(FileAccess *input);
    bool init();
    data bs;  // baseline data structure
private:
    FileAccess *in_out_file;
};

#endif //MUL_PROJEKT_BASELINE_H
