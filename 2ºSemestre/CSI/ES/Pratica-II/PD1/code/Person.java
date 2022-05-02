import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class Person {
    public String name;

    private static final int maxCrtFilenameLength = 10;
    public String crtFilename;
    public String publicKeyFilename;
    public String privateKeyFilename;
    public PublicKey publicKey;
    private PrivateKey privateKey;
    ArrayList<VaultDepositOneAccess> depositOutputOneAccess = new ArrayList<>();
    ArrayList<VaultDepositMultipleAccess> VaultDepositMultipleAccess = new ArrayList<>();
    

    Person(String name) {
        this.name = name;
        
        SecureRandom random = new SecureRandom();
        byte[] crtFilenameBytes = new byte[maxCrtFilenameLength];
        random.nextBytes(crtFilenameBytes);
        String tmp = Base64.getEncoder().encodeToString(crtFilenameBytes);
        this.crtFilename = tmp.replace("/", "");
        System.out.println("User " + this.name + " has certificate name " + this.crtFilename);

        // executar ficheiros bash para criar chaves publica e privada
        Process p;
        Runtime r=Runtime.getRuntime();
        String command;
        this.publicKeyFilename = "pubKey_" + this.crtFilename;
        this.privateKeyFilename = "privKey_" + this.crtFilename;
        try{
            command = "./scripts/openssl_create_user_keys.sh " + this.crtFilename + " " + this.publicKeyFilename + " " + this.privateKeyFilename;
            System.out.println(command);
            p=r.exec(command);
            p.waitFor();
        }catch(Exception e){

        }

        // buscar public key e private key
        try{
            byte[] keyBytes = Files.readAllBytes(Paths.get("certificados/" + this.publicKeyFilename + ".der"));

            X509EncodedKeySpec spec =
            new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.publicKey = kf.generatePublic(spec);

            keyBytes = Files.readAllBytes(Paths.get("certificados/" + this.privateKeyFilename + ".der"));

            PKCS8EncodedKeySpec spec2 =
            new PKCS8EncodedKeySpec(keyBytes);
            this.privateKey = kf.generatePrivate(spec2);
        }catch(Exception e){
            System.out.println("Erro fatal " + e);
        }

        
    }

    
    VaultDepositOneAccess depositDocument(Vault v){
        Scanner myObj = new Scanner(System.in);
        //System.out.println("Insira nome do ficheiro para depositar");
        //String filename = myObj.nextLine();  // Read user input
        String filename = "test";
        String content = Main.readFromFile(filename);
        Document document = new Document(filename, content);
        byte[] documentHash = SHA256.hash(document.content.getBytes());

        VaultDepositOneAccess depositOutput = v.depositDocument(this, document);
        depositOutput.documentHash = documentHash;
        this.depositOutputOneAccess.add(
            depositOutput
        );
        myObj.close();
        return depositOutput;
    }

    /*
    VaultDepositMultipleAccess depositDocumentShamir(Vault v, ArrayList<PublicKey> whoCanAccess, int n){
        Scanner myObj = new Scanner(System.in);
        //System.out.println("Insira nome do ficheiro para depositar");
        //String filename = myObj.nextLine();  // Read user input
        String filename = "test";
        String content = Main.readFromFile(filename);
        Document document = new Document(filename, content);

        VaultDepositMultipleAccess depositOutput = v.depositDocumentShamir(this.rsaKeys.publicKey, document, whoCanAccess, n);
        this.VaultDepositMultipleAccess.add(
            depositOutput
        );
        myObj.close();
        return depositOutput;
    }*/

    
    String accessDocument(Vault v, String filename){
        // obter chave do documento para aceder
        
        VaultDepositOneAccess depositOutputInstance = null;
        for(VaultDepositOneAccess depOut : this.depositOutputOneAccess){
            if(depOut.filename.equals(filename)){
                depositOutputInstance = depOut;
                break;
            }
        }
        if(depositOutputInstance == null){
            System.out.println("Erro: O " + this.name + " nao depositou um documento com o nome " + filename);
            return null;
        }
        
        
        BigInteger cipheredDocumentKey = depositOutputInstance.keyCipheredWithDepositantPublicKey;
        byte[] documentKey = RSA.decryptBytes(cipheredDocumentKey, this.privateKey);
        byte[] usedIV = depositOutputInstance.usedInitializationVector;
        byte[] documentHash = depositOutputInstance.documentHash;

        return v.accessDocument(this, filename, documentKey, usedIV, documentHash);
    }
}
