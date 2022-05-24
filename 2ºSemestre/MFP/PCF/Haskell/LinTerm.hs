module LinTerm where


data Vars = X | Y | Z deriving (Show, Eq)

data LTerm = Leaf (Either Vars Double) | Mult Double LTerm  | Add LTerm LTerm
        deriving Show

-- x
x = Leaf (Left X)

-- y
y = Leaf (Left Y)

-- t = x + 2 * y
t = Add x (Mult 2 y)

-- t' = 2 * x + 2 * y
t' = Add (Mult 2 x) (Mult 2 y)

-- t'' = 3 * (2*x) + 2 * (y + z)
z = Leaf (Left Z)
t'' = Add (Mult 3 (Mult 2 x)) (Mult 2 (Add y z))



sem :: LTerm -> (Vars -> Double) -> Double
sem (Leaf (Left v)) m = m v
sem (Leaf (Right r)) m = r
sem (Mult s t) m = let r = sem t m in s * r
sem (Add t1 t2) m = let r1 = sem t1 m
                        r2 = sem t2 m in r1 + r2

m X = 3
m Y = 4
m Z = 5