/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.2.2016
 * File:  asciiNode.cpp
 */

#include "asciiNode.h"
AsciiNode::AsciiNode(TreeNode *parent_node, FileAccess *file_access, HuffmanTable *table): TreeNode(parent_node, file_access, table){
    ascii_node = -1;
}

// method create node in huffman tree
void AsciiNode::buildTree(){
    if (ascii_node != -1){
        throw string("Huffman tree decode error");
    }
    ascii_node = huffman_table->getNodeAscii();
}

// method creturn node value
int AsciiNode::getDecodedInt8() {
    return ascii_node;
}