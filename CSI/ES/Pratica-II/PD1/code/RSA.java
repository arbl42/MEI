import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSA {

    public static RSAKeys generateKeys(){
        KeyPairGenerator kpg;

        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao instanciar RSA...");
            return null;
        }

        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        return new RSAKeys(
            kp.getPrivate(),
            kp.getPublic()
        );
    }

    public static BigInteger encrypt(byte[] secretMessageBytes, PublicKey publicKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedMessageBytes = cipher.doFinal(secretMessageBytes);
            String encryptedMessageString = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            return StringToInt.convertStringToInt(encryptedMessageString);
        } catch(Exception e){
            System.err.println("Ocorreu um erro ao cifrar com RSA..");
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(BigInteger data, PrivateKey privateKey) {
        String encryptedMessageString = StringToInt.convertIntToString(data);
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessageString);
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessageBytes = cipher.doFinal(encryptedMessageBytes);
            return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch(Exception e){
            System.err.println("Ocorreu um erro ao decifrar com RSA...");
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptBytes(BigInteger data, PrivateKey privateKey) {
        String encryptedMessageString = StringToInt.convertIntToString(data);
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(encryptedMessageString);
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedMessageBytes);
        } catch(Exception e){
            System.err.println("Ocorreu um erro ao decifrar com RSA...");
            e.printStackTrace();
            return null;
        }
    }
}
