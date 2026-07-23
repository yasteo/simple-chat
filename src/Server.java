import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try (var server = new ServerSocket(8080)) {
            List<PrintWriter> list = new ArrayList<>();
            var IP = InetAddress.getLocalHost().getHostAddress();
            var port = server.getLocalPort();
            System.out.println("Сервер запустился");
            System.out.println("IP сервера: " + IP);
            System.out.println("Порт сервера: " + port);
            while (true) {
                try (var client = server.accept()) {
                    var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    var out = new PrintWriter(client.getOutputStream());
                    list.add(out);
                    var thread = new Thread(new ClientHandler(in, out, list));
                    thread.start();
                }
            }
        } catch (IOException e) {
            System.err.println("[ОШИБКА] " + e.getMessage());
        } finally {
            System.out.println("Сервер выключен");
        }
    }
}
