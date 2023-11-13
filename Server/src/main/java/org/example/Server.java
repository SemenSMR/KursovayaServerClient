package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int DEFAULT_PORT = 12345;
    private static final String PORT_FILE = "settings.txt";
    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int port = loadPortFromConfig();
        if (port == -1) {
            port = DEFAULT_PORT;
            savePortToConfig(port);
        }


        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Start server..... ");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connect client");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            clients.add(out);
            out.println("Hello, welcome to our chat, Whats your name? ");
            Thread clientThread = new Thread(new ClientHandler(clientSocket));
            clientThread.start();
        }
    }

    private static void savePortToConfig(int port) {
        try (PrintWriter writer = new PrintWriter(PORT_FILE)) {
            writer.println(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int loadPortFromConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PORT_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void broadcast(String mesage) {
        for (PrintWriter client : clients) {
            client.println(mesage);
        }
    }
}
