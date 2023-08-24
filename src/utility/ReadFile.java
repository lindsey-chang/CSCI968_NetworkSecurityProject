package utility;
import java.io.*;
public class ReadFile {
    public static String[] readPKSK(String csvFile){
        String[] keys = new String[2];
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                keys = line.split(csvSplitBy);
                //System.out.println("PK = " + keys[0] + "\nSK = " + keys[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keys;
    }

    public static String readPKSHA_1(String csvFile){
        String sha = "";
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                sha = line.split(csvSplitBy)[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sha;
    }

    public static String SearchPasswordSHA_1(String username,String csvFile)
    {
        String SplitBy = ",";
        String searchedPasswordSHA_1="";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // 使用逗号分隔
                String[] user = line.split(SplitBy);
                if (user[0].equals(username)) {
                    searchedPasswordSHA_1=user[1];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchedPasswordSHA_1;
    }


    public static void main(String[] args) {
        //readPKSK("./src/Alice/RSApksk.csv");
    }
}
