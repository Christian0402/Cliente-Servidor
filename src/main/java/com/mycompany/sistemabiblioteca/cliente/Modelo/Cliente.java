/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Modelo;

import java.io.*;
import java.net.Socket;


public class Cliente {
    //private static final String HOST = "localhost";
    private static final String HOST = "10.1.5.67";
    private static final int PUERTO = 5000;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    public Cliente() {
        try {
            this.socket = new Socket(HOST, PUERTO);
            this.salida = new PrintWriter(socket.getOutputStream(), true);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Conectado al servidor en " + HOST + ":" + PUERTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    public String recibirMensaje() {
        try {
            return entrada.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cerrarConexion() {
        try {
            socket.close();
            System.out.println("Conexión cerrada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
