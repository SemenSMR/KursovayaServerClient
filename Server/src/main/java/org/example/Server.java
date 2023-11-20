package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final String PORT_FILE = "settings.txt";
    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PORT_FILE));
        String line = reader.readLine();
        int port = Integer.parseInt(line);


        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Start server..... ");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            clients.add(out);
            Thread clientThread = new Thread(new ClientHandler(clientSocket, out, clients));
            clientThread.start();
        }
    }


    public static void broadcast(String message, PrintWriter sender,List<PrintWriter> clients) {
        for (PrintWriter client : clients) {
            if (client != sender) {
                client.println(message);
            }
        }
    }
}
