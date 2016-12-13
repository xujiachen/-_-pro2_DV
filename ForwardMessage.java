import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ForwardMessage extends Thread {
    private Message message_;

    public ForwardMessage(Message message) {
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
                socket.bind(new InetSocketAddress(Router.getLocalIP().toString(), Router.MESSAGEport));
                socket.connect(new InetSocketAddress(nextHop.toString(), Router.MESSAGEport));

                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os);
                writer.write(message_.toString());
                writer.flush();
                writer.close();
                socket.shutdownOutput();

                MyConsole.log("Send message to " + nextHop.toString());

                InputStream is = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                MyConsole.log(nextHop.toString() + " response: " + builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
