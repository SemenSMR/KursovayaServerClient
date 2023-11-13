package org.example;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ClientHandler implements Runnable {

    private BufferedReader in;

    public ClientHandler(Socket clientSocket) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    }

    @Override
    public void run() {
        try {
            String ClientName = in.readLine();
            System.out.println("Приветствуем " + ClientName);
            try (PrintWriter log = new PrintWriter(new FileWriter("file.log", true))) {
                log.println(getCurrentDataTime() + " " + ClientName + ": " + "Присоединился к чату");
            }
            String message;
            while ((message = in.readLine()) != null) {

                System.out.println("Сообщение от пользователя " + ClientName + ": " + message);
                Server.broadcast(message);

                try (PrintWriter log = new PrintWriter(new FileWriter("file.log", true))) {
                    log.println(getCurrentDataTime() + " " + ClientName + ": "  + message);


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDataTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}

