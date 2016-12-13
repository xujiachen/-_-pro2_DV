import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendDVToNeighbor extends Thread {
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (true) {
           if(System.currentTimeMillis() - time >= 5000) {
               time = System.currentTimeMillis();
               if (Router.isTableRefresh) continue;
               for (IP neighborIP : Router.getNeighbors()) {
                   try {
                       Socket socket = new Socket();
                       socket.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), Router.DVport));
                       socket.connect(new InetSocketAddress(neighborIP.toString(), Router.DVport));

                       OutputStream os = socket.getOutputStream();
                       PrintWriter pw = new PrintWriter(os);
                       pw.write(Router.getTable().toString());
                       pw.flush();
                       pw.close();
                       socket.shutdownOutput();
                       MyConsole.log("Send a DV to Neighbor " + neighborIP.toString() + ".");

                       InputStream is = socket.getInputStream();
                       BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                       String line;
                       StringBuilder builder = new StringBuilder();
                       while ((line = reader.readLine()) != null) {
                           builder.append(line);
                       }

                       MyConsole.log("Neighbor " + neighborIP.toString() + " received.");
                       MyConsole.log("Neighbor response: " + builder.toString());

                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
        }

    }
}
