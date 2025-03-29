/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador.DAO;

import com.mycompany.sistemabiblioteca.cliente.Controlador.Conexion;
import com.mycompany.sistemabiblioteca.cliente.Modelo.UsuarioMOD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class UsuarioDAO {
    public boolean insertar(UsuarioMOD usuario) {
        String query = "INSERT INTO Usuario (nombre,primerApellido,segundoApellido,rol,correo,contrasena) VALUES (?,?,?,?,?,?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuario.getNombre());
             pstmt.setString(2, usuario.getPrimerApellido());
              pstmt.setString(3, usuario.getSegundoApellido());
               pstmt.setString(4, usuario.getRol());
                pstmt.setString(5, usuario.getCorreo());
                 pstmt.setString(6, usuario.getContrasena());
            
            
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<UsuarioMOD> obtener() {
        ArrayList<UsuarioMOD> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        try (Connection conn = Conexion.getConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                UsuarioMOD usuario = new UsuarioMOD(
                        rs.getInt("usuarioID"),
                        rs.getString("nombre"),
                        rs.getString("primerApellido"),
                        rs.getString("segundoApellido"),
                        rs.getString("rol"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                        
                );

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    public boolean actualizar(UsuarioMOD usuario) {
     
        String query = "UPDATE Usuario SET nombre = ?,primerApellido=?,segundoApellido=?,rol=?,correo=?,contrasena=? WHERE usuarioID = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2,usuario.getPrimerApellido() );
            pstmt.setString(3,usuario.getSegundoApellido() );
            pstmt.setString(4,usuario.getRol() );
            pstmt.setString(5,usuario.getCorreo() );
            pstmt.setString(6,usuario.getContrasena());
            pstmt.setInt(7,usuario.getUsuarioID());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminar(int id) {
        String query = "DELETE FROM Usuario WHERE usuarioID = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            System.out.println(id  +  "EL ID QUE RECIBE EL DAO");
            
            pstmt.setInt(1, id);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
