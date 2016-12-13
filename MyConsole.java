import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyConsole {
    static private MyConsole myConsole;

    private MyConsole() {
    }

    static void RunForAddNeighbor() throws IOException {
        if (myConsole == null) {
            myConsole = new MyConsole();
        }

        System.out.println("Please input the IP of this host's Neighbors. The IP is requested as \"A.B.C.D\". Finish your input with a \"end\".");
        System.out.print(Router.getLocalIP().toString() + "> ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        while (!(input = reader.readLine()).equals("end")) {
            Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find() && input.equals(matcher.group(0)))
                Router.addNeighbor(new IP(matcher.group(0)));
            else
                System.out.println("\"" + input + "\" is not a IP, please input as \"A.B.C.D\".");

            System.out.print(Router.getLocalIP().toString() + "> ");
        }

        System.out.println("Add " + Router.getNeighbors().size() + " Neighbors!");
    }

    static void RunAfterNeighbor() {
        if (myConsole == null) {
            myConsole = new MyConsole();
        }
        String input;
        System.out.print(Router.getLocalIP().toString() + "> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!(input = reader.readLine()).equals("exit")) {
                if (Pattern.matches("send to [0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3} *$", input)) {
                    Pattern IPPattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
                    Matcher IPMatcher = IPPattern.matcher(input);
                    if (IPMatcher.find()) {
                        IP destinationIP = new IP(IPMatcher.group(0));

                        Message message = new Message();
                        message.setSource(Router.getLocalIP());
                        message.setDestination(destinationIP);
                        message.addPoint(Router.getLocalIP());
                        new ForwardMessage(message).start();
                    }
                } else if (Pattern.matches("show route table *$", input)) {
                    Router.getTable().show();
                } else if (Pattern.matches("help *$", input)) {
                    System.out.println("send to A.B.C.D   ---- Send a message to the IP A.B.C.D");
                    System.out.println("show route table  ---- Show the route table now");
                    System.out.println("help              ---- show the order can be used");
                    System.out.println("exit              ---- exit the program");
                } else if (!Pattern.matches(" *$", input)) {
                    System.out.println("Your input is wrong, please check!");
                }

                System.out.print(Router.getLocalIP().toString() + "> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void log(String str) {
        System.out.println(Router.getLocalIP().toString() + "> " + str);
    }
}
