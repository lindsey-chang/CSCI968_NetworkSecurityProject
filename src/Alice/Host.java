package Alice;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Base64;
import netsecurity.*;
import utility.*;

public class Host {


    private static int hostPort = 7734;

    public static int getHostPort() {
        return hostPort;
    }

    public static void main(String[] args){
        String passwordPath="";
        String RSApkskPath="";
        // Determine whether it is currently in the NetwoekSecurityA1 directory or the src directory
        String currentDirPath = System.getProperty("user.dir");
        File currentDir = new File(currentDirPath);
        String lastDirectoryName = currentDir.getName();
        if(lastDirectoryName.equals("src"))
        {
            passwordPath="./Alice/password.csv";
            RSApkskPath="./Alice/RSApksk.csv";
        }else
        {
            passwordPath="./src/Alice/password.csv";
            RSApkskPath="./src/Alice/RSApksk.csv";
        }
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(7734);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("\nStep 3: Host is running and waiting for connection.");
        while(true) {

            // Host received Client's username
            StrSocketAddressPair pair = UDPUtil.UDPReceiveStrAddressMessage(serverSocket);
            InetSocketAddress clientSocketAddress = pair.getSocketAddress(); // Record the socket address of the client sending the usernamek
            String username = pair.getStr();
            System.out.println("There is a remote connection request from " + username);

            String[] pk_sk = ReadFile.readPKSK(RSApkskPath);
            String pk = pk_sk[0];
            String sk = pk_sk[1];
            byte[] pkBytes = Base64.getDecoder().decode(pk);

            // Host generates NA
            byte[] NA = SecRandom.secureRandom(); // NA is a 128-bit random string, 128/8=16, byte[] NA.length = 16
            byte[] sendData_pkBytes_NA = new byte[pkBytes.length + NA.length];
            System.arraycopy(pkBytes, 0, sendData_pkBytes_NA, 0, pkBytes.length);
            System.arraycopy(NA, 0, sendData_pkBytes_NA, pkBytes.length, NA.length);

            //  Host sends the (pk,NA) message to Client
            UDPUtil.UDPSendMessage(serverSocket, sendData_pkBytes_NA, clientSocketAddress);
            System.out.println("\nProtocol Step 2: A sends B:pk,NA");

            // Host receives RSA(pk,OTP) and decrypt the OTP need same Client SocketAddress
            byte[] encryptedOTP = UDPUtil.UDPReceiveBytesMessage(serverSocket);

            // decrypt the OTP from client
            byte[] clientOTP = new byte[0];
            clientOTP = Base64.getDecoder().decode(RSAUtil.decrypt(encryptedOTP, sk));

            // calculate the OTP from myself
            // OTP is a one-time password which is computed as OTP = H(H(pw), NA)
            String notification = "";
            String pw_SHA_1_str = ReadFile.SearchPasswordSHA_1(username,passwordPath);
            if (pw_SHA_1_str.equals("")) {
                // if do not search the username in the password file
                notification = "Fail";
            } else {
                byte[] pw_SHA_1 = Base64.getDecoder().decode(pw_SHA_1_str);
                byte[] temp1 = new byte[pw_SHA_1.length + NA.length];
                System.arraycopy(pw_SHA_1, 0, temp1, 0, pw_SHA_1.length);
                System.arraycopy(NA, 0, temp1, pw_SHA_1.length, NA.length);
                byte[] hostOTP = HashSHA_1.getBytesSHA_1(Base64.getEncoder().encodeToString(temp1));

                if (Arrays.equals(clientOTP, hostOTP)) {
                    // Success
                    notification = "Success";
                } else {
                    // Fail
                    notification = "Fail";
                }
            }

            // Alice sends the notification to Client
            UDPUtil.UDPSendMessage(serverSocket, notification, clientSocketAddress);
            System.out.println("\nProtocol Step 4: A sends B:Success/Fail");
            System.out.println("==================================");
        }

    }
}
