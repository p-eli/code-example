/*
 * Autor: Jakub Pelikan (xpelik14)
 * Datum: 9.2.2015
 * Soubor: ahed.c
 * Komentar: Library for code and decode data with adaptive Huffman codding.
 */

#include "ahed.h"

/* Nazev:
 *   AHEDEncoding
 * Cinnost:
 *   Funkce koduje vstupni soubor do vystupniho souboru a porizuje zaznam o kodovani.
 * Parametry:
 *   ahed - zaznam o kodovani
 *   inputFile - vstupni soubor (nekodovany)
 *   outputFile - vystupní soubor (kodovany)
 * Navratova hodnota:
 *    0 - kodovani probehlo v poradku
 *    -1 - pøi kodovani nastala chyba
 */
int AHEDEncoding(tAHED *ahed, FILE *inputFile, FILE *outputFile)
{
    //check and init ahed
    if (ahed == NULL){
        fprintf(stderr, "Error - ahed unexcepted value NULL");
        exit(EXIT_FAILURE);
    }
    ahed->codedSize = 0;
    ahed->uncodedSize = 0;
    //create escape Node
    node * escNode = NULL;
    if ((escNode = initNode(NULL, ' ', escapeNode)) == NULL){return AHEDFail;}
    node * root = escNode;
    char input = fgetc(inputFile);
    //create output data structure
    sOut * outputData = NULL;
    if ((outputData = initSOUT(outputFile, ahed)) == NULL){freeTree(root); return AHEDFail;}
    int status = 0;
    while (!feof(inputFile)){
        ahed->uncodedSize++;
        //search if input char is in tree
        status = searchCharInTree(root, input,outputData);
        if (status == 2){ //create new node
            if((status = writeNodePath(escNode, outputData)) != 0) {break;}
            if((status =  writeAscii(input, outputData)) != 0) {break;}
            if ((status = insertNode(escNode, input)) != 0){break;}
        } else if (status == 1){return AHEDFail;}
        updateRoot(&root);
        //read next input char
        input = fgetc(inputFile);
    }
    if (status==0){
        //write eof node to output file
        status = writeEOFNodeToFile(escNode, outputData);
    }
    //free alocated memory
    free(outputData);
    freeTree(root);
    if (status==0){
        return AHEDOK;
    } else {
        return AHEDFail;
    }
}


/* Nazev:
 *   AHEDDecoding
 * Cinnost:
 *   Funkce dekoduje vstupni soubor do vystupniho souboru a porizuje zaznam o dekodovani.
 * Parametry:
 *   ahed - zaznam o dekodovani
 *   inputFile - vstupni soubor (kodovany)
 *   outputFile - vystupní soubor (nekodovany)
 * Navratova hodnota:
 *    0 - dekodovani probehlo v poradku
 *    -1 - pøi dekodovani nastala chyba
 */
