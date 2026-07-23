import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String IP = null;
        Integer port = null;
        try {
            IP = IO.readln("Введите IP-адрес сервера: ");
            port = Integer.parseInt(IO.readln("Укажите порт сервера: "));
            try (var socket = new Socket(IP, port)) {
                var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("Успешное подключение");
                var login = IO.readln("Введите ваш логин: ");
                out.println(login);
                var runnable = new Runnable() {
                    @Override
                    public void run() {
                        String message = null;
                        try {
                            while ((message = in.readLine()) != null) {
                                System.out.println(message);
                            }
                        } catch (IOException e) {
                            System.err.println("[ОШИБКА] " + e.getMessage());
                        }
                    }
                };
                var thread = new Thread(runnable);
                thread.start();
                while (true) {
                    String message = IO.readln();
                    out.println(message);
                }
            }
        } catch (Exception e) {
            System.err.println("[ОШИБКА]" + e.getMessage());
        }
    }
}
