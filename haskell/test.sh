#!/bin/bash
#Author : Jakub Pelikan, xpelik14
#Test script for subs-cipher

EXEC=./subs-cipher
PRINT_KEY_FLAG=-k
PRINT_DECODE_TEXT_FLAG=-t
TEST_FOLDER=./test/

ONE_CHAR_DB=oneChar.db
TWO_CHAR_DB=twoChar.db
THREE_CHAR_DB=threeChar.db
EMPTY_DB=empty.db
BAD_FORMAT_DB=badFormat.db

EMPTY_IN=empty.in
CODED_TEXT=test.in
PLAIN_TEXT=test.out
KEY_FILE=key.txt

# function print test header
function print_test_header() {
	local header="$1"
	echo "" >&2
	echo "===============================================================" >&2
	echo "TEST: $header" >&2
	echo "===============================================================" >&2
}

#function compare original and generated key
function cmp_keys(){
	echo "$(tput setaf 3)""COMPARE KEY""$(tput sgr0)" >&2
	echo >&2
    local databaseFile="$1"
    local inputFile="$2"
    key_new="$("$EXEC" "$PRINT_KEY_FLAG" "$TEST_FOLDER""$databaseFile" "$TEST_FOLDER""$inputFile")"
    echo "ORIGINAL KEY: " `cat "$TEST_FOLDER""$KEY_FILE"`  >&2
    echo "FIND     KEY: " "$key_new"  >&2
    echo >&2
    key_original="$(cat "$TEST_FOLDER""$KEY_FILE")"
    diffChar=0
    for (( i=0; i<${#key_original}; i++ )); do
        if [ "${key_original:$i:1}" == "${key_new:$i:1}" ] ;then
            ((diffChar=diffChar+1))
        fi
    done
    echo "MATCH: " "$(tput setaf 2)" "$diffChar" / "$i" "$(tput sgr0)"
    echo >&2
}

#fuction compare original plain text with decoded plain text
function cmp_file(){
    echo "$(tput setaf 3)""COMPARE ORIGINAL PLAIN AND DECODED PLAIN TEXT""$(tput sgr0)" >&2
	echo >&2
    local databaseFile="$1"
    local inputFile="$2"
    decode="$("$EXEC" "$PRINT_DECODE_TEXT_FLAG" "$TEST_FOLDER""$databaseFile" "$TEST_FOLDER""$inputFile")"
    originalPlain="$(cat "$TEST_FOLDER""$PLAIN_TEXT")"
    diffChar=0
    for (( i=0; i<${#originalPlain}; i++ )); do
        if [ "${originalPlain:$i:1}" == "${decode:$i:1}" ] ;then
            ((diffChar=diffChar+1))
        fi
    done
    echo "MATCH: " "$(tput setaf 2)" "$diffChar" / "$i"   --\>  $((diffChar/(i/100)))%     "$(tput sgr0)"
}

# function run key and text compare
function run_test(){
    local name="$1"
    local databaseFile="$2"
    local inputFile="$3"
    local outputKey="$4"
    print_test_header "$name"
    cmp_keys "$databaseFile" "$inputFile"
    cmp_file "$databaseFile" "$inputFile"
}

#function test no argument
function no_argument_test(){
    local name="$1"
    print_test_header "$name"
    echo "$(tput setaf 2)"
    "$EXEC"
    echo "$(tput sgr0)"
}

#function test argument
function argument_test(){
    local name="$1"
    local choice="$2"
    local databaseFile="$3"
    local inputFile="$4"
    print_test_header "$name"
    echo "$(tput setaf 2)"
    "$EXEC" "$choice" "$TEST_FOLDER""$databaseFile" "$TEST_FOLDER""$inputFile"
    echo "$(tput sgr0)"
}

no_argument_test "No Argument"
argument_test "Input file not exist" -t a
argument_test "Unknown argument -h" -h "$THREE_CHAR_DB" "$CODED_TEXT"
argument_test "Empty database" "$PRINT_KEY_FLAG" "$EMPTY_DB" "$CODED_TEXT"
argument_test "Bad database format 'tios 0.378058'" "$PRINT_KEY_FLAG" "$BAD_FORMAT_DB" "$CODED_TEXT"
argument_test "Empty input file" "$PRINT_KEY_FLAG" "$THREE_CHAR_DB" "$EMPTY_IN"

run_test "Database with only single char frequency" "$ONE_CHAR_DB" "$CODED_TEXT"
run_test "Database with single char and diagram frequency" "$TWO_CHAR_DB" "$CODED_TEXT"
run_test "Database with single char, diagram and trigram frequency" "$THREE_CHAR_DB" "$CODED_TEXT"


echo >&2