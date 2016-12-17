import org.json.JSONException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenDVThread extends Thread {
    @Override
    public void run() {
        MyConsole.log("Begin to listen neighbors.");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Router.Port_ListenDV);
            while (Router.isRunning) {
                Socket socket = serverSocket.accept();
                new DVServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class DVServerThread extends Thread {
        private Socket socket;

        DVServerThread(Socket socket_) {
            super();
            socket = socket_;
        }

        @Override
        public void run() {
            try {
                MyConsole.log("Receive a DV connection with " + socket.getRemoteSocketAddress().toString());
                while (Router.isRunning) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = reader.readLine();

                    if (line != null) {
                        MyConsole.log("Receive a DV route table from " + socket.getRemoteSocketAddress().toString());
                        new RouteTable(line).show();

                        String strIP = socket.getRemoteSocketAddress().toString().substring(1);

                        Router.getTable().addRoutesFromNeighbor(new RouteTable(line), new IP(strIP.substring(strIP.indexOf(':'))));

                        PrintWriter writer = new PrintWriter(socket.getOutputStream());
                        writer.write(Router.getLocalIP().toString() + " Receive!\r\n");
                        writer.flush();
                    } else {
                        MyConsole.log("line is null");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
