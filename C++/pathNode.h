/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.2.2016
 * File:  pathNode.h
 * Class Path node represent non leaf node in huffman tree.
 * Is child class from treeNode
 */

#ifndef MUL_PROJEKT_PATHNODE_H
#define MUL_PROJEKT_PATHNODE_H


#include "treeNode.h"

class PathNode : public TreeNode {
public:
    PathNode(TreeNode *parent_node, FileAccess *file_access, HuffmanTable *table);
};


#endif //MUL_PROJEKT_PATHNODE_H
