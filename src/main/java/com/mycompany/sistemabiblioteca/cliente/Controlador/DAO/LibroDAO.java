/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador.DAO;

import com.mycompany.sistemabiblioteca.cliente.Controlador.Conexion;
import com.mycompany.sistemabiblioteca.cliente.Modelo.LibroMOD;
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
public class LibroDAO {

    public boolean insertar(LibroMOD libro) {
        String query = "INSERT INTO Libro (titulo,autorID,categoriaID,disponibilidad,anoPublicacion) VALUES (?,?,?,?,?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setInt(2, libro.getAutorID());
            pstmt.setInt(3, libro.getCategoriaID());
            pstmt.setBoolean(4, libro.isDisponibilidad());
            pstmt.setDate(5, libro.getAnoPublicacion());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<LibroMOD> obtener() {
        ArrayList<LibroMOD> libros = new ArrayList<>();
        String query = "SELECT * FROM Libro";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                LibroMOD libro = new LibroMOD(
                        rs.getInt("libroID"),
                        rs.getString("titulo"),
                        rs.getInt("autorID"),
                        rs.getInt("categoriaID"),
                        rs.getBoolean("disponibilidad"),
                        rs.getDate("anoPublicacion")
                );

                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public boolean actualizar(LibroMOD libro) {
        System.out.println("El nuevo id de categoria es " + libro.getCategoriaID());
        String query = "UPDATE Libro SET titulo = ?,autorID=?,categoriaID=?,disponibilidad=?,anoPublicacion=? WHERE libroID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setInt(2, libro.getAutorID());
            pstmt.setInt(3, libro.getCategoriaID());
            pstmt.setBoolean(4, libro.isDisponibilidad());
            pstmt.setDate(5, libro.getAnoPublicacion());
            pstmt.setInt(6, libro.getLibroID());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM Libro WHERE libroID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            System.out.println(id + "EL ID QUE RECIBE EL DAO");

            pstmt.setInt(1, id);
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarDisponibilidad(int id) {
        String query = "UPDATE Libro SET disponibilidad=false  WHERE libroID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerNombrePorID(int id) {
        String query = "Select titulo from  Libro  WHERE libroID = ?";
        String nombreLibro = "";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nombreLibro = rs.getString("titulo");

                }
            }

            
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return nombreLibro;
    }
    public boolean setDisponible(int id){
         String query = "UPDATE Libro SET disponibilidad=true  WHERE libroID = ?";
          try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);

            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
