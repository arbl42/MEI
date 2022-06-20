module LogListMonad where

-- monad for position logs

import Data.List

-- The starting position
initialMove :: [(Int,Int)]
initialMove = return (0,0)

choice :: ([a],[a]) -> [a]
choice (l,m) = l ++ m

manyChoice :: [[a]] -> [a]
manyChoice = concat


-- The Knight's Quest (from "Learn you a Haskell for Great Good")
possibleMoves :: (Int,Int) -> [(Int,Int)]
possibleMoves (x,y) = manyChoice [
  return (x-1,y+2),
  return (x+1,y+2),
  return (x-1,y-2),
  return (x+1,y-2),
  return (x-2,y+1),
  return (x-2,y-1),
  return (x+2,y+1),
  return (x+2,y-1)]

exec = do s0 <- initialMove
          s1 <- possibleMoves s0
          s2 <- possibleMoves s1
          s3 <- possibleMoves s2
          return s3


-- Determines whether the target position was achieved or not
targetAchieved :: (Int,Int) -> [(Int,Int)] -> Bool
targetAchieved = elem



data LogList a = Log [(String, a)] deriving Show

remLog :: LogList a -> [(String, a)]
remLog (Log x) = x

instance Functor LogList where
  fmap f = let f' = \(s,x) -> (s, f x) in
    Log . (map f') . remLog

instance Applicative LogList where
  pure x = Log [([],x)]
  l1 <*> l2 = Log $ do x <- remLog l1
                       y <- remLog l2
                       g(x,y) where
                         g((s,f),(s',x)) = return (s ++ s', f x)

instance Monad LogList where
  return = pure
  l >>= k = Log $ do x <- remLog l
                     g x where
                       g(s,x) = let u = (remLog (k x)) in map (\(s',x) -> (s ++ s', x)) u

manyLChoice :: [LogList a] -> LogList a
manyLChoice = Log . manyChoice . (map remLog)

mwrite :: String -> LogList a -> LogList a
mwrite msg l = Log $ let l' = remLog l in map (\(s,x) -> (s ++ msg, x)) l'

linitialMove :: LogList (Int,Int)
linitialMove = return (0,0)


lpossibleMoves :: (Int,Int) -> LogList (Int,Int)
lpossibleMoves (x,y) = manyLChoice [
  mwrite (" "++(show (x,y))++" ") (return (x-1,y+2)),
  mwrite (" "++(show (x,y))++" ") (return (x+1,y+2)),
  mwrite (" "++(show (x,y))++" ") (return (x-1,y-2)),
  mwrite (" "++(show (x,y))++" ") (return (x+1,y-2)),
  mwrite (" "++(show (x,y))++" ") (return (x-2,y+1)),
  mwrite (" "++(show (x,y))++" ") (return (x-2,y-1)),
  mwrite (" "++(show (x,y))++" ") (return (x+2,y+1)),
  mwrite (" "++(show (x,y))++" ") (return (x+2,y-1))]

lexec = do s0 <- linitialMove
           s1 <- lpossibleMoves s0
           s2 <- lpossibleMoves s1
           s3 <- lpossibleMoves s2
           return s2


-- Determines whether the target position was achieved or not
ltargetAchieved :: (Int,Int) -> LogList (Int,Int) -> Maybe (String,(Int,Int))
ltargetAchieved t l = let l' = remLog l in find (\(s,x) -> x == t) l'