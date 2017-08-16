/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.4.2016
 * File:  quantizationTable.h
 * Class read and process quantization table data.
 */

#ifndef MUL_PROJEKT_QUANTIZATIONTABLE_H
#define MUL_PROJEKT_QUANTIZATIONTABLE_H


#include <vector>
#include <stdint.h>
#include <iostream>
#include "fileAccess.h"


class QuantizationTable {
public:
    QuantizationTable(FileAccess *input);
    bool init();
    int getId();
    vector <int> qt;
private:
    int id;
    FileAccess *in_out_file;
};


#endif //MUL_PROJEKT_QUANTIZATIONTABLE_H