int AHEDDecoding(tAHED *ahed, FILE *inputFile, FILE *outputFile)
{
    //check and init ahed
    if (ahed == NULL){
        fprintf(stderr, "Error - ahed unexcepted value NULL");
        exit(EXIT_FAILURE);
    }
    ahed->codedSize = 0;
    ahed->uncodedSize = 0;
    //init escape and eof node
    node * root = initNode(NULL, ' ', escapeNode);
    if (root == NULL){return AHEDFail;}
    //create output data structure
    sOut * inputData = initSOUT(inputFile, ahed);
    if (inputData == NULL){freeTree(root);return AHEDFail;}
    inputData->buffer = fgetc(inputData->file);
    inputData->ahed->codedSize++;
    int status = 2;
    //read input file and create huffman tree
    while(status == 2){
        updateRoot(&root);
        status = buildDecodingTree(root, inputData, outputFile);
    }
    //free alocated memory
    free(inputData);
    freeTree(root);
    if (status == 0){
        return AHEDOK;
    }
    return AHEDFail;
}


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
int writeEOFNodeToFile(node * eofNode, sOut * outputData){
    int status = 0;
    status  = writeNodePath(eofNode, outputData);
      while(outputData->pos != 0){
         status = writeToFile(1, outputData);
      }
      return status;
}


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
int readEOFFromFile(char ch){
    for (int x=0;x<8;x++){
        if (((1 << 7) & ch) != (1 << 7)){
            fprintf(stderr, "Unexcepted EOF");
            return(1);
        }
        ch = ch << 1 ;
    }
    return 0;
}


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
int buildDecodingTree(node * actNode, sOut * inputData, FILE * outputFile){
    if (actNode->type == escapeNode){
        // read 8 bits, write bits to output and create new charNode
        int8_t ch;
        ch = getAsciiChar(inputData);
        // if eof finish decoding
        if (feof(inputData->file)){return readEOFFromFile(ch);}
        if (insertNode(actNode, (char)ch) == 1){return 1;}
        if (writeAsciiToFile(outputFile, (char)ch, inputData) == 1){return 1;}
    } else if(actNode->type == noCharNode){
        if (feof(inputData->file)){return(1);}
        if (getBitFromFile(inputData) == 1){ // 1 right child
            return buildDecodingTree(actNode->right, inputData, outputFile);
        } else { // 0 left child
            return buildDecodingTree(actNode->left, inputData, outputFile);
        }
    }  else if(actNode->type == charNode){ // write char to output file
        if (feof(inputData->file)){return(1);}
        if (writeAsciiToFile(outputFile, actNode->nodeChar, inputData) == 1){return 1;}
        if (updateTree(actNode) == 1){return 1;}
    }
    return (2);
}


/* Name:
 *   getAsciiChar
 * Function:
 *   Function return one char from input
 * Parametrs:
 *   inputData - input data structure
 * Return value:
 *   ch - one input char
 */
int8_t getAsciiChar(sOut * inputData){
    int8_t ch = 0;
    for(int x=0;x<8;x++){
        if (inputData->pos != 8){
            ch = (ch << 1) | getBitFromFile(inputData);
        } else {
            ch = (ch << 1) | 1;
        }
    }
    return(ch);
}


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
int getBitFromFile(sOut * inputData){
    int res = 0;
    if (((1 << 7) & inputData->buffer) == (1 << 7)){
        res = 1;
    } else {
        res = 0;
    }
    inputData->pos++;
    inputData->buffer = inputData->buffer << 1 ;
    if ((inputData->pos == 8)){
        inputData->buffer = fgetc(inputData->file);
        if ((!feof(inputData->file))){
            inputData->ahed->codedSize++;
            inputData->pos = 0;
        }
    }
    return res;
}


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
int writeAsciiToFile(FILE * outputFile, char ch, sOut * inputData){
    inputData->ahed->uncodedSize++;
    fputc(ch, outputFile);
    if(ferror(outputFile)){fprintf(stderr, "Error writing to file"); return 1;}
    return(0);
}


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
sOut * initSOUT(FILE * file, tAHED *ahed){
    sOut * outputData = malloc(sizeof(sOut));
    if (outputData == NULL){fprintf(stderr, "Error allocated memory");return(NULL);}
    outputData->pos = 0;
    outputData->buffer = 0;
    outputData->file = file;
    outputData->ahed = ahed;
    return outputData;
}


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
int writeToFile(int8_t number, sOut * outputData){
    outputData->pos++;
    outputData->buffer = outputData->buffer << 1 ;
    outputData->buffer = outputData->buffer | number ;
    if (outputData->pos == 8){
        outputData->ahed->codedSize++;
        fputc(outputData->buffer, outputData->file);
        if(ferror(outputData->file)){fprintf(stderr, "Error writing to file"); return 1;}
        outputData->pos = 0;
        outputData->buffer = 0;
    }
    return(0);
}


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
int writeNodePath(node * actualNode,sOut * outputData){
    int status = 0;
    if (actualNode->parent != NULL){
        if ((status = writeNodePath(actualNode->parent, outputData)) == 0){
            if (actualNode->parent->left == actualNode){ // left child write 0
                status = writeToFile(0, outputData);
            } else { // right child write 0
                status = writeToFile(1, outputData);
            }
        }
    }
    return(status);
}


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
int writeAscii(char ch,  sOut * outputData){
      for(int x=0;x<8;x++){
          if(((ch >> (7-x))& 1) == 1){
              if (writeToFile(1, outputData) == 1){return 1;}
          } else {
              if (writeToFile(0, outputData) == 1){return 1;}
          }
      }
    return(0);
}

