/*
 * Autor: Jakub Pelikan (xpelik14)
 * Datum: 9.2.2015
 * Soubor: ahed.d
 * Komentar: Head file for library for code and decode data with adaptive Huffman codding.
 */

#ifndef __KKO_AHED_H__
#define __KKO_AHED_H__

#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
#include <string.h>

#define AHEDOK 0
#define AHEDFail -1

/* Datovy typ zaznamu o (de)kodovani */
typedef struct{
    /* velikost nekodovaneho retezce */
    int64_t uncodedSize;
    /* velikost kodovaneho retezce */
    int64_t codedSize;
} tAHED;


/* Enum with type of node */
typedef enum {EOFNode, noCharNode, escapeNode, charNode} nodeType;


/* Data type node */
typedef struct treeElement node;


/* Struct of tree node*/
struct treeElement {
    u_int16_t count;
    int8_t nodeChar;
    nodeType type;
    node *left, *right, *parent;
};


/* Data type for search node */
typedef struct sNode searchNode;


/* Struct for search node */
struct sNode{
    node * findNode;
    int pos;
};


/* Data type for output data structure */
typedef struct saveOut sOut;


/* Structure for output data */
struct saveOut{
    int pos;
    int8_t buffer;
    FILE * file;
    tAHED *ahed;
};

/* Nazev:
 *   AHEDEncoding
 * Cinnost:
 *   Funkce koduje vstupni soubor do vystupniho souboru a porizuje zaznam o kodovani.
 * Parametry:
 *   ahed - zaznam o kodovani
 *   inputFile - vstupni soubor (nekodovany)
 *   outputFile - vystupn? soubor (kodovany)
 * Navratova hodnota:
 *    0 - kodovani probehlo v poradku
 *    -1 - p?i kodovani nastala chyba
 */
int AHEDEncoding(tAHED *ahed, FILE *inputFile, FILE *outputFile);


/* Nazev:
 *   AHEDDecoding
 * Cinnost:
 *   Funkce dekoduje vstupni soubor do vystupniho souboru a porizuje zaznam o dekodovani.
 * Parametry:
 *   ahed - zaznam o dekodovani
 *   inputFile - vstupni soubor (kodovany)
 *   outputFile - vystupn? soubor (nekodovany)
 * Navratova hodnota:
 *    0 - dekodovani probehlo v poradku
 *    -1 - p?i dekodovani nastala chyba

 */
int AHEDDecoding(tAHED *ahed, FILE *inputFile, FILE *outputFile);


/* Name:
 *   writeEOFNodeToFile
 * Function:
 *   Function write EOF node to output file
 * Parametrs:
 *  eofNode - eof node
 *  outputData - output data structure
 * Return value:
 *   0 - write node success
 *   1 - write node fail
 */
int writeEOFNodeToFile(node * eofNode, sOut * outputData);


/* Name:
 *   readEOFFromFile
 * Function:
 *   Function read bytes after last node, witch must be one
 * Parametrs:
 *  ch - last read char
 * Return value:
 *   0 - read input success
 *   1 - read input fail
 */
int readEOFFromFile(char ch);


/* Name:
 *   buildDecodingTree
 * Function:
 *   Function build decoding tree
 * Parametrs:
 *   actNode - actual position node
 *   inputData - input data structure
 *   outputFile - output file
 * Return value:
 *   0 - build decoding tree success
 *   1 - build decoding tree fail
 */
int buildDecodingTree(node * actNode, sOut * inputData, FILE * outputFile);


/* Name:
 *   getAsciiChar
 * Function:
 *   Function return one char from input
 * Parametrs:
 *   inputData - input data structure
 * Return value:
 *   ch - one input char
 */
int8_t getAsciiChar(sOut * inputData);


/* Name:
 *   getBitFromFile
 * Function:
 *   function return representation of one bit from input file
 * Parametrs:
 *   inputData - input data structure
 * Return value:
 *   0 - value represent 0 bit
 *   1 - value represent 1 bit
 */
int getBitFromFile(sOut * inputData);


/* Name:
 *   writeAsciiToFile
 * Function:
 *   Function write ascii char to output
 * Parametrs:
 *   outputFile - output file
 *   ch - write char
 *   inputData - input data structure
 * Return value:
 *   0 - write char success
 *   1 - write char fail
 */
int writeAsciiToFile(FILE * outputFile, char ch, sOut * inputData);


/* Name:
 *   initSOUT
 * Function:
 *   Method init output/input data structure
 * Parametrs:
 *   file - output/input file descriptor
 *   ahed - statustic structure
 *   actNode - actual position node
 *   NULL - init fail
 *   sOut - init success
 */
sOut * initSOUT(FILE * file, tAHED *ahed);


/* Name:
 *   writeToFile
 * Function:
 *   Function write bajt to file.
 * Parametrs:
 *   number - 1/0 bit representation
 *   outputFile - output file
 * Return value:
 *   0 - write success
 *   1 - write fail
 */
