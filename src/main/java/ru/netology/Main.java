package ru.netology;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    final static int PORT = 23444;

    public static void main(String[] args) {

        System.out.println("Запускаем сервер...");
        Thread server = new Server();
        server.start();

        System.out.println("Запускаем клиент...");
        startClient();
    }

    private static void startClient() {

        // Определяем сокет сервера и получаем входящий и исходящий потоки информации
        try (Socket socket = new Socket("127.0.0.1", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Клиент запущен");

            String msg;
            while (true) {
                System.out.println("Введите целое число");
                msg = scanner.nextLine();
                out.println(msg);
                out.flush();
                if ("end".equals(msg)) break;

                System.out.println("SERVER: " + in.readLine());
            }
        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}
