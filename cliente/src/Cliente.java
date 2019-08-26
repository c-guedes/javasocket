import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.concurrent.Future;


public class Cliente {
    public static void main(String args[]) throws IOException {
        Socket cSocket = null;  //socket do cliente
        ObjectOutputStream sendObject = null;
        ObjectInputStream serverResponse = null;
        String envServ = "";
        Scanner auxEnt = new Scanner(System.in);

        try {
            cSocket = new Socket("127.0.0.1", 7000);
            sendObject = new ObjectOutputStream(cSocket.getOutputStream());
            serverResponse = new ObjectInputStream(cSocket.getInputStream());
        } catch (IOException e) {//se maq desabilitada ou erro de leitura/escrita
            System.err.println("Erro = " + e);
            System.exit(0);
        }

        ServerResponse response = null;
        ThreadFazCoisas faz = new ThreadFazCoisas(cSocket);

        while (response == null || response.getResponseCode() != 0) {
            System.out.print("Digite seu login: ");
            String login = auxEnt.nextLine();
            System.out.print("Digite seu pass: ");
            String pass = auxEnt.nextLine();
            Pessoa teste = new Pessoa(login, pass);
            sendObject.writeObject(teste);

            try {
                response = (ServerResponse) serverResponse.readObject();

                if (response.getResponseCode() == 0) {
                    System.out.println(response.getMessage());
                    faz.start();
                } else if (response.getResponseCode() == 3) {
                    System.out.println(response.getMessage());
                } else {
                    System.out.println(response.getMessage());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!faz.isAlive()) {
            serverResponse.reset();
            sendObject.reset();
            cSocket.close();
            System.exit(0);
        }
    }
}
