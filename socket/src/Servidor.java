import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    private static ArrayList<Pessoa> users = new ArrayList<>();
    private static ArrayList<Contato> contats = new ArrayList<>();

    static synchronized Boolean existPerson(Pessoa fname) {
        for (int i = 0; i < users.size(); i++) {
            if (fname.getLogin().equals(users.get(i).getLogin())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Socket cSocket; //socket conectado com clientes
        ServerSocket sSocket = null;
        String strRec;
        BufferedReader RecebClient;
        ObjectInputStream ReceivedObj;
        ObjectOutputStream sendResponse;

        users.add(new Pessoa("caique", "123"));


        try {
            sSocket = new ServerSocket(7000);
        } catch (IOException e) {
            System.err.println("Servidor nao pode ouvir a porta: 7000 ");
            System.exit(0);
        }

        while (true) {
            try {
                System.out.println("\n\nEsperando cliente:" +
                        "Endereço IP do servidor: " + sSocket.getInetAddress());
                cSocket = sSocket.accept();

                sendResponse = new ObjectOutputStream(cSocket.getOutputStream());
                RecebClient = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                ReceivedObj = new ObjectInputStream(cSocket.getInputStream());

                strRec = "";
                ThreadAtendeCliente ser = new ThreadAtendeCliente(cSocket,contats);

                while (!strRec.equalsIgnoreCase("sair")) {
                    //strRec = RecebClient.readLine();
                    System.out.println();
                    Pessoa user = null;

                    try {
                        user = (Pessoa) ReceivedObj.readObject();
                    } catch (IOException e) {
                        System.err.println("Erro :" + e);
                        System.exit(0);
                    }

                    if (user != null) {
                        if (existPerson(user)) {
                            System.out.println("Recebeu login");
                            sendResponse.writeObject(new ServerResponse(0, "Sucesso no login"));
                            ser.start();
                        } else {
                            sendResponse.writeObject(new ServerResponse(1, "Usuário não encontrado"));
                        }
                    }

                }
                if (!ser.isAlive()) {
                    sendResponse.reset();
                    RecebClient.close();
                    cSocket.close();
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro :" + e);
                System.exit(0);
            }
        }


    }
}
