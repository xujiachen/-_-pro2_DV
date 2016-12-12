import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyConsole {
    static private MyConsole myConsole;

    private MyConsole() {
    }

    static void RunForAddNeighbor(IP LocalIP) throws IOException {
        if (myConsole == null) {
            myConsole = new MyConsole();
        }

        System.out.println("Please input the IP of this host's Neighbors. The IP is requested as \"A.B.C.D\". Finish your input with a \"End\".");
        System.out.print(LocalIP.toString() + "> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        int count = 0;
        while (!(input = reader.readLine()).equals("End")) {
            // TODO 处理输入
            Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
            Matcher matcher = pattern.matcher(input);
            if (matcher.find() && input.equals(matcher.group(0))) {
                IP ip = new IP(matcher.group(0));
                LocalRouteTable.getTable().AddRoute(new RouteEntry(ip, ip, 1));
                count++;
            } else {
                System.out.println("\"" + input + "\" is not a IP, please input as \"A.B.C.D\".");
            }
            System.out.print(LocalIP.toString() + "> ");
        }
        System.out.println("Add " + count + " Neighbors!");
    }
}
