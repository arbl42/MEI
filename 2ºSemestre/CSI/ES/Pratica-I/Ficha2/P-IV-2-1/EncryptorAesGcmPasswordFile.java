import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class EncryptorAesGcmPasswordFile {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128; // tag size must be 128
    private static final int IV_LENGTH_BYTE = 12; // iv size must be 12
    private static final int SALT_LENGTH_BYTE = 16;

    public static byte[] encrypt(byte[] pText, String password) throws Exception {

        // 16 bytes salt
        byte[] salt = CryptoUtils.getRandomNonce(SALT_LENGTH_BYTE);

        // GCM recommended 12 bytes iv
        byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE); // random initial vector

        // secret key from password
        SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        // AES-GCM needs GCMParameterSpec
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(pText);

        // prefix IV and Salt to cipher text
        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                .put(iv)
                .put(salt)
                .put(cipherText)
                .array();

        return cipherTextWithIvSalt;

    }

    // we need the same password, salt and iv to decrypt it
    private static byte[] decrypt(byte[] cText, String password) throws Exception, AEADBadTagException  {

        // get back the iv and salt that was prefixed in the cipher text
        ByteBuffer bb = ByteBuffer.wrap(cText);

        byte[] iv = new byte[12];
        bb.get(iv);

        byte[] salt = new byte[16];
        bb.get(salt);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        // get back the aes key from the same password and salt
        SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] plainText = cipher.doFinal(cipherText);

        return plainText;

    }

    public static void encryptFile(String fromFile, String toFile, String password) throws Exception {

        // read a normal txt file
        byte[] fileContent = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(fromFile).toURI()));

        // encrypt with a password
        byte[] encryptedText = EncryptorAesGcmPasswordFile.encrypt(fileContent, password);

        // save a file
        Path path = Paths.get(toFile);

        Files.write(path, encryptedText);

    }

    public static void decryptFile(String fromEncryptedFile, String toFile, String password) throws Exception {

        // read a file
        byte[] fileContent = Files.readAllBytes(Paths.get(fromEncryptedFile));

        byte[] decryptedText = EncryptorAesGcmPasswordFile.decrypt(fileContent, password);
        
        Path path = Paths.get(toFile);

        Files.write(path, decryptedText);

    }

    public static void main(String[] args) throws Exception, AEADBadTagException {
        
        Scanner scanner = new Scanner(System. in);

        System.out.println("\nPlease insert your password:\n");
        String password = scanner.nextLine();
        System.out.println("\nPlease insert your message file (path):\n");
        String fromFile = scanner.nextLine();
        System.out.println("\nPlease insert your output file (path):\n");
        String toFile = scanner.nextLine();


        System.out.println("Please select one of the options: \n \t 1 - encrypt\n\t 2 - decrypt\n");
        int choice = scanner.nextInt();

        do {
            switch (choice){
                case 1:
                    // encrypt file

                    EncryptorAesGcmPasswordFile.encryptFile(fromFile, toFile, password);
                    break;
                case 2:
                    // decrypt file
                    EncryptorAesGcmPasswordFile.decryptFile(fromFile, toFile, password);
                    break;
              
          
            } 
        } while (choice<1 || choice>2);

        scanner.close();

    }

}

