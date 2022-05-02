import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static String encrypt(String plaintext, byte[] key, byte[] iv) {
        byte[] secretMessageBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        // usar funcao de derivacao
        try {
            Cipher jce = Cipher.getInstance("AES/CBC/PKCS5Padding");
            jce.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] encryptedMessageBytes = jce.doFinal(secretMessageBytes);
            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (Exception e) {
            System.err.println("Setup failure:  ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static String decrypt(String ciphertext, byte[] key, byte[] iv) {
        byte[] encryptedMessageBytes = Base64.getDecoder().decode(ciphertext);
        try {
            Cipher jce = Cipher.getInstance("AES/CBC/PKCS5Padding");
            jce.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] decryptedMessageBytes = jce.doFinal(encryptedMessageBytes);
            return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.err.println("Setup failure:  ");
            e.printStackTrace();
        }
        return null;
    }

    // 128/192/256 bits = 16/24/32 bytes
    public static int getKeyByteSize(){
        return 32;
    }

    public static int getIVByteSize(){
        return 16;
    }
}
