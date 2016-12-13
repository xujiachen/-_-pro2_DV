import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        Router.init();

        MyConsole.RunForAddNeighbor();
        Router.getTable().show();
        new SendDVToNeighbor().start();
        new ListenForDV().start();
        new ListenForMessage().start();
        MyConsole.RunAfterNeighbor();
    }
}
