module WhP where

import BTerm
import LinTerm


data WhP = Asg Vars LTerm | Seq WhP WhP | Ife BTerm WhP WhP | Wh BTerm WhP 
        deriving Show

one = Leaf (Right 1) 

pr = Wh (Leq x y) (Seq (Asg X (Add x y)) (Asg Y (Add y one)))



chMem :: Vars -> Double -> (Vars -> Double) -> (Vars -> Double)
chMem v r m = \u -> if u == v then r else m u

wsem :: WhP -> (Vars -> Double) -> (Vars -> Double)
wsem (Asg v t) m = chMem v (sem t m) m
wsem (Seq p q) m = let m' = wsem p m
                       m'' = wsem q m'
                   in m''
wsem (Ife b p q) m = if (bsem b m) then wsem p m else wsem q m
wsem (Wh b p) m = if (bsem b m) then let m' = wsem p m in wsem (Wh b p) m' else m                  