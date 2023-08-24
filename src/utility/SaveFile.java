package utility;
import netsecurity.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class SaveFile {

    // save password hash to Alice
    public static void saveInPasswordFile(String filePath, String client, String pwHashCode) {


        // Change the second constructor parameter of FileWriter from true (for append) to false (for overwrite).
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Build CSV row data
            String csvLine = client + "," + pwHashCode;

            // write to CSV file
            writer.write(csvLine);
            writer.newLine();

            // flush the buffer
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //save RSA (pk,sk) to Alice
    public static void saveInPKSKFile(String filePath, PublicKey publicKey, PrivateKey privateKey){
        String pk = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String sk = Base64.getEncoder().encodeToString(privateKey.getEncoded());


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Build CSV row data
            String csvLine = pk + "," + sk;

            // write to CSV file
            writer.write(csvLine);
            writer.newLine();

            // flush the buffer
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveInPKSHA(String filePath, PublicKey publicKey){
        String pk = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String pkSHA_1 = HashSHA_1.BytesToBase64String(HashSHA_1.getBytesSHA_1(pk));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Build CSV row data
            String csvLine = pkSHA_1;

            // write to CSV file
            writer.write(csvLine);
            writer.newLine();

            // flush the buffer
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        //System.out.println(new File(".").getAbsolutePath());

    }
}
