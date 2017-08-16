/*
 * Autors: Jakub Pelikan
 * Login: xpelik14
 * Datum: 29.2.2016
 * File:  huffmanTable.cpp
 * class read huffman table and inicialize build huffman tree
 */

#include "huffmanTable.h"
#include "asciiNode.h"
#include "pathNode.h"


HuffmanTable::HuffmanTable(FileAccess *input) {
    in_out_file = input;
}

HuffmanTable::~HuffmanTable(){
    delete ht;
}

bool HuffmanTable::init(){
    table_length = TABLE_LENGTH;
    ht = new int *[table_length];
    pos_x = pos_y = table_item_count = 0;
    int length = (in_out_file->getAsciiChar()<<8) + in_out_file->getAsciiChar();
    length -=2;
    // 4b -	class (0 is DC, 1 is AC, more on this later)
    // 4b - table id
    type = in_out_file->getBit();
    type = (type<<1) + in_out_file->getBit();
    type = (type<<1) + in_out_file->getBit();
    type = (type<<1) + in_out_file->getBit();

    id = in_out_file->getBit();
    id = (id<<1) + in_out_file->getBit();
    id = (id<<1) + in_out_file->getBit();
    id = (id<<1) + in_out_file->getBit();
    length --;

    //array of 16 u8  number of elements for each of 16 depths3
    for (int x=0;x<16;x++){
        setRowItem(in_out_file->getAsciiChar());
        length --;
    }
    for(int x=0;x<getTableItemCount();x++){
        addRowItem(in_out_file->getAsciiChar());
        length--;
    }
    if (length != 0){
        throw string("read huffman table fail");
    }
    // init build huffman tree
    initCreateTree();
    if (getFirstItemCount() == 0){
        root = new PathNode(NULL, in_out_file, this);
    } else {
        root = new AsciiNode(NULL, in_out_file, this);
    }
    root->buildTree();
return true;
}

// decode value in huffman tree
int HuffmanTable::getDecodedInt8(){
     return root->getDecodedInt8();
}

// get table type
int HuffmanTable::getType(){
    return type;
}

// get table id
int HuffmanTable::getId(){
    return id;
}

// set row item
void HuffmanTable::setRowItem(int item){
    ht[pos_x] = new int[item+1];
    ht[pos_x][0] = item;
    table_item_count+= item;
    pos_x++;
}

// add item to row
void HuffmanTable::addRowItem(int item){
    if (pos_y == 0){
        pos_x = 0;
        pos_y++;
    }
    while(ht[pos_x][0]<pos_y) {
        pos_x++;
        pos_y = 1;
        if (pos_x >= table_length) {
            throw string("read huffman table fail");
        }
    }
    ht[pos_x][pos_y] = item;
    pos_y++;
}

// return count of item in table
int HuffmanTable::getTableItemCount(){
    return table_item_count;
}

// return first item
int HuffmanTable::getFirstItemCount(){
    return ht[0][0];
}

// method inicialize building tree
void HuffmanTable::initCreateTree(){
    pos_on_path = path_count = pos_x = 0;
    while (ht[pos_x][0] == 0){
        pos_x++;
    }
    pos_y = 1;
}

// method return next bit in path
int HuffmanTable::getNodePath(){
    if (pos_x >= table_length){
        return 2;
    }
    int status = ((((1 << (pos_x-pos_on_path)) & path_count) != 0) != 0);
    pos_on_path++;
    return status;
}

// method return if next create node is leaf
int HuffmanTable::isNextNodeAscii(){
    if (pos_on_path>pos_x){
        return 1;
    } else {
        return 0;
    }
}

// method return asci node
int HuffmanTable::getNodeAscii(){
    int tmp = ht[pos_x][pos_y];
    pos_on_path = 0;
    path_count++;
    pos_y++;
    while(ht[pos_x][0]+1<=pos_y) {
        pos_x++;
        if (pos_x >= table_length){
            break;
        }
        path_count = path_count << 1;
        pos_y = 1;
    }
    return tmp;
}