/*
 * Autor: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 1.4.2016
 * File:  main.cpp
 * Main class.
 */
#include <iostream>
#include <string>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "jpegDecoder.h"
#include "optargs.h"

using namespace std;

int dctQuality = 8;
int grayscaleMode = 0;

int main(int argc, char *argv[]) {
    if(argc < 3 || argc > 5) {
        cout << "Program arguments:" << endl;
        cout << "input.jpg output.bmp [-qt=8] [-bw]" << endl;
        exit(0);
    }

    for(int i = 3; i < argc; i++) {
        std::string param = argv[i];

        if(param.substr(0,4).compare("-qt=") == 0 && strlen(argv[i]) > 4) {
            dctQuality = atoi(&argv[i][4]);
        }
        else if(param.compare("-bw") == 0) {
            grayscaleMode = 1;
        }
        else {
            cout << "Program arguments:" << endl;
            cout << "input.jpg output.bmp [-qt=8] [-bw]" << endl;
            exit(0);
        }
    }


    try {
        JpegDecoder * decoder = new JpegDecoder(argv[1],argv[2]);
        decoder->decodeJpegFile();
        decoder->saveData();


    } catch (string e){
        cout << "EXCEPTION, cant open file." << endl;
        cout << e << endl;
    }
    return 0;
}