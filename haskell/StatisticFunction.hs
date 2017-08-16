{- |
Project: SUBS-CIPHER
Author: Jakub Pelikan
Login:  xpelik14
File:   StatisticFunction.hs
Date:  8.3.2016
Implementation function for create statistic from input file, and database file.
-}

module StatisticFunction
 (  readDatabase,
    createInputStatistic,
 ) where

import SubsCipherData
import System.IO

-- | Function read databse file and return Directory with database item
readDatabase :: Handle -> IO Dictionary
readDatabase handle = do
    content <- hGetContents handle
    let dict = procLines (lines content)
    return $ (Dictionary (quicksort $ itemOne dict) (quicksort $ itemTwo dict) (quicksort $ itemThree dict))

-- | Function implement database item procces
procLines :: [String] -> Dictionary
procLines lines =
    if null lines   then error "Empty database file"
                    else parseDatabaze lines (Dictionary [] [] [])
    where
        -- | Function parse database line
        parseDatabaze :: [String] -> Dictionary -> Dictionary
        parseDatabaze [] dictionary = dictionary
        parseDatabaze (line:fileLines) dictionary = parseDatabaze fileLines (addItemToDatabase  (CharStruct (getName line) (getNum line)) dictionary)
        -- | Function add processed line to dictionary
        addItemToDatabase :: CharStruct -> Dictionary -> Dictionary
        addItemToDatabase (CharStruct [] _) _ = error "Invalid database item string format"
        addItemToDatabase item dict =
            case length (char item) of
                1 ->  Dictionary ([item] ++ itemOne dict) (itemTwo dict) (itemThree dict)
                2 ->  Dictionary (itemOne dict) ([item] ++ itemTwo dict) (itemThree dict)
                3 ->  Dictionary (itemOne dict) (itemTwo dict) ([item] ++ itemThree dict)
                otherwise -> error "Invalid database item string length"
        -- | Function parse expresion from line
        getName :: String -> String
        getName [] = []
        getName (x:xs) =
            if x ==  ' ' then []
                         else x:getName xs
        -- | Function parse frequency from expresion
        getNum :: String -> Float
        getNum [] = error "Invalid database item number"
        getNum (x:xs) = if x ==  ' ' then read xs :: Float
                                     else getNum(xs)
-- | Function create dictionary with statistic about input file data
createInputStatistic :: String -> Dictionary
createInputStatistic [] = Dictionary [] [] []
createInputStatistic inputData = (Dictionary
        (quicksort $ occurrence2percent $ createOneCharStatistic inputData [])
        (quicksort $ occurrence2percent $ createTwoCharStatistic inputData [])
        (quicksort $ occurrence2percent $ createThreeCharStatistic inputData []))
    where
        -- | Function create one char statistic
        createOneCharStatistic :: String -> [CharStruct] -> [CharStruct]
        createOneCharStatistic input statistic =
            case input of
                [] -> statistic
                (y:ys) -> createOneCharStatistic ys (incrementItem [y] statistic)
        -- | Function create two char statristic
        createTwoCharStatistic :: String -> [CharStruct] -> [CharStruct]
        createTwoCharStatistic input statistic =
            case input of
                [] -> statistic
                (_:[]) -> statistic
                (x:y:ys) -> createTwoCharStatistic (y:ys) (incrementItem [x,y] statistic)
        -- | Function create three char statistic
        createThreeCharStatistic :: String -> [CharStruct] -> [CharStruct]
        createThreeCharStatistic input statistic =
            case input of
                [] -> statistic
                (_:[]) -> statistic
                (_:_:[]) -> statistic
                (x:y:z:ys) -> createThreeCharStatistic (y:z:ys) (incrementItem [x,y,z] statistic)
        -- | Function increment item counter
        incrementItem ::  String -> [CharStruct] ->  [CharStruct]
        incrementItem value statistic =
            case statistic of
                [] -> [CharStruct value 1]
                (x:xs) -> if (char x) == value  then CharStruct (char x) (succ (count x)) : xs
                                                else x : incrementItem value xs
-- | Function transform char count to percentage
occurrence2percent :: [CharStruct] -> [CharStruct]
occurrence2percent list = do
    let count = sumCount list
    updateCount list (count/100)
    where
        -- | Function make sum of char count
        sumCount :: [CharStruct] -> Float
        sumCount [] = 0
        sumCount ((CharStruct _ a):xs) =  a + (sumCount xs)
        -- | Function update item count from char count to percentage
        updateCount ::  [CharStruct] -> Float -> [CharStruct]
        updateCount [] _ = []
        updateCount (x:xs) percent = (CharStruct (char x) ((count x)/percent)):updateCount xs percent

-- | Function implement quick sort
quicksort ::  [CharStruct] -> [CharStruct]
quicksort [] = []
quicksort (x:xs) = quicksort [y | y <- xs, (count y) > (count x)] ++ [x] ++ quicksort [y | y <- xs, (count y) <= (count x)]