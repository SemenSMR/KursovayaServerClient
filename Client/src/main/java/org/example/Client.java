package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";


    public static void main(String[] args) throws IOException {
        int port = loadPortFromConfig();
        if (port == -1) {
            System.out.println("Port not found in config file.");
            return;
        }
        try (Socket clientSocket = new Socket(SERVER_ADDRESS, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            String serverMessage = in.readLine();
            System.out.println(serverMessage);
//            Thread serverThread = new Thread(new ReceiveHandler(in));
//            serverThread.start();

            String message;
            while (true) {
                message = scanner.nextLine();
                out.println(message);

                if (message.equals("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int loadPortFromConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader("settings.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}