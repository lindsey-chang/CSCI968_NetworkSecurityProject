package utility;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class StrSocketAddressPair {

    private String str;
    private InetSocketAddress socketAddress;

    public StrSocketAddressPair(String str, InetSocketAddress socketAddress) {
        this.str = str;
        this.socketAddress = socketAddress;
    }


    public String getStr() {
        return str;
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }





}
