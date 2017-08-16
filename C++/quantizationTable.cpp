/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.4.2016
 * File:  quantizationTable.cpp
 * Class read and process quantization table data.
 */

#include "quantizationTable.h"


QuantizationTable::QuantizationTable(FileAccess *input){
    in_out_file = input;
}

// read quantization table and store matrix to qt vector
bool  QuantizationTable::init(){
    int length = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    id = in_out_file->getAsciiChar();
    length -=3;
    for (int x=0;x<length;x++){
        qt.push_back(in_out_file->getAsciiChar());
    }
    return true;
}

// method return quantization table id
int QuantizationTable::getId(){
    return id;
}