{- |
Project: SUBS-CIPHER
Author: Jakub Pelikan
Login:  xpelik14
File:   Main.hs
Date:  8.3.2016
Parametrs: [choice] [db] [input]
    choice "-t" - print decoded text to stdout
    choice "-k" - print key for code/decode text to stdout
Implementation of main file for aplication, which make ciphertext only attack.
-}
module Main(main) where

import System.Environment
import System.IO
import Control.Monad
import System.IO.Error

import SubsCipherData
import SubsCipherFunc
import StatisticFunction

-- precision of match diagram frequency
precision = 0.5

-- | Main method 'main'
main :: IO ()
main = do
--  Read arguments
    args <- getArgs
--  Parse argument
    (choice, databaseFileHandle, inputFileHandle, prec) <- parseArguments args
--  Read Input File/stdin
    input <- hGetContents inputFileHandle
--  Read database file
    database <- (readDatabase databaseFileHandle)
--  Create keys list
    let keyList = findKey prec choice (database) (createInputStatistic$ filter (/= '\r') $ filter (/= '\n') input)
--  Print key or decode input file
    case choice of
        "-t" -> putStrLn $ decodeText keyList input
        "-k" -> putStrLn $  getKeyString keyList
        otherwise ->  error $ "Unknown parameter: "++choice
--  Close files
    hClose databaseFileHandle
    hClose inputFileHandle
    return ()

-- | Method 'parseArguments' parse program argument
parseArguments ::  [String] -> IO (String, Handle, Handle, Float)
parseArguments [choice, datF] = do
    datFHandler <- openFile datF ReadMode
    return (choice, datFHandler, stdin, precision)
parseArguments [choice, datF, inF] = do
    datFHandler <- openFile datF ReadMode
    inFHandler <- openFile inF ReadMode
    return (choice, datFHandler, inFHandler, precision)
parseArguments [choice, datF, inF, "-p", a] = do
    datFHandler <- openFile datF ReadMode
    inFHandler <- openFile inF ReadMode
    return (choice, datFHandler, inFHandler, read a :: Float)
parseArguments _ = error "Missing or unexcepted argument"
