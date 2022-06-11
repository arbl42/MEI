module TPC2 where

-- Comecando por utilizar o LTerm e o BTerm definidos nas aulas da UC:
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

{-
   E necessario adicionar o tempo de execucao de cada programa, de modo a calcular o tempo de execucao final,
 com execao do Assignment, que de acordo com as regras expostas no enunciado corresponde a 0 (Time = 0).
   Para este exercicio, nao se consideraram os tempos de execucao das comparacoes, apenas o tempo a operacoes relativas a alteracoes na
 memoria
   Tambem foi adicionada a operacao Wait, que consiste em aguardar Time unidades de tempo para executar depois o programa WhP
-}
data WhP = Asg Vars LTerm | Seq WhP Int WhP Int | Ife BTerm WhP Int WhP Int | Wh BTerm WhP Int | Wait Int WhP 
                deriving Show



-- Utilizando as funções de alteração de memória definadas durante as aulas da UC:
chMem :: Vars -> Double -> (Vars -> Double) -> (Vars -> Double)
chMem v r m = \u -> if u == v then r else m u

wsem :: WhP -> (Vars -> Double) -> (Vars -> Double)
wsem (Asg v t) m = chMem v (sem t m) m
wsem (Seq p _ q _) m = let m' = wsem p m
                           m'' = wsem q m'
                       in m''
wsem (Ife b p _ q _) m = if (bsem b m) then wsem p m else wsem q m
wsem (Wh b p t) m = if (bsem b m) then let m' = wsem p m in wsem (Wh b p t) m' else m                  
wsem (Wait _ p) m = wsem p m

{-
    Para implementar a parte do tempo de execucao, fez-se uma nova versao da funcao anterior.
    De modo a retornar memoria e o tempo de execucao, alterou-se o output da seguinte funcao para um par que contém
  a memória e o tempo de execucao final.
    Tambem se adicionou um argumento Time de modo a que seja possivel ir calculando o tempo de execucao ao longo da
  execucao do programa.
-}
wsemTime :: WhP -> (Vars -> Double) -> Int -> ((Vars -> Double), Int)
wsemTime asg@(Asg v t) m time = (wsem asg m, time)
wsemTime seq1@(Seq p t1 q t2) m time = (wsem seq1 m, (time + t1 + t2))
wsemTime ife@(Ife b p t1 q t2) m time = if (bsem b m) then (wsem p m, time + t1) else (wsem q m, time + t2)
wsemTime wh@(Wh b p t) m time = if (bsem b m) then (wsem p m, time + t) else (m,time)
wsemTime wait@(Wait t p) m time = (wsem wait m, t+time)


{- Exemplos de execucao(simples) do exercicio 1
one = Leaf (Right 1) 

exp1 = Wait 3 (Wait 4 (Asg X one))
exp2 = Wait 7 (Asg X one)
-}