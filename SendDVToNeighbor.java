import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendDVToNeighbor extends Thread {
    private IP neighborIP_;
    private Socket socket_;

    public SendDVToNeighbor(IP neighborIP) throws IOException {
        neighborIP_ = neighborIP;
        socket_ = new Socket();
        socket_.connect(new InetSocketAddress(neighborIP.toString(), Router.Port_ListenDV));
        MyConsole.log("Connect with the neighbor (" + neighborIP.toString() + ").");
        MyConsole.log(neighborIP.toString() + "  port:" + socket_.getLocalPort());
        MyConsole.log("Now you can send DV route table to this neighbor.");
    }

    @Override
    public void run() {
        try {
            System.out.print("Run 3!");
            while (Router.isRunning) {
                Thread.sleep(5000);

                if (!Router.isTableRefresh) {
                    PrintWriter writer = new PrintWriter(socket_.getOutputStream());
                    writer.write(Router.getTable().toString());
                    writer.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    MyConsole.log(neighborIP_.toString() + " response:" + builder.toString());
                }
            }
            System.out.print("Exit 3!");
        } catch (IOException e) {
            MyConsole.log("Cannot from Neighbor(" + neighborIP_.toString() + ")");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
