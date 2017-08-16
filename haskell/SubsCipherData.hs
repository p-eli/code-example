{- |
Project: SUBS-CIPHER
Author: Jakub Pelikan
Login:  xpelik14
File:   SubsCipherData.hs
Date:  8.3.2016
Data type for StatisticFunction,hs and SubsCipherFunc.hs
-}

module SubsCipherData where
-- | Data type Dictionary for store char diagram and triagram statistic
data Dictionary = Dictionary {
    itemOne :: [CharStruct],
    itemTwo :: [CharStruct],
    itemThree :: [CharStruct]
    } deriving Show

-- | Data type CharStruct for store dicrionary items
data CharStruct = CharStruct {
    char :: String,
    count :: Float
    } deriving (Show, Eq)

-- | Data type Key for store find key
data Key = Key {
    key :: Char,
    value :: Char
    } deriving (Show, Eq)

-- | Data type for store best item in diagram match
data BestItem = BestItem{
    bestKey :: Char,
    bestValue :: Char,
    rate :: Float
}

