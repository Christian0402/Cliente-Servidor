package servidor;

import java.io.*;
import java.net.*;

public class ManejadorCliente implements Runnable {

    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        ) {
            
            String ipCliente = obtenerIpCliente();
            System.out.println("Cliente conectado desde la IP: " + ipCliente);

         
            String mensaje = entrada.readLine();
            if (mensaje != null) {
                System.out.println("Cliente dice: " + mensaje);
                
                salida.println("Mensaje recibido: " + mensaje);
            } else {
                System.out.println("El cliente cerró la conexión sin enviar un mensaje.");
            }

        } catch (IOException e) {
           
            System.out.println("Error en la conexión con el cliente: " + e.getMessage());
        } finally {
            try {
               
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    System.out.println("Cliente desconectado.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String obtenerIpCliente() {
        try {
           
            return socket.getInetAddress().getHostAddress();
        } catch (Exception e) {
           
            System.out.println("No se pudo obtener la IP del cliente, usando dirección desconocida.");
            return "Desconocida";  // IP predeterminada si no se puede obtener la IP
        }
    }
}
