package netsecurity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import utility.*;

public class HashSHA_1 {

    // Java program to calculate SHA-1 hash value
    // https://www.geeksforgeeks.org/sha-1-hash-in-java/

    public static byte[] getBytesSHA_1(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // System.out.println(messageDigest.length);
            return messageDigest;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String BytesToBase64String(byte[] messageDigest)
    {
        return Base64.getEncoder().encodeToString(messageDigest);
    }

    public static byte[] Base64StringToBytes(String base64String)
    {
        return Base64.getDecoder().decode(base64String);
    }

}





