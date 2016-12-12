import java.io.IOException;
import java.net.Inet4Address;

class Main {
    public static void main(String[] args) throws IOException {
        Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
        IP myIP = new IP(address.getHostAddress());
        System.out.println("Local IP is "+myIP.toString());
        MyConsole.RunForAddNeighbor(myIP);
        LocalRouteTable.show();
    }
}
