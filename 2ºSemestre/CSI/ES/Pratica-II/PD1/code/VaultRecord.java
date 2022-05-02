import java.security.PublicKey;
import java.util.ArrayList;

public class VaultRecord {
    public String filename;
    public byte[] documentHash;
    public String cipheredDocument;
    public byte[] initializationVector;
    public Person owner; // identificados pela chave pública
    public boolean shamirUsed;

    VaultRecord(String filename, byte[] documentHash, String cipheredDocument, byte[] initializationVector, Person owner, boolean shamirUsed){
        this.filename = filename;
        this.documentHash = documentHash;
        this.cipheredDocument = cipheredDocument;
        this.initializationVector = initializationVector;
        this.owner = owner;
        this.shamirUsed = shamirUsed;
    }


}