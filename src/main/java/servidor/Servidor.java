package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    private static final int PUERTO = 5000;
    private static final int MAX_CLIENTES = 5;
    private ExecutorService executor;

   

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CLIENTES);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor escuchando en el puerto " + PUERTO);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado desde " + clienteSocket.getInetAddress());

               
                executor.execute(new ManejadorCliente(clienteSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