/* Name:
 *   updateRoot
 * Function:
 *   Method set node pointer to root node
 * Parametrs:
 *   root - pointer whitch is update
 */
void updateRoot(node ** root){
    node * tmpNode = (*root);
    while (tmpNode->parent != NULL){
        tmpNode = tmpNode->parent;
    }
    (*root) = tmpNode;
}


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
int searchCharInTree(node * actualNode, char inputChar, sOut * outputData){
    int status = 2;
    if ((actualNode) == NULL) {
        return(status);
    } else if (((actualNode)->type == charNode) && ((actualNode)->nodeChar == inputChar)){
        if (writeNodePath(actualNode, outputData)==1){return(1);}
        status = updateTree(actualNode);
    } else if ((status = searchCharInTree((actualNode)->right, inputChar, outputData)) != 2) {
        return(status);
    } else if ((status = searchCharInTree((actualNode)->left, inputChar, outputData)) != 2) {
        return(status);
    }
    return(status);
}


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
int updateTree(node * updateNode){
    node * tmpNode = updateNode;
    int status = 0;
    while (tmpNode!= NULL){
        if(testTree(tmpNode, 0)==1){return(1);}
        status = incrementNode(tmpNode);
        if (status == 1){
            return status;
        } else if(status == 2){
            return 0;
        }
        tmpNode = tmpNode->parent;
    }
    return(0);
}


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
 *   0 - search node to change
 *   1 - no node to change
 */
int searchN(node * actNode, int pos, searchNode * sNode, node * changingNode, int rescale){
    if ((actNode != NULL) && (pos>=0) && (pos>sNode->pos)){
        if (((rescale == 0) && (actNode->count == changingNode->count))
             ||((rescale == 1) && (pos>0) && (actNode->count < changingNode->count))){
            if ((actNode != changingNode->parent)){
                node * tmpNode = changingNode->parent;
                while (tmpNode != NULL) {
                    if (tmpNode == actNode){
                        return 1;
                    }
                    tmpNode = tmpNode->parent;
                }
                sNode->findNode = actNode;
                sNode->pos = pos;
                return(0);
            }
        } else {
            int statusR = -1, statusL = -1;
            statusR = searchN(actNode->right, pos-1, sNode, changingNode, rescale);
            statusL = searchN(actNode->left, pos-1, sNode, changingNode, rescale);
            if ((statusL == 0)||(statusR == 0)){
                return(0);
            }
        }
    }
    return(1);
}


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
int testTree(node * updateNode, int rescale){
        int status = 0;
        node * root = updateNode;
        int pos = 0;
        while (root->parent != NULL){
            pos++;
            root = root->parent;
        }
         searchNode * seaNode = malloc(sizeof(searchNode));
         if (seaNode == NULL){fprintf(stderr, "Error allocated memory");return(1);}
         seaNode->pos = -1;
         seaNode->findNode = NULL;
         if ((searchN(root, pos, seaNode, updateNode, rescale) == 0)
                 && (seaNode->findNode != updateNode)){
             if (switchNode(updateNode, seaNode->findNode) == 1){
                 status = 1;
             } else {
                 status = 2;
             }
         }
         free(seaNode);
    return(status);
}


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
int switchNode(node * node1, node * node2){
    if (node1->parent != NULL){
        if (node1->parent->left == node1){
            node1->parent->left = node2;
        } else if (node1->parent->right == node1) {
            node1->parent->right = node2;
        } else{
            return(-1);
        }
    }
    if (node2->parent != NULL){
        if (node2->parent->left == node2){
            node2->parent->left = node1;
        } else if (node2->parent->right == node2) {
           node2->parent->right = node1;
        } else{
            return(-1);
        }
    }
    node * node1Parent = node1->parent;
    node1->parent = node2->parent;
    node2->parent = node1Parent;
    return(0);
}


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
int insertNode(node * escNode, char inputChar){
    node * newNode = initNode((escNode)->parent,' ', noCharNode);
    node * tmpNode = initNode(newNode, inputChar, charNode);
    if ((newNode == NULL) || (tmpNode == NULL)){return(1);}
    newNode->right = tmpNode;
    newNode->left = escNode;
    if (escNode->parent != NULL){
        if (escNode->parent->left == escNode){
            escNode->parent->left = newNode;
        } else {
            escNode->parent->right = newNode;
        }
    }
    escNode->parent = newNode;
    updateTree(tmpNode);
    return(0);
}


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
node * initNode(node * parent, char inputChar, nodeType type){
    node * newNode = malloc(sizeof(node));
    if (newNode == NULL){fprintf(stderr, "Error allocated memory");return(NULL);}
    newNode->left = NULL;
    newNode->right = NULL;
    newNode->parent = parent;
    newNode->count = 0;
    newNode->nodeChar = (int8_t)inputChar;
    newNode->type = type;
    return  newNode;
}


