{- Cyber-Physical Programming 2021/2022 - Recalling Haskell.
 - Why Haskell? We will use Haskell in this course to implement 
 - programming languages and respective semantics.
 - Author: Renato Neves.
 - Goal: Solve the exercises listed below.
-}

module LectureCPC where

import Data.Char
-- Basic functions and conditionals -- 

-- Implement the function that returns 
-- the maximum of two integers.
max1 :: (Int,Int) -> Int
max1 (x,y) | x >= y = x
           | otherwise = y

-- Implement the function that returns 
-- the maximum of three integers.
max3 :: (Int,(Int,Int)) -> Int
max3 (x1,pair@(x2,x3)) = max1 (x1,max1 pair)

-- Implement the function that scales 
-- a 2-dimensional real vector by a 
-- real number.
scaleV :: (Double, (Double,Double)) -> (Double, Double)
scaleV (s,(x1,x2)) = (s*x1,s*x2)

-- Implement the function that adds up
-- two 2-dimensional real vectors
addV :: ((Double,Double),(Double,Double)) -> (Double, Double)
addV ((x1,x2),(y1,y2)) = (x1+y1, x2+y2)

-- Type Synonims
type TSMatrix = ((Double,Double),(Double,Double))
type TVector = (Double,Double)

multM :: (TSMatrix,TVector) -> TVector
multM (((m1,m2),(m3,m4)), (d1,d2)) = (m1*d1 + m2*d2, m3*d1 + m4*d2)
--------------------------------------

-- Recursion ------------------------- 

-- Implement the function that calculates
-- the factorial of an integer
fact :: Int -> Int
fact 1 = 1
fact x = x * fact(x-1)

-- Implement the function that return the length
-- of a given list
len1 :: [a] -> Int
len1 = foldr (\ l -> (+) 1) 0

-- Implement the function that removes all
-- the even numbers from a given list
odds :: [Int] -> [Int]
odds = filter (\x -> x `mod` 2 /= 0)

-- Implement Caesar Cypher with shift=3
-- https://en.wikipedia.org/wiki/Caesar_cipher
-- Suggestion: add "import Data.Char", and use
-- the functions "chr" and "ord"
ecode :: String -> String
ecode = map (\l -> chr(ord l + 3))

dcode :: String -> String
dcode = map (\l -> chr(ord l - 3))

-- Implement the QuickSort algorithm
-- Think how to implement it in your favorite language
-- without using recursion
qSort :: [Int] -> [Int]
qSort = undefined

-- Implement the solution to the Hanoi problem
-- Think how to implement it in your favorite language
-- without using recursion
hanoi :: Int -> a -> a -> a -> [(a,a)]
hanoi = undefined
-- --------------------------------------

-- Datatypes ----------------------------

-- The datatype of leaf trees
data LTree a = Leaf a | Fork (LTree a, LTree a) deriving Show

-- Implement the function that increments all values
-- in a given leaf tree
incr :: LTree Int -> LTree Int
incr = undefined

-- Implement the function that counts the number of leafs
-- in a leaf tree 
count :: LTree Int -> Int
count = undefined

-- The datatype of binary trees
data BTree a = Empty | Node a (BTree a, BTree a) deriving Show

-- Implement the function that increments all values
-- in a given binary tree
bincr :: BTree Int -> BTree Int
bincr = undefined

-- Implement the function that counts the number of leafs
-- in a leaf tree 
bcount :: BTree Int -> Int
bcount = undefined


-- The datatype of "full" trees
data FTree a b = Tip a | Join b (FTree a b, FTree a b) deriving Show

-- Implement the function that sends a full tree into a leaf tree
fTree2LTree :: FTree a b -> LTree a
fTree2LTree = undefined

-- Implement the function that sends a full tree into a binary tree
fTree2BTree :: FTree a b -> BTree b
fTree2BTree = undefined


-- Implement the semantics of the following very simple 
-- language of Arithmetic Expressions
data Vars = X1 | X2
data Ops = Sum | Mult
type AExp = FTree (Either Vars Int) Ops
type AState = Vars -> Int

semA :: (AExp, AState) -> Int
semA = undefined

-- The datatype of "rose" trees
data RTree a b = Rtip a | Rjoin b [RTree a b] deriving Show

-- Implement the semantics of the following very simple 
-- language of Boolean Expressions
data BOps = Disj | Conj | Neg
type BExp = RTree (Either Vars Bool) BOps
type BState = Vars -> Bool

semB :: (BExp, BState) -> Bool
semB = undefined

-- Improve the two previous programming languages by defining
-- data types specific to them
--------------------------------------
