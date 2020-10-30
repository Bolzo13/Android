import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Protocol {

    private  final String WELCOME = "Benvenuto alla pizzeria da Gennarino";
    private  final String HOWMANY = "Quante pizze vuoi?";
    private  final String ASK_WHAT = "Che pizza vuoi?";
    private  final String RISPOSTA = "OK";
    private  final String[] PIZZE = {"Margherita", "Viennese", "Patatosa", "Fantasia"};
    public  final double[] PREZZI = {5.5, 6, 6, 8.5};
    private BufferedReader input;
    private PrintWriter output;

    Protocol(BufferedReader input, PrintWriter output) {
        this.input=input;
        this.output=output;
    }

    public int welcome() {
        output.println(WELCOME);
        return send();
    }
    public int send() {
        try {
            String risposta=input.readLine();
            if (risposta.equalsIgnoreCase(RISPOSTA)){
                return 0;
            }
            else{
                if(risposta.equalsIgnoreCase("ORDINE")) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }
    public int howMany() {
        output.println(HOWMANY);
        try {
            String inputK=input.readLine();
            System.out.println(inputK);
            int quantity = Integer.parseInt(inputK);
            if (quantity > 0) return quantity;
            else return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int sendTastes() {
        output.println(Arrays.toString(PIZZE)+"\n"+Arrays.toString(PREZZI));
        return send();
    }
    public String[] retrieveNames() {
        String[] out = new String[PIZZE.length];
        try {
            for(int i=0;i<PIZZE.length;i++) {
                output.println("Quante "+PIZZE[i]+" ?");
                out[i] = input.readLine();
            }
            System.out.println(out);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
    public void end(){
        output.println(Ordinazioni.totale());
    }

    public double[] getPrezzi(){
        return PREZZI;
    }
}
