module BTerm where

import LinTerm

data BTerm = Leq LTerm LTerm | Conj BTerm BTerm | Neg BTerm deriving Show

bsem :: BTerm -> (Vars -> Double) -> Bool
bsem (Leq t1 t2) m = let r1 = sem t1 m
                         r2 = sem t2 m
                     in  if r1 <= r2 then True else False
bsem (Conj b1 b2) m = let v1 = bsem b1 m
                          v2 = bsem b2 m
                      in v1 && v2
bsem (Neg b) m = let v = bsem b m in not v



-- b = t <= t'
b = Leq t t'

-- c = Neg b
c = Neg b


