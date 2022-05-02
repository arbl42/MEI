import java.math.BigInteger;


public class StringToInt {
    public static void main(String[] args) {

        String stringToConvert = "asdasdz";

        System.out.println(
            convertIntToString(
                convertStringToInt(stringToConvert)
            )
        );

    }

    public static BigInteger convertStringToInt(String str) {
        return convertHexToInt(
            convertStringToHex(str).toString()
        );
    }

    private static StringBuilder convertStringToHex(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            String charToHex = Integer.toHexString(c);
            stringBuilder.append(charToHex);
        }

        //System.out.println("Converted Hex from String: "+stringBuilder.toString());
        return stringBuilder;
    }


    private static BigInteger convertHexToInt(String hexStr){
        BigInteger value = new BigInteger(hexStr, 16);

        //System.out.println("Converted int from hex: " + value);
        return value;
    }

    public static String convertIntToString(BigInteger value){
        return convertHexToString(
            convertIntToHex(value)
        );
    }

    private static String convertIntToHex(BigInteger value){        

        String Hex = value.toString(16);

        //System.out.println("Converted hex from int: " + Hex);
        return Hex;
    }

    private static String convertHexToString(String hex){        

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }

        //System.out.println("Converted String from Hex: " + str);
        return str.toString();
    }
}
