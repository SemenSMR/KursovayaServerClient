package org.example;

import javax.xml.stream.util.EventReaderDelegate;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client3 {
    private static final String SERVER_ADDRESS = "localhost";
    private static BufferedReader inMess;
    private static PrintWriter outMess;
    private static Scanner scannerConsole;
    private static Socket clientSocket = null;

    public static void main(String[] args) throws IOException {
        int port = loadPortFromConfig();
        if (port == -1) {
            System.out.println("Порт не найден");
            return;
        }
        clientSocket = new Socket(SERVER_ADDRESS, port);
        outMess = new PrintWriter(clientSocket.getOutputStream(), true);
        inMess = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        scannerConsole = new Scanner(System.in);

        AtomicBoolean flag = new AtomicBoolean(true);


        new Thread(() -> {
            try {
                while (true) {
                    if (!flag.get()) {
                        inMess.close();
                        clientSocket.close();
                        break;
                    }

                    if (inMess.ready()) {
                        String messFormServer = inMess.readLine();
                        System.out.println(messFormServer);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


        new Thread(() -> {
            while (true) {
                if (scannerConsole.hasNext()) {
                    String mess = scannerConsole.nextLine();
                    if (mess.equalsIgnoreCase("exit")) {
                        outMess.println(mess);
                        scannerConsole.close();
                        outMess.close();
                        flag.set(false);
                        break;
                    }
                    outMess.println(mess);
                }
            }
        }).start();

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