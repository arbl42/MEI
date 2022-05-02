import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Scanner;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

 
public class App {
    public static KeyPair generateKeyPair()
        throws GeneralSecurityException
    {
        KeyPairGenerator keyPair = KeyPairGenerator.getInstance("RSA", "BC");
        keyPair.initialize(new RSAKeyGenParameterSpec(4096, RSAKeyGenParameterSpec.F4));
        return keyPair.generateKeyPair();
    }

    public static byte[] generatePssSignature(PrivateKey rsaPrivate, byte[] input)
        throws GeneralSecurityException
    {
        Signature signature = Signature.getInstance("SHA3-256withRSAandMGF1", "BC");
        signature.initSign(rsaPrivate);
        signature.update(input);
        return signature.sign();
    }
    
    public static boolean verifyPssSignature(PublicKey rsaPublic, byte[] input, byte[] encSignature)
        throws GeneralSecurityException
    {
        Signature signature = Signature.getInstance("SHA3-256withRSAandMGF1", "BC");
        signature.initVerify(rsaPublic);
        signature.update(input);
        return signature.verify(encSignature);
    }    

    public static void main(String[] args) throws GeneralSecurityException, IOException, NoSuchFileException {

        //add Provider to the Project
        Security.addProvider(new BouncyCastleProvider());
        String providerName = "BC";
        Provider provider = Security.getProvider(providerName);
        if (provider == null) {
            System.out.println(providerName + " provider not installed");
            return;
        }
        
        //verify if provider is installed
        System.out.println("Provider Name :"+ provider.getName());
        System.out.println("Provider Info :" + provider.getInfo());


        Scanner scanner = new Scanner(System. in);

        System.out.println("\nPlease insert your file path:\n");
        String fromFile = scanner.nextLine();

        FileInputStream fis = new FileInputStream(fromFile);
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
	        sb.append(new String(buffer));
	        buffer = new byte[10];
        }
        fis.close();

        String content = sb.toString();

        System.out.println("\nFile Content (plaintext): \n" + content);

        KeyPair keypair = generateKeyPair();
        PrivateKey privateKey = keypair.getPrivate();
        PublicKey publicKey = keypair.getPublic();

        byte[] fileContent = content.getBytes(Charset.forName("UTF-8")); 

        byte[] signature = generatePssSignature(privateKey, fileContent);

        System.out.println("\nFile content: "+fileContent);

        System.out.println("\nFile Signature: " + signature.toString());

        if (verifyPssSignature(publicKey, fileContent, signature)){
            System.out.println("\nThe signature is valid!\n");
        } else {
            System.out.println("\nInvalid signature.\n");
        }


        scanner.close();


    }
}