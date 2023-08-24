package netsecurity;
import java.security.SecureRandom;
import java.util.Base64;

public class SecRandom {

    public static byte[] secureRandom(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);
        return token;
    }

    public static void printRandomBytes(byte[] bytes)
    {
        for (byte b : bytes) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }
    public static String toBase64String(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64StringToBytes(String base64String){

        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        return decodedBytes;
    }


//    public static void main(String[] args) {
//        byte[] token = secureRandom();
//        printRandomBytes(token);
//
//        System.out.println(toBase64String(token));
//
//    }
}
