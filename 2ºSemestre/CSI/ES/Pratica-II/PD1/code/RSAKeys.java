import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAKeys {
    public PrivateKey privateKey;
    public PublicKey publicKey;

    RSAKeys(PrivateKey privateKey, PublicKey publicKey){
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}
