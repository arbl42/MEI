{-# LANGUAGE FlexibleInstances #-}
module Adventurers where

import DurationMonad

-- The list of adventurers
data Adventurer = P1 | P2 | P5 | P10 deriving (Show,Eq)
-- Adventurers + the lantern
type Objects = Either Adventurer ()

-- The time that each adventurer needs to cross the bridge
-- To implement 
getTimeAdv :: Adventurer -> Int
getTimeAdv P1 = 1
getTimeAdv P2 = 2
getTimeAdv P5 = 5
getTimeAdv P10 = 10

{-- The state of the game, i.e. the current position of each adventurer
+ the lantern. The function (const False) represents the initial state
of the game, with all adventurers and the lantern on the left side of
the bridge. Similarly, the function (const True) represents the end
state of the game, with all adventurers and the lantern on the right
side of the bridge.  --}
type State = Objects -> Bool

instance Show State where
  show s = (show . (fmap show)) [s (Left P1),
                                 s (Left P2),
                                 s (Left P5),
                                 s (Left P10),
                                 s (Right ())]

instance Eq State where
  (==) s1 s2 = and [s1 (Left P1) == s2 (Left P1),
                    s1 (Left P2) == s2 (Left P2),
                    s1 (Left P5) == s2 (Left P5),
                    s1 (Left P10) == s2 (Left P10),
                    s1 (Right ()) == s2 (Right ())]



-- The initial state of the game
gInit :: State
gInit = const False

-- Changes the state of the game for a given object
changeState :: Objects -> State -> State
changeState a s = let v = s a in (\x -> if x == a then not v else s x)

-- Changes the state of the game of a list of objects 
mChangeState :: [Objects] -> State -> State
mChangeState os s = foldr changeState s os
                               

{-- For a given state of the game, the function presents all the
possible moves that the adventurers can make.  --}
-- To implement
allValidPlays :: State -> ListDur State
allValidPlays s = LD [
   if  s (Right()) == s (Left P1)
      then Duration(getTimeAdv P1, mChangeState [(Right()), (Left P1)] s)
      else Duration(0, s),
   if  s (Right()) == s (Left P2)
      then Duration(getTimeAdv P2, mChangeState [(Right()), (Left P2)] s)
      else Duration(0, s),
   if  s (Right()) == s (Left P5)
      then Duration(getTimeAdv P5, mChangeState [(Right()), (Left P5)] s)
      else Duration(0, s),
   if  s (Right()) == s (Left P10)
      then Duration(getTimeAdv P10, mChangeState [(Right()), (Left P10)] s)
      else Duration(0, s),
   if  (s (Right()) == s (Left P1)) && (s (Right()) == s (Left P2))
      then Duration(getTimeAdv P2, mChangeState [(Right()), (Left P1), (Left P2)] s)
      else Duration(0, s),
   if  (s (Right()) == s (Left P1)) && (s (Right()) == s (Left P5))
      then Duration(getTimeAdv P5, mChangeState [(Right()), (Left P1), (Left P5)] s)
      else Duration(0, s),
   if  (s (Right()) == s (Left P1)) && (s (Right()) == s (Left P10))
      then Duration(getTimeAdv P10, mChangeState [(Right()), (Left P1), (Left P10)] s)
      else Duration(0, s),
   if  (s (Right()) == s (Left P2)) && (s (Right()) == s (Left P5))
      then Duration(getTimeAdv P5, mChangeState [(Right()), (Left P2), (Left P5)] s)
      else Duration(0, s),
   if  (s (Right()) == s (Left P2)) && (s (Right()) == s (Left P10))
      then Duration(getTimeAdv P10, mChangeState [(Right()), (Left P2), (Left P10)] s)
      else Duration(0, s),
   if  (s (Right()) == s (Left P5)) && (s (Right()) == s (Left P10))
      then Duration(getTimeAdv P10, mChangeState [(Right()), (Left P5), (Left P10)] s)
      else Duration(0, s)]

{-- For a given number n and initial state, the function calculates
all possible n-sequences of moves that the adventures can make --}
-- To implement 
exec :: Int -> State -> ListDur State
exec 0 s = LD [return $ s]
exec n s = do s0 <- allValidPlays s
              exec (n-1) s0

{-- Is it possible for all adventurers to be on the other side
in <=17 min and not exceeding 5 moves ? --}
-- To implement
leq17 :: Bool
leq17 = any (\x -> getDuration(x) <= 17 && getValue(x) == const True) (remLD (exec 5 gInit))

{-- Is it possible for all adventurers to be on the other side
in < 17 min ? --}
-- To implement
l17 :: Bool
l17 = any (\x -> getDuration(x) < 17 && getValue(x) == const True) (remLD (exec 5 gInit))


--------------------------------------------------------------------------
{-- Implementation of the monad used for the problem of the adventurers.
Recall the Knight's quest --}

data ListDur a = LD [Duration a] deriving Show

remLD :: ListDur a -> [Duration a]
remLD (LD x) = x

-- To implement
instance Functor ListDur where
   fmap f = let f' = \(Duration (i,x)) -> Duration (i,f x) in
      LD . (map f') . remLD

-- To implement
instance Applicative ListDur where
   pure x = LD [Duration (0,x)]
   l1 <*> l2 = LD $ do x <- remLD l1
                       y <- remLD l2
                       g(x,y) where
                         g(Duration (t1, w), Duration (t2, z)) = return $ Duration (t1+t2, w z)

                      
-- To implement
instance Monad ListDur where
   return = pure
   l >>= k = LD $ do x <- remLD l
                     g x where
                        g(Duration (s,x)) = let u = (remLD (k x)) in map (\(Duration (s',x)) -> Duration (s + s', x)) u


manyChoice :: [ListDur a] -> ListDur a
manyChoice = LD . concat . (map remLD)
--------------------------------------------------------------------------
