import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        if (Router.init()) {
            // Listening DV is the first.
            Router.BeginListenDV();

            // Add the Neighbors. Need input from console
            MyConsole.RunForAddNeighbor();
            Router.getTable().show();

            // Send DV route table to neighbors
            Router.BeginSendDV();

            // run as cmd, accept input and do something
            MyConsole.RunAsCMD();

        } else {
            System.out.println("Initialization Failed. Router shutdown.");
        }
    }
}
