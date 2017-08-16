/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.2.2016
 * File:  asciiNode.h
 * Class Ascii node represent leaf node in huffman tree.
 * Is child class from treeNode
 */

#ifndef MUL_PROJEKT_ASCIINODE_H
#define MUL_PROJEKT_ASCIINODE_H

#include "treeNode.h"

class AsciiNode : public TreeNode {
public:
    AsciiNode(TreeNode *parent_node, FileAccess *file_access, HuffmanTable *table);
    virtual void buildTree();
    virtual int getDecodedInt8();
private:
    int ascii_node;
};


#endif //MUL_PROJEKT_ASCIINODE_H
