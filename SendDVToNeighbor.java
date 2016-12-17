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
    }

    @Override
    public void run() {
        try {
            socket_.connect(new InetSocketAddress(Inet4Address.getByAddress(neighborIP_.getBytes()), Router.Port_ListenDV));
            MyConsole.log("Connect with the neighbor (" + neighborIP_.toString() + "  port:" + socket_.getLocalPort() + ").");
            MyConsole.log("Now you can send DV route table to this neighbor.");

            while (Router.isRunning) {
                Thread.sleep(5000);

                if (!socket_.isConnected()) {
                    MyConsole.log("The connection with " + neighborIP_.toString() + " for send DV is closed");
                    break;
                }

                if (!Router.isTableRefresh) {
                    MyConsole.log("Sending a DV to neighbor ("+ neighborIP_.toString() +")");

                    PrintWriter writer = new PrintWriter(socket_.getOutputStream());
                    writer.write(Router.getTable().toString());
                    writer.write("\r\n");
                    writer.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket_.getInputStream()));
                    String line = reader.readLine();
                    MyConsole.log(neighborIP_.toString() + " response:" + line);

                    if (line == null) {
                        MyConsole.log(neighborIP_.toString() + " response is null, close the connection with it.");
                        break;
                    }
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
