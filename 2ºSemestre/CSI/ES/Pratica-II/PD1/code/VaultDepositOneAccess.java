import java.math.BigInteger;

// é preciso esta classe para poder dar return correto do depósito no cofre
public class VaultDepositOneAccess extends VaultDeposit {
    public String filename;
    public byte[] documentHash;
    public BigInteger keyCipheredWithDepositantPublicKey;
    public byte[] usedInitializationVector;

    // para dar return na função Deposit do cofre
    VaultDepositOneAccess(String filename, byte[] documentHash, BigInteger keyCipheredWithDepositantPublicKey, byte[] usedInitializationVector){
        this.filename = filename;
        this.documentHash = documentHash;
        this.keyCipheredWithDepositantPublicKey = keyCipheredWithDepositantPublicKey;
        this.usedInitializationVector = usedInitializationVector;
    }
}