int writeToFile(int8_t number, sOut * outputData);


/* Name:
 *   writeNodePath
 * Function:
 *   Function write to output file path from root to actual node
 * Parametrs:
 *   actNode - actual position node
 *   outputFile - output file
 * Return value:
 *   0 - write path success
 *   1 - write path fail
 */
int writeNodePath(node * actualNode,sOut * outputData);


/* Name:
 *   writeAscii
 * Function:
 *   Method write ascii char to ouput buffer
 * Parametrs:
 *   ch - add char
 *   outputData - output file
 * Return value:
 *   0 - add char success
 *   1 - add char fail
 */
int writeAscii(char ch,  sOut * outputData);


/* Name:
 *   updateRoot
 * Function:
 *   Method set node pointer to root node
 * Parametrs:
 *   root - pointer whitch is update
 */
void updateRoot(node ** root);


/* Name:
 *   searchCharInTree
 * Function:
 *   Method search char in tree
 * Parametrs:
 *   actNode - actual position node
 *   inputChar - search char
 *   inputData - input data structure
 * Return value:
 *   0 - search char success
 *   1 - search error
 *   2 - char not found
 */
int searchCharInTree(node * actualNode, char inputChar, sOut * outputData);


/* Name:
 *   updateTree
 * Function:
 *   Function increment and update node position,
 *   from actual to root node.
 * Parametrs:
 *   outputFile - output file
 * Return value:
 *   0 - update tree success
 *   1 - update tree fail
 */
int updateTree(node * updateNode);


/* Name:
 *   searchN
 * Function:
 *   Function search if exist node on better position on tree,
 *   and add them to searchNode structure.
 * Parametrs:
 *   actNode - actual position node
 *   pos - distance from root
 *   sNode - searchNode structure
 *   changingNode - updating node
 *   rescale - 0/1 normal/rescale search mode
 * Return value:
 *   0 - search success
 *   1 - search fail
 */
int searchN(node * actNode, int pos, searchNode * sNode, node * changingNode, int rescale);


/* Name:
 *   testTree
 * Function:
 *   Function search if exist node on better position on tree,
 *   and if is possible better position change update node with
 *   node on better position.
 * Parametrs:
 *   updateNode - update node
 *   rescale - 0/1 normal/rescale search mode
 * Return value:
 *   0 - no changes
 *   1 - test tree fail
 *   2 - function switch node
 */
int testTree(node * updateNode, int rescale);


/* Name:
 *   switchNode
 * Function:
 *   Function switch two node
 * Parametrs:
 *   node1 - node 1
 *   node2 - node 2
 * Return value:
 *   0 - switch node success
 *   1 - switch node fail
 */
int switchNode(node * node1, node * node2);


/* Name:
 *   insertNode
 * Function:
 *   Function insert new node to tree
 * Parametrs:
 *   escNode - escape node
 *   inputChar - new node char
 * Return value:
 *   0 - add node success
 *   1 - add node fail
 */
int insertNode(node * escNode, char inputChar);


/* Name:
 *   initNode
 * Function:
 *   Function init new node
 * Parametrs:
 *   parent - node parent
 *   inputChar - new node char
 *   type - type of node
 * Return value:
 *   node - return new node
 *   NULL - create new node fail
 */
node * initNode(node * parent, char inputChar, nodeType type);


/* Name:
 *   freeTree
 * Function:
 *   Function free all tree node
 * Parametrs:
 *   actualNode - actual position node
 * Return value:
 */
void freeTree(node * actualNode);


/* Name:
 *   incrementNode
 * Function:
 *   Function increment node
 * Parametrs:
 *   changingNode - node to increment
 * Return value:
 *   0 - increment without tree rescale success
 *   1 - increment fail
 *   2 - increment with tree rescale success
 */
int incrementNode(node * changingNode);


/* Name:
 *   changeScaleTree
 * Function:
 *   Function change tree scale and then rebuild tree
 * Parametrs:
 *   changingNode - some tree node
 * Return value:
 *   0 - change scale without change some node position
 *   1 - change scale fail
 *   2 - change scale with change some node position
 */
int changeScaleTree(node * changingNode);


/* Name:
 *   rescaleTree
 * Function:
 *   Method divided lift of tree and
 *   compute new parent value
 * Parametrs:
 *   actNode - actual position node
 * Return value:
 *   int - value of node
 */
int rescaleTree(node * actNode);


/* Name:
 *   rebuildTreeAfterRescale
 * Function:
 *   Function rebuild tree after rescale
 * Parametrs:
 *   actNode - actual position node
 * Return value:
 *   0 - rebuild succes without chagne some node position
 *   1 - rebuild tree fail
 *   2 - rebuild succes with chagne some node position
 */
int rebuildTreeAfterRescale(node * actNode);


/* Name:
 *   recalculateTree
 * Function:
 *   Method recalculate tree node value
 * Parametrs:
 *   actNode - actual position node
 * Return value:
 *   int - value of node
 */
int recalculateTree(node * actNode);

#endif
