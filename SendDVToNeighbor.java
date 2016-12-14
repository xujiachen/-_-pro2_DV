import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class SendDVToNeighbor extends Thread {
    private IP neighborIP_;
    private Socket socket_;

    public SendDVToNeighbor(IP neighborIP) throws IOException {
        neighborIP_ = neighborIP;
        socket_ = new Socket();
        socket_.setKeepAlive(true);
        socket_.connect(new InetSocketAddress(Inet4Address.getByAddress(neighborIP.getBytes()), Router.Port_ListenDV));
        MyConsole.log("Connect with the neighbor (" + neighborIP.toString() + ").");
        MyConsole.log(neighborIP.toString() + "  port:" + socket_.getLocalPort());
        MyConsole.log("Now you can send DV route table to this neighbor.");
    }

    @Override
    public void run() {
        try {
            while (Router.isRunning) {
                Thread.sleep(5000);

                MyConsole.log("Running");

                if (!socket_.isConnected()) {
                    MyConsole.log("The connection with " + neighborIP_.toString() + " for send DV is closed");
                    break;
                }

                if (!Router.isTableRefresh) {
                    MyConsole.log("Sending");

                    PrintWriter writer = new PrintWriter(socket_.getOutputStream());
                    writer.write(Router.getTable().toString());
                    writer.write("\r\n");
                    writer.flush();
                    socket_.getOutputStream().flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    MyConsole.log(neighborIP_.toString() + " response:" + builder.toString());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            MyConsole.log("Cannot from Neighbor(" + neighborIP_.toString() + ")");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
