/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 30.4.2016
 * File:  baseLineDCT.cpp
 */

#include "baseLineDCT.h"

BaseLineDCT::BaseLineDCT(FileAccess *input) {
    in_out_file = input;
}

// method read baseline block data
bool  BaseLineDCT::init(){
    int length = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    bs.precision = in_out_file->getAsciiChar();
    bs.nlines = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    bs.sample = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    bs.ncomp = in_out_file->getAsciiChar();
    length -=8;
    for (int x=0;x < bs.ncomp;x++){
        length -=3;
        comp c;
        c.id = in_out_file->getAsciiChar();
        c.sX = in_out_file->getAsciiChar();
        c.sY = c.sX & 0x0f;
        c.sX = (c.sX >> 4) & 0x0f;
        c.quantTable = in_out_file->getAsciiChar();
        bs.components.push_back(c);
    }
    for (int x=0;x<length;x++){
        in_out_file->getAsciiChar();
    }
    return true;
}
