import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Ordinazioni implements Runnable {
    private Socket clientSocket;
    private int clientNumber;
    private BufferedReader input;
    private PrintWriter output;
    private static String[] order;

    private static Protocol p;

    private Calendar date=new GregorianCalendar();

    public Ordinazioni(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }

    public static double totale() {
        double total = 0;
        double j;
        for (int i = 0; i < order.length; i++) {
            j = p.getPrezzi()[i];
            total += Integer.parseInt(order[i]) * j;

        }
        return total;
    }


    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            p = new Protocol(input, output);
            System.out.println("Processing request: " + clientNumber+" ["+date.getTime().toString()+"]");
            int response=p.welcome();
            if (response == 0) {
                p.sendTastes();
                System.out.println("MENU INVIATO");
            } else {
                if (response == 1) {
                    int quantity = p.howMany();
                    if (quantity > 0) {
                        order = p.retrieveNames();
                        for (int i = 0; i < order.length; i++) {
                            System.out.println(order[i]);
                        }
                        //output.println("Totale Scontrino: "+totale());
                        p.end();
                    } else {
                        System.out.println("Errore nell'inserire la quantità!!!");
                    }
                } else{
                    System.out.println("Errore di connessione!!!");
                }
            }
            output.close();
            input.close();
            System.out.println("Connection "+clientNumber+" closed"+" ["+date.getTime().toString()+"]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}