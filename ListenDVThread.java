import org.json.JSONException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenDVThread extends Thread {
    @Override
    public void run() {
        System.out.print("Run 1!");
        MyConsole.log("Begin to listen neighbors.");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Router.Port_ListenDV);
            while (Router.isRunning) {
                Socket socket = serverSocket.accept();
                new DVServerThread(socket).start();
            }
            System.out.print("Exit 1!");
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
            System.out.print("Run 2!");
            try {
                MyConsole.log("Receive a hello from " + socket.getRemoteSocketAddress().toString());
                while (Router.isRunning) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    MyConsole.log("Receive a DV route table from " + socket.getRemoteSocketAddress().toString());
                    new RouteTable(builder.toString()).show();
                    Router.getTable().addRoutesFromNeighbor(new RouteTable(builder.toString()), new IP(socket.getRemoteSocketAddress().toString()));

                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    writer.write(Router.getLocalIP().toString() + " Receive!");
                    writer.flush();
                }
                System.out.print("Exit 2!");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
