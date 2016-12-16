import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ForwardMessage extends Thread {
    private Message message_;

    ForwardMessage(Message message) {
        super();
        message_ = message;
    }


    @Override
    public void run() {
        IP nextHop = Router.getTable().getNextHop(message_.getDestination());
        if (nextHop != null) {
            message_.addPoint(Router.getLocalIP());
            try {
                Socket socket = new Socket();

                socket.connect(new InetSocketAddress(Inet4Address.getByAddress(nextHop.getBytes()), Router.Port_listenMessage));

                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
                writer.write(message_.toString());
                writer.flush();

                MyConsole.log("Send message to " + nextHop.toString());

                InputStream is = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                if (!socket.isClosed())
                    socket.close();

                MyConsole.log(nextHop.toString() + " response: " + builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MyConsole.log("Cannot reach the destination:"+message_.getDestination());
        }
    }
}
