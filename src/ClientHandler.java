import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private final BufferedReader in;
    private final PrintWriter out;
    private final List<PrintWriter> list;

    public ClientHandler(BufferedReader in, PrintWriter out, List<PrintWriter> list) {
        this.in = in;
        this.out = out;
        this.list = list;
    }

    public void run() {
        var login = "гость";
        try {
            login = in.readLine().trim();
            System.out.println("Пользователь " + login + " подключился");
            String message = null;
            while ((message = in.readLine()) != null) {
                System.out.println(login + ": " + message);
            }
        } catch (IOException e) {
            System.err.println("[ОШИБКА] " + e.getMessage());
        } finally {
            list.remove(out);
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println("[ОШИБКА] " + e.getMessage());
            }
            System.out.println("Пользователь " + login + " отключился");
        }
    }
}
