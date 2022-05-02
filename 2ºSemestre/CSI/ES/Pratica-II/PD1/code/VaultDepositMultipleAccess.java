import java.util.ArrayList;

public class VaultDepositMultipleAccess extends VaultDeposit {
    public String filename;
    public byte[] documentHash;
    public ArrayList<FunctionXAndY> shamirPointPairs;
    public byte[] usedInitializationVector;

    // para dar return na função depositDocumentForMultipleAccess do cofre
    VaultDepositMultipleAccess(String filename, byte[] documentHash, ArrayList<FunctionXAndY> shamirPointPairs, byte[] usedInitializationVector){
        this.filename = filename;
        this.documentHash = documentHash;
        this.shamirPointPairs = shamirPointPairs;
        this.usedInitializationVector = usedInitializationVector;
    }
}
