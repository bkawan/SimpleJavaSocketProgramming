/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bk.echoserver.clienthandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.interrupted;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author bkawan
 * @date Mar 14, 2016
 * @time 6:38:49 PM
 */// need client for clientlistner
public class ClientListner extends Thread {

    // 1 - need client which is socket 
    // current client
    private Socket client;

    // list of all other clients to communicate with eachother
    private List<Socket> clients;

    /**
     *
     * @param client
     */
    public ClientListner(Socket client, List<Socket> clients) {

        this.client = client;
        this.clients = clients;

    }

    // to send client message to other clients
    private void broadcastMessage(String msg) throws IOException {
        //loop through all client

        for (Socket s : clients) {
            PrintWriter out = new PrintWriter(s.getOutputStream());

            out.print(msg);

            out.print(">>");

            out.flush();

        }
    }

    @Override
    public void run() {

        while (!interrupted()) {
            try {
                PrintWriter out = new PrintWriter(client.getOutputStream());

                //to read msg from client
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("welcome to our server");
                out.print(">>");
                out.flush();

                String line = "";
                while (!(line = reader.readLine()).toLowerCase().equals("exit")) {

                    String sender = client.getInetAddress().getHostName();
                    InetAddress inetAddr = InetAddress.getLocalHost();
                    //InetAddress clientip = client.getInetAddress().getLocalHost();
                    //System.out.println(line);
                    //  broadcastMessage(inetAddr.getHostAddress()+"[" + inetAddr.getHostName()+"] " + ": " + line + "\n");
                    broadcastMessage(sender + ": " + line + "\n");

                    out.flush();
                }
                if ((line = reader.readLine()).toLowerCase().equals("exit")) {
                    out.println("You are logout");
                    out.flush();
                }

            } catch (IOException ex) {
                System.out.println("Connection problem");
            }
        }
    }

}
