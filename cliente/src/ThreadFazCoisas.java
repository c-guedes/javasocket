/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Madianita
 */
public class ThreadFazCoisas extends Thread {
    Socket cSocket;  //socket do cliente
    ObjectOutputStream sendObject;
    ObjectInputStream serverResponse;
    String envServ = "";
    Scanner auxEnt = new Scanner(System.in);

    ThreadFazCoisas(Socket cSocket) {
        this.cSocket = cSocket;
    }

    public void run() {
        System.out.println("Thread de inserção");
        try {
            sendObject = new ObjectOutputStream(cSocket.getOutputStream());
            serverResponse = new ObjectInputStream(cSocket.getInputStream());
        } catch (IOException e) {
            System.exit(0);
        }

        ServerResponse response = null;

        while (true) {
            Contato contatin = new Contato("caiquezin");
            String login = auxEnt.nextLine();

            try {
                sendObject.writeObject(contatin);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                response = (ServerResponse) serverResponse.readObject();
                System.out.println(response.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                serverResponse.reset();
                sendObject.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
