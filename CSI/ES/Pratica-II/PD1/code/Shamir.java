import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Shamir {

    private static final BigInteger MAX_COEFS = BigInteger.valueOf(50);
    BigInteger nCoefs = MAX_COEFS; // n maximo de coefs
    BigInteger coefs[] = new BigInteger[nCoefs.intValue()];
    BigInteger p;

    // P>M e P>S
    private static BigInteger choosePrime(BigInteger m, BigInteger s){
        BigInteger max = s.multiply(BigInteger.TEN);
        BigInteger min;
        if(m.compareTo(s) > 0)
            min = m.add(BigInteger.ONE);
        else
            min = s.add(BigInteger.ONE);
        
        BigInteger prime;
        while (true)
        {
            BigInteger count = BigInteger.ZERO;
            double x  = Math.random();
            double y  = max.doubleValue() * x;
            double z  = Math.ceil(y);
            System.out.println(z);
            prime     = BigInteger.valueOf((int)z);
            System.out.println(prime);
            for (BigInteger i = BigInteger.ONE; i.compareTo(prime) <= 0; i=i.add(BigInteger.ONE))
            {
                BigInteger modfactor = prime.remainder(i);
                if (modfactor.compareTo(BigInteger.ZERO) == 0)
                {
                    count = count.add(BigInteger.ONE);
                }
            }
            if (count.compareTo(BigInteger.TWO) == 0)
            {
                if (prime.compareTo(min) > 0) break;
            }
        }
        return prime;
    }

    private static BigInteger mod(BigInteger a, BigInteger n){
        while(a.compareTo(BigInteger.ZERO)<0)
            a = a.add(n);
        return a.mod(n);
    }

    // http://www-math.ucdenver.edu/~wcherowi/courses/m5410/exeucalg.html
    public static BigInteger extendedEuclideanAlgorithm(BigInteger initialA, BigInteger n){
        if(initialA.compareTo(BigInteger.ZERO) < 0){
            initialA = mod(initialA,n);
        }
        // a = q * b + r
        BigInteger a=n;
        BigInteger b=initialA;
        BigInteger finalP;
        BigInteger r=BigInteger.ONE,i=BigInteger.ZERO; // para poder entrar no ciclo
        ArrayList<BigInteger> p = new ArrayList<>();
        ArrayList<BigInteger> q = new ArrayList<>();
        p.add(BigInteger.ZERO);p.add(BigInteger.ONE);

        while(r.compareTo(BigInteger.ZERO)!=0){ // calcular todos os q (e ir calculando p)
            q.add(a.divide(b));
            r = a.subtract( q.get(i.intValue()).multiply(b));
            
            if(i.compareTo(BigInteger.ONE)>0){ // calcular p
                 p.add(
                    mod( p.get(i.intValue()-2).subtract( (p.get(i.intValue()-1).multiply( q.get(i.intValue()-2)))), n)    
                 );
            }

            a = b;
            b = r;
            i = i.add(BigInteger.ONE);
        }
        if(i.compareTo(BigInteger.TWO) < 0){ // caso em que b é divisor de a, por exemplo para os args 1 e 1613
            return BigInteger.ONE;
        }
        finalP = mod( p.get(i.intValue()-2).subtract( (p.get(i.intValue()-1).multiply( q.get(i.intValue()-2)))), n);

        return finalP;
    }

    private boolean checkIfNumberWasAlreadyGenerated(BigInteger n){
        int ii;
        for(BigInteger i=BigInteger.ZERO; i.compareTo(this.nCoefs)<0; i = i.add(BigInteger.ONE)){
            ii = i.intValue();
            if(this.coefs[ii] == n){
                return true;
            }
        }
        return false;
    }

    // IMPORTANTE: BigIntegereiro gerado NÃO pode estar já dentro dos coefs
    private BigInteger generateBigInteger(BigInteger exclusiveMax){
        Random rand = new Random();
        rand.nextInt(exclusiveMax.intValue());
        BigInteger BigIntegerRandom = BigInteger.valueOf( rand.nextInt(exclusiveMax.intValue())); 
        while(checkIfNumberWasAlreadyGenerated(BigIntegerRandom)){
            BigIntegerRandom = BigInteger.valueOf( rand.nextInt(exclusiveMax.intValue()));
        }
        return BigIntegerRandom;
    }

    // f(x) = ... mod p
    private BigInteger f(BigInteger x, BigInteger p){
        BigInteger tmp,tmp2;
        BigInteger res = this.coefs[0];
        for(BigInteger i=BigInteger.ONE; i.compareTo(this.nCoefs)<=0; i=i.add(BigInteger.ONE)){
            tmp2 = x.modPow(i, p);
            tmp = mod(coefs[i.intValue()].multiply(tmp2),this.p);
            res = res.add( mod(tmp,this.p) );
        }
        return mod(res,p);
    }

    // s é o segredo
    public ArrayList<FunctionXAndY> encapsulateSecret(BigInteger s, BigInteger m, BigInteger n){
        //BigInteger s = sBI.BigIntegerValue();
        if(n.compareTo(m)>0){
            System.out.println("Erro: n tem de ser menor que m");
            return null;
        }
        if(n.compareTo(MAX_COEFS)>0){
            System.out.println("Erro: n tem de ser menor que " + MAX_COEFS);
            return null;
        }

        // 1) escolher primo p
        this.p = choosePrime(m, s);
        
        // 2) escolher alteatoriamente n-1 coeficientes BigIntegereiros positivos
        this.nCoefs = n.subtract( BigInteger.ONE );
        this.coefs[0] = s;
        for(BigInteger i=BigInteger.ONE; i.compareTo(n.subtract(BigInteger.ONE)) <=0; i=i.add(BigInteger.ONE)){
            this.coefs[i.intValue()] = generateBigInteger(this.p);
            //System.out.println("coef["+i+"]="+this.coefs[i.intValue()]);
        }

        // 3) calcular aleatoriamente 6 pontos
        ArrayList<FunctionXAndY> valuePair = new ArrayList<>(this.nCoefs.intValue());
        for(BigInteger i=BigInteger.ZERO; i.compareTo(m)<0; i=i.add(BigInteger.ONE)){
            valuePair.add(i.intValue(), 
                new FunctionXAndY(i.add(BigInteger.ONE), f(i.add(BigInteger.ONE), this.p))
            );
        }

        return valuePair;
    }

    private BigInteger lagrangePolynomialBigInterpolation(ArrayList<FunctionXAndY> valuePairs){
        BigInteger totalSum = BigInteger.ZERO;
        BigInteger xj, f_xj, xi, sumResult, multResult;
        BigInteger n = BigInteger.valueOf(valuePairs.size());
        BigInteger tmp;

        for(BigInteger j=BigInteger.ZERO; j.compareTo(n)<0; j=j.add(BigInteger.ONE)){
            sumResult = BigInteger.ZERO;
            xj = valuePairs.get(j.intValue()).x;
            f_xj = valuePairs.get(j.intValue()).y;

            // produtorio
            // o uso excessivo de mods é para evitar OVERFLOW
            multResult = BigInteger.ONE;
            for(BigInteger i=BigInteger.ZERO; i.compareTo(n)<0; i=i.add(BigInteger.ONE)){
                if(i.compareTo(j)!=0){
                    xi = valuePairs.get(i.intValue()).x;
                    tmp = extendedEuclideanAlgorithm(xi.subtract(xj), this.p);
                    multResult = multResult.multiply( mod(xi.multiply(tmp), this.p) );
                    multResult = mod(multResult, this.p);
                }
            }

            sumResult = mod(f_xj.multiply(multResult), this.p);
            totalSum = mod(totalSum.add(sumResult), this.p);
        }
        return mod(totalSum, this.p);
    }

    public BigInteger revealSecret(ArrayList<FunctionXAndY> valuePairs){
        return lagrangePolynomialBigInterpolation(valuePairs);
    }
}
