/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.2.2016
 * File:  pathNode.cpp
 * Class Tree node is parent class for leap and non-leap class.
 */
#include "treeNode.h"
#include "asciiNode.h"
#include "pathNode.h"


TreeNode::TreeNode(TreeNode *parent_node, FileAccess *file_access, HuffmanTable *table){
    huffman_table = table;
    parent = parent_node;
    in_out_file = file_access;
}

// method start build huffman tree
void TreeNode::buildTree(){
    bool stat;
    while(true){
        stat = createChild();
        if ((parent != NULL)||stat){
            return;
        }
    }
}

// method create child node
bool TreeNode::createChild(){
    switch(huffman_table->getNodePath()){
        case 0: //right child
            if (right_child == NULL){
                if (huffman_table->isNextNodeAscii()){
                    right_child = new AsciiNode(this, in_out_file, huffman_table);
                } else {
                    right_child = new PathNode(this, in_out_file, huffman_table);
                }
            }
            right_child->buildTree();
            return false;
        case 1: //left child
            if (left_child == NULL){
                if (huffman_table->isNextNodeAscii()){
                    left_child = new AsciiNode(this, in_out_file, huffman_table);
                } else {
                    left_child = new PathNode(this, in_out_file, huffman_table);
                }
            }
            left_child->buildTree();
            return false;
        case 2:
            return true;
        default:
            throw string("create huffman tree FAIL");
        }
}

// method decoded input data
int TreeNode::getDecodedInt8() {
    switch(in_out_file->getBit()){
        case 0: //right child
            if (right_child == NULL){
                return -1;
            }
            return right_child->getDecodedInt8();
        case 1: //left child
            if (left_child == NULL){
                return -1;
            }
            return left_child->getDecodedInt8();
        default:
            throw string("read input FAIL");
        }
}