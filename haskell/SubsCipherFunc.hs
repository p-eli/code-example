{- |
Project: SUBS-CIPHER
Author: Jakub Pelikan
Login:  xpelik14
File:   StatisticFunction.hs
Date:  8.3.2016
Implementation function for ciphertext only attack.
-}

module SubsCipherFunc
 (
    findKey,
    getKeyString,
    decodeText
 )where

import System.IO

import SubsCipherData

-- | Function implement cipher-text only attack
findKey :: Float -> String -> Dictionary -> Dictionary -> [Key]
findKey _ _ _ (Dictionary [] [] []) = error "Empty input file"
findKey _ _ (Dictionary [] _ _) _ = error "Empty one char database file"
findKey precision choice database inputStatistic = do
    let newKey = initKeyValue initKeyList (char $ head $ itemOne database) (char $ head $ itemOne inputStatistic)
    let newKey1 = initKeyValue newKey (char $ head $ tail $ itemOne database) (char $ head $ tail $ itemOne inputStatistic)
    let newKey2 = findTwoThreeCharKeys precision newKey1 database inputStatistic
    findOneCharKeys newKey2 (itemOne database) (itemOne inputStatistic)
    where
        -- | Function inicialize key list
        initKeyList :: [Key]
        initKeyList = map getKey ['a'..'z'] where
                    getKey :: Char -> Key
                    getKey keyName = Key keyName '-'
        -- | Function find key through one char database
        findOneCharKeys :: [Key] -> [CharStruct] -> [CharStruct] -> [Key]
        findOneCharKeys key [] _ = key
        findOneCharKeys key _ [] = key
        findOneCharKeys key (database) (y:inputData) = findOneCharKeys ((searchInputOnDatabase key database (y:inputData))) database inputData
        -- | Function find key through one, two and three char database
        findTwoThreeCharKeys ::Float -> [Key] -> Dictionary -> Dictionary -> [Key]
        findTwoThreeCharKeys precision key database inputData = do
        let newKey = searchTwoChar precision key (itemTwo database) (itemTwo inputData) [] (itemOne database)
        let newKey1 = searchInputOnDatabase newKey (itemThree database) (itemThree inputData)
        case ((itemTwo inputData), (itemThree inputData)) of
            ([],[]) -> newKey1
            ([],_:three) -> findTwoThreeCharKeys precision newKey1 database (Dictionary (itemOne inputData) [] three)
            (_:two,[]) -> findTwoThreeCharKeys precision newKey1 database (Dictionary (itemOne inputData) two [])
            (_:two,_:three) -> findTwoThreeCharKeys precision newKey1 database (Dictionary (itemOne inputData) two three)

-- | Function return key string from key list
getKeyString :: [Key] -> String
getKeyString [] = []
getKeyString keys = transformKey keys where
                        transformKey [] = []
                        transformKey (x:xs) = (value x):transformKey xs

-- | Function decode ciphertext to plain text
decodeText :: [Key] -> String -> String
decodeText _ [] = []
decodeText keys (y:text) =
    case (getKey keys y) of
        Nothing -> y:(decodeText keys text)
        Just x -> x:(decodeText keys text)

