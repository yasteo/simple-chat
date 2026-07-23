import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;
    private final List<PrintWriter> writers;

    public ClientHandler(Socket client, BufferedReader in, PrintWriter out, List<PrintWriter> writers) {
        this.client = client;
        this.in = in;
        this.out = out;
        this.writers = writers;
    }

    public void run() {
        var login = "гость";
        try {
            login = in.readLine().trim();
            System.out.println("Пользователь " + login + " подключился");
            String message = null;
            while ((message = in.readLine()) != null) {
                System.out.println(login + ": " + message);
                for (var writer : writers) {
                    if (writer != out) {
                        writer.println(login + ": " + message);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[ОШИБКА] " + e.getMessage());
        } finally {
            writers.remove(out);
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println("[ОШИБКА] " + e.getMessage());
            }
            System.out.println("Пользователь " + login + " отключился");
        }
    }
}
