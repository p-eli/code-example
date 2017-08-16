/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.2.2016
 * File:  pathNode.cpp
 * Class Tree node is parent class for leap and non-leap class.
 */

#ifndef MUL_PROJEKT_TREENODE_H
#define MUL_PROJEKT_TREENODE_H

#include <iostream>
#include "fileAccess.h"
#include "huffmanTable.h"


using namespace std;

class TreeNode {
public:
    TreeNode(TreeNode *parent_node, FileAccess *file_access, HuffmanTable *table);
    virtual void buildTree();
    virtual int getDecodedInt8();
protected:
    virtual bool createChild();
    FileAccess *in_out_file=NULL;
    TreeNode *left_child=NULL;
    TreeNode *right_child=NULL;
    TreeNode *parent=NULL;
    HuffmanTable *huffman_table;
};


#endif //MUL_PROJEKT_TREENODE_H
