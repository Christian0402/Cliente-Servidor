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

import shared.Libro;

/**
 *
 * @author Usuario
 */
public class LibroController {
     private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public List<Libro> obtener() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("GET_LIBRO:");
            String response = in.readLine();
            return parseLibros(response);
        }
    }

    public boolean agregar(Libro libro) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("AGREGAR_LIBRO:" + libroToString(libro));
            String response = in.readLine();
            return response.equals("Libro agregado correctamente");
        }
    }

    public boolean actualizar(Libro libro) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ACTUALIZAR_LIBRO:" + libroToString(libro));
            String response = in.readLine();
            return response.equals("Libro actualizado correctamente");
        }
    }

    public boolean eliminar(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ELIMINAR_LIBRO:" + id);
            String response = in.readLine();
            return response.equals("Libro eliminado correctamente");
        }

    }
    public String obtenerNombrePorID(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("LIBRO_BUSCAR_POR_ID:" + id);
            String response = in.readLine();
            return response;
        }

    }
    
    public boolean setDisponible(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("LIBRO_SET_DISPONIBLE:" + id);
            String response = in.readLine();
            return response.equals("Libro disponible!");
        }
    }
    public boolean cambiarDisponibilidad(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("LIBRO_CAMBIAR_DISPONIBILIDAD:" + id);
            String response = in.readLine();
            return response.equals("Libro cambia disponibilidad!");
        }
    }
    
    public List<Libro> buscarPorFiltroGeneral(String filtro)throws IOException{
         try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("FILTRO_LIBRO:" + filtro);
            String response = in.readLine();
            return parseLibros(response);
        }
    }
    
    
    public Date obtenerFechaFinalizacionPorLibro (int id)throws IOException{
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("OBTENER_FECHA_FINALIZACION:" + id);
            
            String response = in.readLine();
            Date fecha = Date.valueOf(response);

            return fecha;
        }
    
    }
    
    
    
    private List<Libro> parseLibros(String data) {
    List<Libro> libros = new ArrayList<>();

    if (data == null || data.isEmpty()) {
        return libros;
    }

    String[] libroStrings = data.split(";");
    for (String libroString : libroStrings) {
        String[] parts = libroString.split(",");
        if (parts.length == 6) {
            try {
                int id = Integer.parseInt(parts[0]);
                String titulo = parts[1];
                int autorID = Integer.parseInt(parts[2]);
                int categoriaID = Integer.parseInt(parts[3]);
                boolean disponibilidad = Boolean.parseBoolean(parts[4]);
                Date anoPublicacion = Date.valueOf(parts[5]);

                libros.add(new Libro(id, titulo, autorID, categoriaID, disponibilidad, anoPublicacion));
            } catch (Exception e) {
                System.out.println("Error al parsear libro: " + e.getMessage());
            }
        }
    }
    return libros;
}

private String libroToString(Libro libro) {
    return libro.getLibroID() + "," +
           libro.getTitulo() + "," +
           libro.getAutorID() + "," +
           libro.getCategoriaID() + "," +
           libro.isDisponibilidad() + "," +
           libro.getAnoPublicacion();
}

    
    
    
}
