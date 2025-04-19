/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import shared.Autor;


/**
 *
 * @author Usuario
 */
public class AutorController {
     private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public List<Autor> obtener() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("GET_AUTOR:");
            String response = in.readLine();
            return parseAutores(response);
        }
    }

    public boolean agregar(Autor autor) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("AGREGAR_AUTOR:" + autorToString(autor));
            String response = in.readLine();
            return response.equals("Autor agregado correctamente");
        }
    }

    public boolean actualizar(Autor autor) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ACTUALIZAR_AUTOR:" + autorToString(autor));
            String response = in.readLine();
            return response.equals("Autor actualizado correctamente");
        }
    }

    public boolean eliminar(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ELIMINAR_AUTOR:" + id);
            String response = in.readLine();
            return response.equals("Autor eliminado correctamente");
        }

    }
    public String buscarPorID(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("AUTOR_BUSCAR_POR_ID:" + id);
            String response = in.readLine();
            System.out.println(response);
            return response;
        }

    }
    private List<Autor> parseAutores(String data) {
    List<Autor> autores = new ArrayList<>();
   
    String[] autorStrings = data.split(";");
    for (String autorString : autorStrings) {
        String[] parts = autorString.split(",");
        if (parts.length == 5) {
            try {
                int id = Integer.parseInt(parts[0]);
                String nombre = parts[1];
                String primerApellido = parts[2];
                Date fechaNacimiento = Date.valueOf(parts[3]);
                Date fechaFallecimiento = Date.valueOf(parts[4]);
                
                autores.add(new Autor(id, nombre, primerApellido, fechaNacimiento, fechaFallecimiento));
            } catch (Exception e) {
                System.out.println("Error al parsear autor: " + e.getMessage());
            }
        }
    }
    return autores;
}

private String autorToString(Autor autor) {
    return autor.getAutorID() + "," +
           autor.getNombre() + "," +
           autor.getPrimerApellido() + "," +
           autor.getFechaNacimiento() + "," +
           autor.getFechaFallecimiento();
}

    
    
}
