//package org.example;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
//public class ReceiveHandler implements Runnable {
//    private BufferedReader in;
//
//
//    public ReceiveHandler(Socket clientSocket) throws IOException {
//
//    }
//
//    @Override
//    public void run() {
//        String message;
//        while (true){
//            try {
//                if (((message = in.readLine()) != null)){
//                    System.out.println("Получение " + message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//}
