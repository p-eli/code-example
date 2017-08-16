/*
 * Autor: Jakub Pelikan
 * Login: xpelik14
 * Datum: 28.2.2016
 * File:  pathNode.cpp
 * Class Path node represent non leaf node in huffman tree.
 * Is child class from treeNode
 */
#include "pathNode.h"

PathNode::PathNode(TreeNode *parent_node, FileAccess *file_access, HuffmanTable *table): TreeNode(parent_node, file_access, table){}
