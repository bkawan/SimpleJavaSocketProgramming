/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.echoserver;

import com.bk.echoserver.clienthandler.ClientListner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author bikeshkawan
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int port = 1500;
        try {
            //instantiate first  server
            ServerSocket server = new ServerSocket(port);
            //to connect server
            //client connect to server
            System.out.println("Server is Running at Port number:  " + port);
            //generic array list of clients
            List<Socket> clients = new ArrayList<Socket>();
            while (true) {
                // to read connection socket is where client connect to server

                Socket client = server.accept();
                clients.add(client);
                System.out.println("Connection Resuest from " + client.getInetAddress().getHostAddress());
                
                //instantiate clientListner class to make multithread 
                // pass current client aand list of all other clients
                ClientListner clientListner = new ClientListner(client , clients);
                clientListner.start();
                
//                PrintWriter out = new PrintWriter(client.getOutputStream());
//                //to read msg from client
//                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                out.println("welcome to our server");
//                out.flush();
//                String line = "";
//                while (!(line = reader.readLine()).toLowerCase().equals("exit")) {
//                    out.println(">");
//                    out.flush();
//                    System.out.println(line);
//                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
