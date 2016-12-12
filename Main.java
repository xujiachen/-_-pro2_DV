import java.io.IOException;
import java.net.Inet4Address;

class Main {
    public static void main(String[] args) throws IOException {
        Router.init();

        MyConsole.RunForAddNeighbor();
        Router.getTable().show();
        MyConsole.RunAfterNeighbor();
        //new SendDVToNeighbor().start();
        //new ListenDVFromNeighbor(myIP).start();
    }
}
