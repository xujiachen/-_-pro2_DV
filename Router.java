/*
 * Router class
 * used to save the message of the router
 */

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Router {
    private Router() {}

    static boolean isRunning = false;
    static int Port_ListenDV = 8000;
    static int Port_listenMessage = 8001;
    static boolean isTableRefresh = false;

    // Network with neighbors
    // static private Map<IP, Socket> socketsForNeighbors;
    static private Map<IP, SendDVToNeighbor> threadForSendToNeighbors;

    static private IP LocalIP;
    static private RouteTable table;
    static private List<IP> Neighbors;

    // router base operation

    static boolean init() {
        try {
            isRunning = true;

            Inet4Address address = (Inet4Address) Inet4Address.getLocalHost();
            LocalIP = new IP(address.getHostAddress());

            Neighbors = new LinkedList<>();
            table = new RouteTable();
            threadForSendToNeighbors = new HashMap<>();

            System.out.println("Local IP is " + LocalIP.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static IP getLocalIP() {
        return LocalIP;
    }

    static void close() {
        isRunning = false;
    }

    static void sendNewMessage(IP DestinationIP) {
        Message message = new Message();
        message.setSource(Router.getLocalIP());
        message.setDestination(DestinationIP);
        new ForwardMessage(message).start();
    }

    // operation with neighbor

    static void addNeighbor(IP neighborIP, int cost) {
        for (IP ip : Neighbors) {
            if (ip.equals(neighborIP))
                return;
        }

        // Add a new thread used to send DV for the new neighbor, and
        // build a connection with the new neighbor but there is no
        // message exchanging.
        // If the neighbor is able to connected, add its route.

        try {
            SendDVToNeighbor threadForSend = new SendDVToNeighbor(neighborIP);
            threadForSendToNeighbors.put(neighborIP, threadForSend);

            // add route
            Neighbors.add(neighborIP);
            table.addRoute(new RouteEntry(neighborIP, neighborIP, cost));

        } catch (IOException e) {
            e.printStackTrace();
            MyConsole.log("Cannot connect to the neighbor " + neighborIP.toString() + ", please check your input!");
        }
    }

    static List<IP> getNeighbors() {
        return Neighbors;
    }

    static void BeginListenDV() {
        ListenDVThread listenDVThread = new ListenDVThread();
        listenDVThread.start();
    }

    static void BeginSendDV() {
        for (IP ip : Neighbors) {
            threadForSendToNeighbors.get(ip).start();
        }
    }

    static void BeginListenMessage() {
        new ListenForMessage().start();
    }

    // operation at local table

    static RouteTable getTable() {
        return table;
    }
}
