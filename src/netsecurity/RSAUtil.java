package netsecurity;
import java.security.spec.*;
import java.security.*;
import java.util.Base64;
import javax.crypto.*;

// Reference:
// https://www.devglan.com/java8/rsa-encryption-decryption-java

public class RSAUtil {

    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static byte[] encrypt(String data, String base64PublicKey) {
        byte[] cipherBytes=null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(base64PublicKey));
            cipherBytes=cipher.doFinal(data.getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return cipherBytes;
    }

    public static byte[] encrypt(String data, byte[] bytesPublicKey){
        String base64PublicKey = Base64.getEncoder().encodeToString(bytesPublicKey);
        return encrypt(data, base64PublicKey);
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }
    public static String decrypt(String data, String base64PrivateKey){
        String plaintext="";
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(base64PrivateKey));
            plaintext = new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return plaintext;
    }
    public static String decrypt(String data, byte[] bytesPrivateKey) {
        String base64PrivateKey = Base64.getEncoder().encodeToString(bytesPrivateKey);
        return decrypt(data, base64PrivateKey);
    }

    public static String decrypt(byte[] data, byte[] bytesPrivateKey){
        String base64PrivateKey = Base64.getEncoder().encodeToString(bytesPrivateKey);
        String base64Data = Base64.getEncoder().encodeToString(data);
        return decrypt(base64Data, base64PrivateKey);
    }

    public static String decrypt(byte[] data, String base64PrivateKey){
        String base64Data = Base64.getEncoder().encodeToString(data);
        return decrypt(base64Data, base64PrivateKey);
    }

}
