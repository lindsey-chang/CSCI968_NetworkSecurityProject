package utility;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UDPUtil {


    public static StrSocketAddressPair UDPReceiveStrAddressMessage(DatagramSocket socket){
        byte[] receiveData = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InetSocketAddress receivedSocketAddress = (InetSocketAddress) receivePacket.getSocketAddress(); // To be created after receiving packets

        // Host received Client's username
        String s = new String(receivePacket.getData(),0,receivePacket.getLength());
        StrSocketAddressPair saPair=new StrSocketAddressPair(s,receivedSocketAddress);
        return saPair;

    }
    public static String UDPReceiveStrMessage(DatagramSocket socket)
    {
        return UDPReceiveStrAddressMessage(socket).getStr();
    }

    public static byte[] UDPReceiveBytesMessage(DatagramSocket socket){
        byte[] receiveData = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        receiveData=new byte[receivePacket.getLength()]; // suitable length not 2048
        System.arraycopy(receivePacket.getData(),receivePacket.getOffset(),receiveData,0,receivePacket.getLength());

        return receiveData;
    }

    public static void UDPSendMessage(DatagramSocket socket, byte[] sendData, InetSocketAddress address)
    {
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,address);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void UDPSendMessage(DatagramSocket socket, String sendStr, InetSocketAddress address)
    {
        UDPSendMessage(socket,sendStr.getBytes(),address);
    }
}
