package ru.netology;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.netology.Main.PORT;

public class Server extends Thread{

    @Override
    public void run() {
        try (ServerSocket servSocket = new ServerSocket(PORT)){
            System.out.println("Сервер запущен. Порт " + servSocket.getLocalPort());
            //  Ждем подключения клиента и получаем потоки для дальнейшей работы
            while (true){
                try(Socket socket = servSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ){
                    String line;
                    while ((line = in.readLine()) != null) {
                        String outLine = facadeFibonacciNumbers(line);
                        out.println("N-ное число Фибоначчи: " + outLine);
                        out.flush();

                        // Выход если от клиента получили end
                        if (line.equals("end")) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Server error while connection: " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }

    private String facadeFibonacciNumbers(String s) {
        try {
            int n = Integer.parseInt(s);
            long result = fibonacciNumbers(n);
            return String.valueOf(result);
        } catch (Exception e) {
            return "Ошибка: " + e;
        }
    }

    private long fibonacciNumbers(int n){
        long[] mas = new long[n];
        for(int i=0; i<n; i++){
            if (i==0) mas[i] = 0;
            else if (i==1) mas[i] = 1;
            else mas[i] = mas[i-2] + mas[i-1];
        }
        /*for(int i=0; i<n; i++){
            System.out.print(mas[i] + "-");
        }
        System.out.println();*/

        return mas[n-1];
    }
}
