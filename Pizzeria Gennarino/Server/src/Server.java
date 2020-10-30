import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private int serverPort;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private int clientNumber = 0;

    public Server(int port){
        this.serverPort = port;
    }

    public void run(){

        openServerSocket();
        while(!isStopped()){
            synchronized(this){
                clientNumber++;
            }
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    break;
                }
                throw new RuntimeException("Errore di connessione!!!", e);
            }

            threadPool.execute(new Ordinazioni(clientSocket, clientNumber));
        }
        threadPool.shutdown();
        System.out.println("Stopping server (after finishing pending orders)...") ;
    }


    private synchronized boolean isStopped() {
        return isStopped;
    }

    synchronized void stop(){
        isStopped = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }
}