/* Name:
 *   freeTree
 * Function:
 *   Function free all tree node
 * Parametrs:
 *   actualNode - actual position node
 * Return value:
 */
void freeTree(node * actualNode){
    if (actualNode == NULL){
        return;
    } else {
        freeTree(actualNode->left);
        freeTree(actualNode->right);
        free(actualNode);
    }
}


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
int incrementNode(node * changingNode){
    int status = 0;
    changingNode->count++;
    if (changingNode->count+1 >= (65536-1)){ //2^16 -1
        if ((status = changeScaleTree(changingNode)) == 0){
            status = 2;
        }
    }
    return(status);
}


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
int changeScaleTree(node * changingNode){
    node * root = changingNode;
    updateRoot(&root);
    rescaleTree(root);
    int rebuild = 2;
    while(rebuild == 2){
        root = changingNode;
        updateRoot(&root);
        rebuild = rebuildTreeAfterRescale(root);
    }
    return(rebuild);
}


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
int rescaleTree(node * actNode){
    if (actNode == NULL){
        return (0);
    } else {
        if (actNode->type == charNode){
            actNode->count = actNode->count / 2;
            return(actNode->count);
        } else {
            actNode->count = rescaleTree(actNode->left) + rescaleTree(actNode->right);
            return (actNode->count);
        }
    }
}


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
int rebuildTreeAfterRescale(node * actNode){
    int status = 0;
    if (actNode == NULL){return 0;}
    if (actNode->type == charNode){
        node * tmpNode = actNode;
        while (tmpNode!= NULL){
            if(tmpNode->count > 0){
                status = testTree(tmpNode, 1);
                if (status == 2){
                    node * root = tmpNode;
                    updateRoot(&root);
                    recalculateTree(root);
                    return (status);
                } else if (status == 1){
                    return (status);
                }
            }
            tmpNode = tmpNode->parent;
        }
    } else if (actNode->type == noCharNode) {
        if ((status = rebuildTreeAfterRescale(actNode->left)) == 2){
            return status;
        } else {
            status = rebuildTreeAfterRescale(actNode->right);
        }
    }
    return status;
}


/* Name:
 *   recalculateTree
 * Function:
 *   Method recalculate tree node value
 * Parametrs:
 *   actNode - actual position node
 * Return value:
 *   int - value of node
 */
int recalculateTree(node * actNode){
    if (actNode == NULL){
        return (0);
    } else {
        if (actNode->type == charNode){
            return(actNode->count);
        } else {
            actNode->count = recalculateTree(actNode->left) + recalculateTree(actNode->right);
            return (actNode->count);
        }
    }
}
