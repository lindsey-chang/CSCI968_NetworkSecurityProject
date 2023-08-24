import netsecurity.RSAKeyPairGenerator;
import utility.SaveFile;
import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import netsecurity.*;

public class Main {
    public static void main(String[] args) {

        String passwordPath="";
        String RSApkskPath="";
        String pkSHA_1_path="";
        // Determine whether it is currently in the NetwoekSecurityA1 directory or the src directory
        String currentDirPath = System.getProperty("user.dir");
        File currentDir = new File(currentDirPath);
        String lastDirectoryName = currentDir.getName();
        if(lastDirectoryName.equals("src"))
        {
            passwordPath="./Alice/password.csv";
            RSApkskPath="./Alice/RSApksk.csv";
            pkSHA_1_path="./Bob/pkSHA_1.csv";
        }else
        {
            passwordPath="./src/Alice/password.csv";
            RSApkskPath="./src/Alice/RSApksk.csv";
            pkSHA_1_path="./src/Bob/pkSHA_1.csv";
        }

        // Step 1:
        // Create a password file in Alice’s directory.
        System.out.println("Step 1: Create a password file in Alice’s directory.");
        String username="Bob";
        String password="t1234567";
        SaveFile.saveInPasswordFile(passwordPath, username, HashSHA_1.BytesToBase64String(HashSHA_1.getBytesSHA_1(password)));


        // Step 2:
        // Generate a public and private key pair for the Alice, and store the generated public and private key pair (pk, sk) in a key file under Alice’s directory.
        // Also, store H(pk) in a key file under Bob’s directory.

        System.out.println("\nStep 2: Generate a public and private key pair for the Alice, " +
                "\nand store the generated public and private key pair (pk, sk) in a key file under Alice’s directory." +
                "\nAlso, store H(pk) in a key file under Bob’s directory.");
        try {
            RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();

            PublicKey publickey = keyPairGenerator.getPublicKey();
            PrivateKey privateKey = keyPairGenerator.getPrivateKey();

            //System.out.println("Private Key: " + privateKey.toString());
            //System.out.println("Public Key: " + publickey.toString());

            // Save (pk,sk) in Alice
            SaveFile.saveInPKSKFile(RSApkskPath, publickey,privateKey);
            // Save H(pk) in Bob
            SaveFile.saveInPKSHA(pkSHA_1_path,publickey);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}