/*
 * Autors: Jakub Pelikan, Petr Vizina
 * Login: xpelik14, xvizin00
 * Datum: 28.2.2016
 * File:  fileAccess.h
 * Class for manipulate with input output file
 */

#ifndef MUL_PROJEKT_FILEACCES_H
#define MUL_PROJEKT_FILEACCES_H

#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>


using namespace std;

// structure store information about position in input file
typedef struct {
    int pos=0;
    int buffer = 0;
    int nextNum=0;
    void * file;
} openFileStructure;


class FileAccess {
public:
    FileAccess(string inputName, string outputName);
    bool eof = false;
    int getBit();
    int getAsciiChar();
    void openFileForRead(string inputFileName);
    void openFileForWrite(string outputFileName);
    void enableRemoveZero();
    void disableRemoveZero();
    bool endOfImage();
    void clearBuffer();
    void saveOutput(vector <vector<int>> output, uint8_t *header, uint8_t *headerInfo, int headerSize, int headerInfoSize);
private:
    bool endOfImg = false;
    bool removeZero = false;
    openFileStructure *inputStruct=NULL;
    openFileStructure *outputStruct = NULL;
};


#endif //MUL_PROJEKT_FILEACCES_H
