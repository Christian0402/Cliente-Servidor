/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador.DAO;

import com.mycompany.sistemabiblioteca.cliente.Controlador.Conexion;

import com.mycompany.sistemabiblioteca.cliente.Modelo.PrestamoMOD;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class PrestamoDAO {
    public ArrayList<PrestamoMOD> obtener() {
        ArrayList<PrestamoMOD> prestamos = new ArrayList<>();
        String query = "SELECT * FROM Prestamo";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                PrestamoMOD prestamo = new PrestamoMOD(
                        rs.getInt("prestamoID"),
                        rs.getInt("usuarioID"),
                        rs.getInt("libroID"),
                        rs.getDate("fechaInicio"),
                        rs.getDate("fechaFinalizacion"),
                        rs.getString("estado"),
                        rs.getDouble("multa"),
                        rs.getDate("fechaDevolucion")
                        
                );

                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    
    
    
    public boolean insertar(PrestamoMOD prestamo) {
        String query = "INSERT INTO Prestamo (usuarioID,libroID,fechaInicio,fechaFinalizacion,estado,multa,fechaDevolucion) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1,prestamo.getUsuarioID() );
            pstmt.setInt(2, prestamo.getLibroID());
            pstmt.setDate(3, prestamo.getFechaInicio());
            pstmt.setDate(4, prestamo.getFechaFinalizacion());
            pstmt.setString(5, prestamo.getEstado());
            pstmt.setDouble(6,prestamo.getMulta());
            pstmt.setDate(7, prestamo.getFechaDevolucion());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public ArrayList<PrestamoMOD> obtenerPorUsuarioID(int usuarioID) {
    ArrayList<PrestamoMOD> prestamos = new ArrayList<>();
    String query = "SELECT * FROM Prestamo WHERE usuarioID = ?";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, usuarioID); 
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                PrestamoMOD prestamo = new PrestamoMOD(
                        rs.getInt("prestamoID"),
                        rs.getInt("usuarioID"),
                        rs.getInt("libroID"),
                        rs.getDate("fechaInicio"),
                        rs.getDate("fechaFinalizacion"),
                        rs.getString("estado"),
                        rs.getDouble("multa"),
                        rs.getDate("fechaDevolucion")
                );
                prestamos.add(prestamo);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return prestamos;
}
     
     public int obtenerIDLibroPorIDPrestamo(int idPrestamo) {
        String query = "SELECT libroID FROM Prestamo WHERE prestamoID=?";
        int libroID = -1;

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idPrestamo);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    libroID = rs.getInt("libroID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return libroID;
    }
     
     public boolean finalizarPrestamo(int idPrestamo, Date fechaDevolucion) {
        String query = "UPDATE Prestamo SET estado= ? ,fechaDevolucion=?  WHERE prestamoID = ?";
       
        
        
        try (Connection conn = Conexion.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1,"Finalizado");
            pstmt.setDate(2,fechaDevolucion);
            pstmt.setInt(3,idPrestamo);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
       
        
    }
     
     

    
  
}
