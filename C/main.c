/*
 * Autor: Jakub Pelikan (xpelik14)
 * Datum: 9.2.2015
 * Soubor: main.c
 * Komentar: Application for code and decode data with adaptive Huffman codding.
 */ 
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <getopt.h>
#include "ahed.h"
#include <math.h>
#include <inttypes.h>

int main(int argc, char **argv)
{
    char *ifileName = NULL, *ofileName = NULL, *logfileName = NULL;
    int code = 0, decode = 0;
    if (argc > 1){    // parametr processing
        int opt = 0, option_index = 0;;
        // struct for parsing argument
        static struct option long_options[] = {
            {"ifile", required_argument, 0, 'i'},
            {"ofile", required_argument, 0, 'o'},
            {"logfile", required_argument, 0, 'l'},
            {"code", no_argument, 0, 'c'},
            {"decode", no_argument, 0, 'x'},
            {"help", no_argument, 0, 'h'},
            {0, 0, 0, 0}
        };
        //parse argument
        while ((opt = getopt_long(argc, argv,"i:o:l:cxh", long_options, &option_index )) != -1) {
            switch (opt) {
                case 'i' : //set input file name
                    if (ifileName != NULL){
                        fprintf(stderr, "Invalid multiple option -i \n Try 'ahed -h' for more information. \n");
                        exit(EXIT_FAILURE);
                    } else {
                        ifileName = optarg;
                    }
                    break;
                case 'o' : //set output file name
                    if (ofileName != NULL){
                        fprintf(stderr, "Invalid multiple option -o \n Try 'ahed -h' for more information. \n");
                        exit(EXIT_FAILURE);
                    } else {
                        ofileName = optarg;
                    }
                    break;
                case 'l' : //set log file name
                    if (logfileName != NULL){
                        fprintf(stderr, "Invalid multiple option -l \n Try 'ahed -h' for more information. \n");
                        exit(EXIT_FAILURE);
                    } else {
                        logfileName = optarg;
                    }
                    break;
                case 'c' : //set application code mode
                    if (decode == 0){
                        if (code == 1){
                            fprintf(stderr, "Invalid multiple option -c \n Try 'ahed -h' for more information. \n");
                            exit(EXIT_FAILURE);
                        } else {
                            code = 1;
                    }
                    } else {
                        fprintf(stderr, "Invalid option combination -c and -x \n Try 'ahed -h' for more information. \n");
                        exit(EXIT_FAILURE);
                    }
                    break;
                case 'x' : //set application decode mode
                    if (code == 0){
                        if (decode == 1){
                            fprintf(stderr, "Invalid multiple option -x \n Try 'ahed -h' for more information. \n");
                            exit(EXIT_FAILURE);
                        } else {
                            decode = 1;
                        }
                    } else {
                        fprintf(stderr, "Invalid option combination -c and -x \n Try 'ahed -h' for more information. \n");
                        exit(EXIT_FAILURE);
                    }
                    break;
                case 'h' : //print help
                    printf("Application for code and decode data with adaptive Huffman codding.\n\n"
                           "-i <ifile> Set input file name <ifile>. Default stdin.\n"
                           "-o <ofile> Set output file name <ofile>. Default stdout.\n"
                           "-l <logfile> Set log file <logfile>.\n"
                           "-c File code mode.\n"
                           "-x File decode mode.\n"
                           "-h Show help.\n"); // todo help
                    exit(EXIT_SUCCESS);
                default:
                    fprintf(stderr, "Try 'ahed -h' for more information. \n");
                    exit(EXIT_FAILURE);
                }
            }
    } else {
        fprintf(stderr, "No parametrs\n Try 'ahed -h' for more information. \n");
        exit(EXIT_FAILURE);
    }
    FILE *ifile = NULL, *ofile = NULL, *logfile = NULL;
    //open input name
    if (ifileName == NULL){
        ifile = stdin;
    } else {
        ifile = fopen(ifileName, "rb"); //todo
        if(ifile == NULL){
            perror("Error in opening file: ");
            return(EXIT_FAILURE);
        }
    }
    //open output file
    if (ofileName == NULL){
        ofile = stdout;
    } else {
        ofile = fopen(ofileName, "wb"); // todo  error open file
        if(ofile == NULL){
            perror("Error in opening file: ");
            return(EXIT_FAILURE);
        }
    }
    //alloc and init ahed
    tAHED *ahed = malloc(sizeof(tAHED));
    if (ahed == NULL){
        exit(EXIT_FAILURE);
    }
    ahed->codedSize = 0;
    ahed->uncodedSize = 0;
    int status = 0;
    if (code == 1){ // code input file
        status = AHEDEncoding(ahed, ifile, ofile);
    } else if (decode == 1) { // decode input file
        status = AHEDDecoding(ahed, ifile, ofile);
    } else {
        fprintf(stderr, "Missing parametr code or decode.\n Try 'ahed -h' for more information. \n");
        free(ahed);
        exit(EXIT_FAILURE);
    }
    // save ahed to file
    if ((status == EXIT_SUCCESS)&&(logfileName != NULL)){
        logfile = fopen(logfileName, "w");
        if(logfile == NULL){
            perror("Error in opening file: ");
            free(ahed);
            return(EXIT_FAILURE);
        }
        char buf[sizeof("login = xpelik14\nuncodedSize = \ncodedSize = \n")
                +sizeof(floor(log10(abs(ahed->uncodedSize))) + 1)
                +sizeof(floor(log10(abs(ahed->codedSize))) + 1)];
        snprintf(buf, sizeof buf, "login = xpelik14\n"
                                  "uncodedSize = %" PRId64 "\n"
                                  "codedSize = %" PRId64 "\n"
                 ,ahed->uncodedSize, ahed->codedSize);
        fputs(buf,logfile);
        //close log file
        fclose(logfile);
    }
    // close input file
    if (ifileName != NULL){
        fclose(ifile);
    }
    // close output file
    if (ofileName != NULL){
        fclose(ofile);
    }
    free(ahed);
    exit(status);
}
