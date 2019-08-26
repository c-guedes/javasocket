/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Madianita
 */
public class ThreadAtendeCliente extends Thread{

    Socket cSocket; //socket conectado com clientes
    BufferedReader RecebClient;
    ObjectInputStream ReceivedObj;
    ObjectOutputStream sendResponse;
    Contato recebido;
    ArrayList<Contato> contats;
    
    ThreadAtendeCliente(Socket cSocket, ArrayList<Contato> contats){
        this.cSocket = cSocket;
    }
    
    public void run(){
        System.out.println("Serviço atende");
        try {
            sendResponse = new ObjectOutputStream(cSocket.getOutputStream());
            RecebClient = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            ReceivedObj = new ObjectInputStream(cSocket.getInputStream());

            recebido = null;

            while (true){
                System.out.print("UEUEUE");
                contats.add((Contato) ReceivedObj.readObject());
                sendResponse.writeObject(new ServerResponse(0, "Sucesso no atendimento"));
            }
//            sendResponse.reset();
//            RecebClient.close();
//            cSocket.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }
}
