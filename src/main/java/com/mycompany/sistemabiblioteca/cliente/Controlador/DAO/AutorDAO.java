/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador.DAO;

import com.mycompany.sistemabiblioteca.cliente.Controlador.Conexion;
import com.mycompany.sistemabiblioteca.cliente.Modelo.AutorMOD;
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
public class AutorDAO {
    public boolean insertar(AutorMOD autor) {
        String query = "INSERT INTO Autor (nombre,primerApellido,fechaNacimiento,fechaFallecimiento) VALUES (?,?,?,?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, autor.getNombre());
            pstmt.setString(2, autor.getPrimerApellido());
            pstmt.setDate(3, autor.getFechaNacimiento());
            pstmt.setDate(4, autor.getFechaFallecimiento());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<AutorMOD> obtener() {
        ArrayList<AutorMOD> autores = new ArrayList<>();
        String query = "SELECT * FROM Autor";
        try (Connection conn = Conexion.getConnection(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                AutorMOD autor = new AutorMOD(
                        rs.getInt("autorID"),
                        rs.getString("nombre"),
                        rs.getString("primerApellido"),
                        rs.getDate("fechaNacimiento"),
                        rs.getDate("fechaFallecimiento")
                );

                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }
    public boolean actualizar(AutorMOD autor) {
        //System.out.println(categoria.getCategoriaID() + categoria.getNombre()+ "en el DAO");
        String query = "UPDATE Autor SET nombre = ?,primerApellido = ?, fechaNacimiento = ?, fechaFallecimiento= ? WHERE autorID = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, autor.getNombre());
            pstmt.setString(2, autor.getPrimerApellido());
            pstmt.setDate(3, autor.getFechaNacimiento());
            pstmt.setDate(4, autor.getFechaFallecimiento());
            pstmt.setInt(5, autor.getAutorID());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminar(AutorMOD autor) {
        String query = "DELETE FROM Autor WHERE autorID = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, autor.getAutorID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
