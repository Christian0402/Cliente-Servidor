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
import java.util.ArrayList;
import java.util.List;
import shared.Usuario;

/**
 *
 * @author Usuario
 */
public class UsuarioController {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public List<Usuario> obtener() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("GET_USUARIOS:");
            String response = in.readLine();
            return parseUsuarios(response);
        }
    }

    public List<Usuario> iniciarSesion(Usuario usuario) throws IOException {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println("INICIAR_SESION:" + usuario.getCorreo() + "," + usuario.getContrasena());

            
            String response = in.readLine();
            if (!response.equals("ERROR")) {
                List<Usuario> usuarios = parseUsuarios(response);
                if (usuarios.isEmpty()) {
                    return null;
                } else {
                    return usuarios; 
                }
            } else {
                return null; 
            }
        }
    }

    public boolean agregar(Usuario usuario) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
          
            
          
            out.println("AGREGAR_USUARIO:" + usuarioToString(usuario));
            String response = in.readLine();
            return response.equals("Usuario agregado correctamente");
        }
    }
    public boolean actualizar(Usuario usuario) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
          
            
           
            out.println("ACTUALIZAR_USUARIO:" + usuarioToString(usuario));
            String response = in.readLine();
            return response.equals("Usuario actualizado correctamente");
        }
    }
    
    public boolean eliminar(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
          
            
           
            out.println("ELIMINAR_USUARIO:" + id);
            String response = in.readLine();
            return response.equals("Usuario eliminado correctamente");
        }
    
    }
    
    public String obtenerNombrePorID(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
          
            
           
            out.println("GET_NOMBRE_USUARIOS_BY_ID:" + id);
            String response = in.readLine();
            return response;
        }
    
    }

    
    
    
    
   
    private List<Usuario> parseUsuarios(String data) {
        List<Usuario> usuarios = new ArrayList<>();
        if (data == null || data.isEmpty()) {
            return usuarios;
        }

        String[] usuariosArray = data.split(";");
        for (String usuarioString : usuariosArray) {
            String[] parts = usuarioString.split(",");
            if (parts.length == 7) {
                try {
                    usuarios.add(new Usuario(
                            Integer.parseInt(parts[0]),
                            parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6]
                    ));
                } catch (Exception e) {
                    System.out.println("Error al parsear usuario: " + e.getMessage());
                }
            }
        }
        return usuarios;
    }

    private String usuarioToString(Usuario usuario) {
        return usuario.getUsuarioID() + ","
                + usuario.getNombre() + ","
                + usuario.getPrimerApellido() + ","
                + usuario.getSegundoApellido() + ","
                + usuario.getRol() + ","
                + usuario.getCorreo() + ","
                + usuario.getContrasena();
    }
}
