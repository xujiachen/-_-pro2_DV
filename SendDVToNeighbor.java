import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class SendDVToNeighbor extends Thread {
    private List<IP> NeighborList;

    public SendDVToNeighbor(List<IP> IPList) {
        super();
        NeighborList = new LinkedList<>();
        for (IP ip : IPList) {
            NeighborList.add(ip);
        }
    }

    @Override
    public void run() {
        System.out.print("RUN");
        for (IP neighborIP : NeighborList) {
            try {
                Socket socket = new Socket(neighborIP.toString(), Router.DVPORT);

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
                while ((line = reader.readLine()) != null) {
                    MyConsole.log("Neighbor " + neighborIP.toString() + " received.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
