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
import shared.Categoria;

/**
 *
 * @author Usuario
 */
public class CategoriaController {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public List<Categoria> obtener() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("GET_CATEGORIAS:");
            String response = in.readLine();
            return parseCategorias(response);
        }
    }

    public boolean agregar(Categoria categoria) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("AGREGAR_CATEGORIA:" + categoriaToString(categoria));
            String response = in.readLine();
            return response.equals("Categoria agregada correctamente");
        }
    }

    public boolean actualizar(Categoria categoria) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ACTUALIZAR_CATEGORIA:" + categoriaToString(categoria));
            String response = in.readLine();
            return response.equals("Categoria actualizada correctamente");
        }
    }

    public boolean eliminar(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("ELIMINAR_CATEGORIA:" + id);
            String response = in.readLine();
            return response.equals("Categoria eliminada correctamente");
        }

    }

    public String buscarPorID(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("CATEGORIA_BUSCAR_POR_ID:" + id);
            String response = in.readLine();
            return response;
        }

    }

    private List<Categoria> parseCategorias(String data) {
        List<Categoria> categorias = new ArrayList<>();
        if (data == null || data.isEmpty()) {
            return categorias;
        }

        String[] categoriaStrings = data.split(";");
        for (String categoriaString : categoriaStrings) {
            String[] parts = categoriaString.split(",");
            if (parts.length == 2) {
                categorias.add(new Categoria(Integer.parseInt(parts[0]), parts[1]));
            }
        }
        return categorias;
    }

    private String categoriaToString(Categoria categoria) {
        return categoria.getCategoriaID() + "," + categoria.getNombre();
    }

    private Categoria parseCategoria(String data) {
        String[] parts = data.split(",");
        try {
            return new Categoria(Integer.parseInt(parts[0]), parts[1]);
        } catch (Exception e) {
            System.out.println("Error al parsear categoría: " + e.getMessage());
            return null;
        }

    }

}
