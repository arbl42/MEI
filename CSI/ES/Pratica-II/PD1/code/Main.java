import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class Main {

    public static String readFromFile(String input_filename){
        input_filename += ".txt";
        String data = "";
        try {
            File myObj = new File(input_filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file " + input_filename);
        }
        return data;
    }
    public static void main(String[] args) { // actualMain
        // Gerar instância do cofre
        Vault vault = new Vault();

        // Criar quem deposita o ficheiro e quem o pode aceder
        Person luis = new Person("Luis");
        Person rui = new Person("Rui");
        
        // registar no cofre
        vault.registerUser(luis);

        // Depositar o documento
        luis.depositDocument(vault);
        
        // Quem acede - tentar aceder
        String originalDocument;
        originalDocument = luis.accessDocument(vault, "test");
        if(originalDocument != null){
            System.out.println("O documento original é " + originalDocument);
        }

        // Tentar aceder
        originalDocument = rui.accessDocument(vault, "test");
        if(originalDocument != null)
            System.out.println("O documento original é " + originalDocument);
        
        try{
            Thread.sleep(20000);
        }catch(Exception e){
            System.out.println("Erro no sleep");
        }

        originalDocument = luis.accessDocument(vault, "test");
        if(originalDocument != null){
            System.out.println("O documento original é " + originalDocument);
        }

        /*
        vault.registerUser(rui);
        originalDocument = rui.accessDocument(vault, "test");
        if(originalDocument != null)
            System.out.println("O documento original é " + originalDocument);
        */

        /*
        // Inserir documento com Shamir
        luis.depositDocumentShamir(vault, new ArrayList<>(), 1);*/
    }

    public static void mainTesteProvider(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println("Get security provider");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        System.out.println("provider for RSA encryption: " + kf.getProvider());

        Cipher cipherAES = Cipher.getInstance("AES/GCM/NoPadding");
        System.out.println("provider for AES/GCM/NoPadding: " + cipherAES.getProvider());
    }

    public static void mainTesteAES(String[] args){ // testar cifra e decifra com AES
        SecureRandom random = new SecureRandom();
        byte[] keyToCipherDocument = new byte[AES.getKeyByteSize()];
        random.nextBytes(keyToCipherDocument);
        
        byte[] ivBytes = new byte[AES.getIVByteSize()];;
        random.nextBytes(ivBytes);

        String ciphertext = AES.encrypt("Yooo", keyToCipherDocument, ivBytes);
        System.out.println("Decryption: " + AES.decrypt(ciphertext, keyToCipherDocument, ivBytes));
    }

    public static void mainTesteRSA(String[] args){ // testar cifra e decifra com RSA
        RSAKeys keys = RSA.generateKeys();
        PrivateKey privKey = keys.privateKey;
        PublicKey pubKey = keys.publicKey;

        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[AES.getIVByteSize()];;
        random.nextBytes(ivBytes);

        byte[] in = "1234".getBytes(StandardCharsets.UTF_8);
        BigInteger out = RSA.encrypt(in, pubKey);
        System.out.println(
            RSA.decrypt(out, privKey)
        );
    }

    public static void mainTesteShamir(String[] args){ // mainTesteShamir
        Shamir shamirInstance = new Shamir();
        //BigInteger s = new BigInteger("1234");
        BigInteger s = BigInteger.valueOf(12345678);
        BigInteger m = BigInteger.valueOf(6);
        BigInteger n = BigInteger.valueOf(3);
        ArrayList<FunctionXAndY> out = shamirInstance.encapsulateSecret(s, m, n);
        /*for(FunctionXAndY xAndY: out){
            System.out.println("x = " + xAndY.x + " f(x) = " + xAndY.y);
        }*/
        
        ArrayList<FunctionXAndY> out245 = new ArrayList<>();
        out245.add(out.get(1));
        out245.add(out.get(3));
        out245.add(out.get(4)); 
        System.out.println("Original is " + shamirInstance.revealSecret(out245));
    }

    public static void mainTesteCertificado(String[] args){ // mainTesteCertificado
        /*ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 365 -out rootCA.crt -subj \"/C=PT/O=Grupo 3/OU=Departamento de Informática/CN=Entidade de Certificação Grupo 3\"");
        */

        Process p;
        Runtime r=Runtime.getRuntime();

        // CA - Geração de par de chaves RSA com 4096 bits
        //      Criação de certificado auto-assinado com validade de 365 dias
        String command;
        try{
            command = "./scripts/openssl_create_root_cert.sh";
            p=r.exec(command);
            p.waitFor();

            command = "./scripts/openssl_verify.sh";
            p=r.exec(command);
            p.waitFor();

            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
