import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(56780);
        Thread threadServer = new Thread(server);
        threadServer.start();
        System.out.print(" Server running\n ");
        Scanner input = new Scanner(System.in);
        if(input.nextLine().equalsIgnoreCase("s")) {
            System.out.println("stopping server");
            server.stop();
        }
    }
}
