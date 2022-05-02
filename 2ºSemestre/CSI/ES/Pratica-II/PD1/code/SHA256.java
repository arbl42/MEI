import java.security.MessageDigest;

public class SHA256 {

    public static byte[] hash(byte[] input){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception NoSuchAlgorithmException) {
            System.out.println("Erro ao criar instância SHA-256");
            return null;
        }
        md.update(input);
        return md.digest();
    }
}
