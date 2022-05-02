import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class Vault {
    private ArrayList<VaultRecord> vaultRecords = new ArrayList<>();

    Vault(){
        // executar ficheiros bash para criar entidade de certificacao
        Process p;
        Runtime r=Runtime.getRuntime();
        String command;
        try{
            command = "./scripts/openssl_create_root_cert.sh";
            p=r.exec(command);
            p.waitFor();
        }catch(Exception e){

        }
    }

    public void registerUser(Person per){
        // criar certificado para o utilizador
        Process p;
        Runtime r=Runtime.getRuntime();
        String command;

        try{
            command = "./scripts/openssl_certify.sh " + per.crtFilename;
            p=r.exec(command);
            p.waitFor();
        }catch(Exception e){

        }
    }

    
    public VaultDepositOneAccess depositDocument(Person owner, Document document){
        // gerar a chave de cifra aleatória
        SecureRandom random = new SecureRandom();
        byte[] keyToCipherDocument = new byte[AES.getKeyByteSize()];
        random.nextBytes(keyToCipherDocument);
        
        // gerar o vetor de inicialização (byte)
        byte[] ivBytes = new byte[AES.getIVByteSize()];;
        random.nextBytes(ivBytes);

        // fazer hash do documento
        byte[] documentHash = SHA256.hash(document.content.getBytes());
        
        // cifrar documento
        String cipheredDocument = AES.encrypt(document.content, keyToCipherDocument, ivBytes);

        // guardar no cofre
        vaultRecords.add(new VaultRecord(
            document.filename,
            documentHash,
            cipheredDocument,
            ivBytes,
            owner,
            false
        ));

        // cifrar chave cifra com chave pública do depositante
        BigInteger cipheredKey = RSA.encrypt(keyToCipherDocument, owner.publicKey);

        // depósito "normal", i.e. sem divisão por partes
        return new VaultDepositOneAccess(document.filename, documentHash, cipheredKey, ivBytes);
    }

    /*
    public VaultDepositMultipleAccess depositDocumentShamir(PublicKey ownerPublicKey, Document document, ArrayList<PublicKey> whoCanAccess, int n){
        // o n vai ser o número de pessoas que lhe podem aceder
        int m = whoCanAccess.size() + 1;
        
        // gerar a chave de cifra aleatória
        SecureRandom random = new SecureRandom();
        byte[] keyToCipherDocument = new byte[AES.getKeyByteSize()];
        random.nextBytes(keyToCipherDocument);
        
        // gerar o vetor de inicialização (byte)
        byte[] ivBytes = new byte[AES.getIVByteSize()];;
        random.nextBytes(ivBytes);

        // fazer hash do documento
        byte[] documentHash = SHA256.hash(document.content.getBytes());
        
        // cifrar documento
        String cipheredDocument = AES.encrypt(document.content, keyToCipherDocument, ivBytes);

        // adicionar depositante à lista de pessoas que podem aceder o documento
        whoCanAccess.add(ownerPublicKey);

        // guardar no cofre
        vaultRecords.add(new VaultRecord(
            document.filename,
            documentHash,
            cipheredDocument,
            ivBytes,
            whoCanAccess,
            true
        ));

        // cifrar chave cifra com chave pública do depositante
        BigInteger cipheredKey = RSA.encrypt(keyToCipherDocument, ownerPublicKey);

        // depósito à la Shamir
        // particionar a chave de cifra através do esquema de Shamir
        Shamir shamirInstance = new Shamir();
        ArrayList<FunctionXAndY> out = shamirInstance.encapsulateSecret(cipheredKey, BigInteger.valueOf(m), BigInteger.valueOf(n));

        return new VaultDepositMultipleAccess(document.filename, documentHash, out, ivBytes);
    }*/

    
    public String accessDocument(Person person, String filename, byte[] documentKey, byte[] usedIV, byte[] userHash){
        VaultRecord vaultRecordInstance = null;
        for(VaultRecord vr : this.vaultRecords){
            if(vr.filename.equals(filename)){
                vaultRecordInstance = vr;
                break;
            }
        }
        if(vaultRecordInstance == null){
            System.out.println("Erro: Não existe nenhum ficheiro com o nome " + filename + "guardado");
            return null;
        }
        if(!vaultRecordInstance.owner.publicKey.equals(person.publicKey)){
            System.out.println("Erro: Utilizador " + person.name + " não tem permissão para aceder");
            return null;
        }

        // validar certificado da pessoa
        Process p;
        Runtime r=Runtime.getRuntime();
        String command;
        try{
            command = "./scripts/openssl_verify.sh " + person.crtFilename;
            System.out.println(command);
            p=r.exec(command);
            p.waitFor();

            BufferedReader stdInput = new BufferedReader(new 
            InputStreamReader(p.getInputStream()));

            // Read the output from the command
            boolean ok=false;
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                if(s.contains("OK")){
                    ok=true;
                }
            }
            if(!ok){
                System.out.println("Não é possível autenticar utilizador...");
                return null;
            }

        }catch(Exception e){
            
        }

        
        if(!Arrays.equals(vaultRecordInstance.documentHash, userHash)){
            System.out.println("Erro: O documento não se manteve íntegro");
            return null;
        }

        return AES.decrypt(vaultRecordInstance.cipheredDocument, documentKey, usedIV);
    }
}