-- | Function find key through two char database
searchTwoChar :: Float -> [Key] -> [CharStruct]-> [CharStruct] ->  [BestItem]  -> [CharStruct] -> [Key]
searchTwoChar _ key _ [] _ _ = key
searchTwoChar _ key _ _ _ [] = key
searchTwoChar _ key [] _ [] _ = key
searchTwoChar _ key [] _ ((BestItem besKa besV rat):sda) _ = initKeyValue key [besKa] [besV]
searchTwoChar precision keys ((CharStruct datCh datVal):database) ((CharStruct inCh inVal):_) bestItem oneCharDB =
    if (datVal+(datVal*precision) >= inVal) && (datVal-(datVal*precision) < inVal) && matchKeys keys datCh inCh
        then if  getKey keys (head inCh) == Nothing && getKey keys (head $ tail inCh) == Nothing
                then initKeyValue keys datCh inCh
                else searchTwoChar precision keys (database) ((CharStruct inCh inVal):[]) (getBestItem bestItem datCh inCh keys oneCharDB )  oneCharDB
        else searchTwoChar precision keys (database) ((CharStruct inCh inVal):[]) bestItem  oneCharDB
    where
        -- | Function test which diagram is better, unknow char in them is mostli frequented in one char database
        getBestItem :: [BestItem] -> String -> String -> [Key] -> [CharStruct] -> [BestItem]
        getBestItem bestIT dat input keys oneChar
            | null dat = bestIT
            | null input = bestIT
            | null oneChar = bestIT
        getBestItem [] (x:dat) (y:input) keys oneChar =
            case (getKey keys y) of
                Just _ -> getBestItem [] dat input keys oneChar
                Nothing ->   [(BestItem x y (getOneCharStatValue x oneChar))]
        getBestItem ((BestItem besKa besV rat):sda) (x:dat) (y:input) keys oneChar =
            case (getKey keys y) of
                Just _ -> getBestItem [(BestItem besKa besV rat)] dat input keys oneChar
                Nothing ->  if getOneCharStatValue x oneChar < rat
                                then [(BestItem x y (getOneCharStatValue x oneChar))]
                                else getBestItem [(BestItem besKa besV rat)] dat input keys oneChar
        -- | Function return percentage from one char database
        getOneCharStatValue :: Char -> [CharStruct] -> Float
        getOneCharStatValue ch ((CharStruct (ke:k) val):dat) =
                    if ke == ch then val
                                else getOneCharStatValue ch dat

-- | Function find input string in database
searchInputOnDatabase :: [Key] -> [CharStruct] -> [CharStruct] -> [Key]
searchInputOnDatabase keyList [] _ = keyList
searchInputOnDatabase keyList _ [] = keyList
searchInputOnDatabase keyList (x:database) (y:inputData) =
    if matchKeys keyList (char x) (char y)
        then  initKeyValue keyList (char x) (char y)
        else searchInputOnDatabase keyList (database)(y:inputData)

-- | Function check value and key match
matchKeys  :: [Key] -> [Char] -> [Char] -> Bool
matchKeys keyList [] [] = True
matchKeys keyList (x:databaseMax) (y:inputMax) =
    case ((getKey keyList y), (getValue keyList x)) of
        (Nothing , Nothing) -> matchKeys (initKeyValue keyList [x] [y])  databaseMax inputMax
        (Just k, Just val) -> if (val == y) && (k == x)
            then matchKeys keyList databaseMax inputMax
            else False
        (_,_) -> False

-- | Function find key value
getValue :: [Key] -> Char -> Maybe Char
getValue [] _ = Nothing
getValue (k:keyList) keyName =
    if (key k) == keyName
        then if (value k) == '-'
            then Nothing
            else Just (value k)
        else getValue keyList keyName

-- | Function find value key
getKey :: [Key] -> Char -> Maybe Char
getKey [] _ = Nothing
getKey (x:keys) codValue =
    if (value x) == codValue then Just (key x)
                             else getKey keys codValue

-- | Function inicialize key value
initKeyValue :: [Key] -> [Char] -> [Char] -> [Key]
initKeyValue keyList [] [] = keyList
initKeyValue keyList (x:xs) (y:ys) =
    initKeyValue (updateKeyValue keyList x y) xs ys
    where
        -- | Function update key value
        updateKeyValue :: [Key] -> Char -> Char -> [Key]
        updateKeyValue [] keyName keyValue = []
        updateKeyValue (k:keyList) keyName keyValue =
            if (key k) == keyName then (Key keyName keyValue):keyList
                                  else k:updateKeyValue keyList keyName keyValue