/*
 * Router class
 * used to save the message of the router
 */

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class Router {
    private Router() {
    }

    static int DVPORT = 8000;
    static int MESSAGEPORT = 8001;

    static private IP LocalIP;
    static private RouteTable table;
    static private List<IP> Neighbors;

    static boolean init() {
        try {
            Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
            LocalIP = new IP(address.getHostAddress());
            Neighbors = new LinkedList<>();
            table = new RouteTable();

            System.out.println("Local IP is " + LocalIP.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static void addNeighbor(IP neighborIP) {
        for (IP ip : Neighbors) {
            if (ip.equals(neighborIP))
                return;
        }

        Neighbors.add(neighborIP);
        table.AddRoute(new RouteEntry(neighborIP, neighborIP, 1));
    }

    static List<IP> getNeighbors() {
        return Neighbors;
    }

    static RouteTable getTable() {
        return table;
    }

    static IP getLocalIP() {
        return LocalIP;
    }
}
