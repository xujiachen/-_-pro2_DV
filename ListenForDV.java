import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenForDV extends Thread {
    @Override
    public void run() {
        MyConsole.log("Begin to listen neighbors.");
        try {
            ServerSocket serverSocket = new ServerSocket(Router.MESSAGEport);
            while (true) {
                Socket socket = serverSocket.accept();

                MyConsole.log(socket.getRemoteSocketAddress().toString());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                MyConsole.log("Receive: " + builder.toString());
                Router.getTable().addRoutes(new RouteTable(builder.toString()));
                new SendDVToNeighbor();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}