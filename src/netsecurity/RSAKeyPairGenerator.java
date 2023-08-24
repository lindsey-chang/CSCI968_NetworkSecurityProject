package netsecurity;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.math.BigInteger;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

// Reference:
// https://www.devglan.com/java8/rsa-encryption-decryption-java

public class RSAKeyPairGenerator {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private BigInteger modulus;

    private BigInteger publicExponent;
    private BigInteger privateExponent;

    public RSAKeyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey =  pair.getPrivate();
        this.publicKey =  pair.getPublic();
        RSAPublicKey pk=(RSAPublicKey)publicKey;
        RSAPrivateKey sk=(RSAPrivateKey)privateKey;

        this.modulus=pk.getModulus();
        this.publicExponent=pk.getPublicExponent();
        this.privateExponent=sk.getPrivateExponent();

    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    public BigInteger getPublicExponent() {
        return publicExponent;
    }

    public BigInteger getPrivateExponent() {
        return privateExponent;
    }
    public BigInteger getModulus() {
        return modulus;
    }

    public static void validatePrivateKey(String base64PrivateKey) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            System.out.println("Private key is valid");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Invalid private key");
            e.printStackTrace();
        }
    }

    public static void checkBase64Encoding(String base64PrivateKey) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(base64PrivateKey);
            System.out.println("Private key is correctly encoded in Base64");
        } catch (IllegalArgumentException e) {
            System.out.println("Private key is not correctly encoded in Base64");
            e.printStackTrace();
        }
    }

}
