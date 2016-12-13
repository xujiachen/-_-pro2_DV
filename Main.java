import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        Router.init();

        MyConsole.RunForAddNeighbor();
        Router.getTable().show();
        new ListenDVFromNeighbor().start();
        new ListenForMessage().start();
        MyConsole.RunAfterNeighbor();
    }
}
