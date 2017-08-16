/*
 * Autors: Jakub Pelikan
 * Login: xpelik14
 * Datum: 29.2.2016
 * File:  huffmanTable.h
 * class read huffman table and inicialize build huffman tree
 */

#ifndef MUL_PROJEKT_HUFFMANTABLE_H
#define MUL_PROJEKT_HUFFMANTABLE_H
#include <iostream>
#include "fileAccess.h"


class TreeNode;


#define TABLE_LENGTH 16;
using namespace std;


class HuffmanTable {
public:
    HuffmanTable(FileAccess *input);
    ~HuffmanTable();
    bool init();
    void setRowItem(int item);
    void addRowItem(int item);
    int getTableItemCount();
    int getFirstItemCount();
    int getNodePath();
    int getNodeAscii();
    void initCreateTree();
    int isNextNodeAscii();
    int getType();
    int getId();
    int getDecodedInt8();

private:
    int type;
    int id;
    TreeNode *root;
    FileAccess *in_out_file;
    int path_count;
    int pos_on_path;
    int table_length;
    int pos_x;
    int pos_y;
    int table_item_count;
    int **ht;
};


#endif //MUL_PROJEKT_HUFFMANTABLE_H
