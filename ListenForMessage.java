import org.json.JSONException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenForMessage extends Thread {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Router.Port_listenMessage);
            while (Router.isRunning) {
                Socket socket = serverSocket.accept();

                InputStream is = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                try {
                    MyConsole.log("Receive a message from " + socket.getRemoteSocketAddress().toString());

                    Message message = new Message(builder.toString());
                    if (message.getDestination().equals(Router.getLocalIP())) {
                        MyConsole.log("The message's destination is me!");
                        MyConsole.log("The message has forwarded by these points:");
                        for (IP i : message.getPointList()) {
                            MyConsole.log(i.show());
                        }
                    } else {

                        OutputStream os = socket.getOutputStream();
                        PrintWriter writer = new PrintWriter(os);
                        writer.write("Receive your message!");
                        writer.flush();
                        writer.close();
                        socket.shutdownOutput();

                        MyConsole.log("Forwarding the message.");
                        new ForwardMessage(message).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
