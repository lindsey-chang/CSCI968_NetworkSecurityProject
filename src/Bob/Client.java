package Bob;

import Alice.Host;

import java.io.File;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Base64;
import java.util.Scanner;
import netsecurity.*;
import utility.*;
import java.util.Arrays;


public class Client {
    private static int clientPort = 5555;
    private static int hostPort = Host.getHostPort();

    public static void main(String[] args){

        String pkSHA_1_path="";
        // Determine whether it is currently in the NetwoekSecurityA1 directory or the src directory
        String currentDirPath = System.getProperty("user.dir");
        File currentDir = new File(currentDirPath);
        String lastDirectoryName = currentDir.getName();
        if(lastDirectoryName.equals("src"))
        {
            pkSHA_1_path="./Bob/pkSHA_1.csv";
        }else
        {
            pkSHA_1_path="./src/Bob/pkSHA_1.csv";
        }

        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket(clientPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("\nStep 4: Client asks for input for username and password from user.");

            Scanner sc = new Scanner(System.in);
            System.out.println("input username:");
            String username = sc.nextLine();
            System.out.println("input password:");
            String password = sc.nextLine();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", hostPort);

            System.out.println("Step 5: Client and Host perform the identification protocol");
            // Client sends the first message containing Bob’s identity to Host
            // Client sends the first message containing Bob’s identity("Bob") to Host
            UDPUtil.UDPSendMessage(clientSocket, username, hostAddress);
            System.out.println("\nProtocol Step 1: B sends A:Username");

            // Client receives (pk,NA)
            byte[] receiveData_pk_NA = UDPUtil.UDPReceiveBytesMessage(clientSocket);

            // split receiveData to get pk and NA
            byte[] pkBytes = new byte[receiveData_pk_NA.length - 16];
            byte[] NA = new byte[16];
            System.arraycopy(receiveData_pk_NA, 0, pkBytes, 0, pkBytes.length);
            System.arraycopy(receiveData_pk_NA, pkBytes.length, NA, 0, NA.length);

            //Client checks whether H(pk) matches the stored value in Bob’s key file
            byte[] pkHost_SHA_1 = HashSHA_1.getBytesSHA_1(Base64.getEncoder().encodeToString(pkBytes));
            byte[] pkClient_SHA_1 = HashSHA_1.Base64StringToBytes(ReadFile.readPKSHA_1(pkSHA_1_path));

            if(Arrays.equals(pkHost_SHA_1,pkClient_SHA_1))
            {
                // compute OTP and send the RSA(pk, OTP) message
                // OTP is a one-time password which is computed as OTP = H(H(pw), NA)
                byte[] pw_SHA_1 = HashSHA_1.getBytesSHA_1(password);
                byte[] temp1 = new byte[pw_SHA_1.length+NA.length];
                System.arraycopy(pw_SHA_1,0, temp1, 0, pw_SHA_1.length);
                System.arraycopy(NA, 0, temp1, pw_SHA_1.length, NA.length);

                byte[] OTP= HashSHA_1.getBytesSHA_1(Base64.getEncoder().encodeToString(temp1));

                // encrypt by RSA(pk, OTP)
                byte[] encryptedOTP = RSAUtil.encrypt(Base64.getEncoder().encodeToString(OTP), pkBytes);
                // Client sends RSA(pk, OTP) to Host
                UDPUtil.UDPSendMessage(clientSocket,encryptedOTP,hostAddress);
                System.out.println("\nProtocol Step 3: B sends A:RSA(pk, OTP)");

                // Client receive notification
                String notificaton = UDPUtil.UDPReceiveStrMessage(clientSocket);

                System.out.println(notificaton);
                System.out.println("==================================");
            }
            else
            {
                // if pkHost_SHA_1,pkClient_SHA_1 not equal, terminate the communication
                clientSocket.close();
            }

        }
    }
}
