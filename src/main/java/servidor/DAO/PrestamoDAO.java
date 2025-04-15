/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.DAO;

import servidor.Conexion;

import shared.Prestamo;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PrestamoDAO {
    public ArrayList<Prestamo> obtener() {
        ArrayList<Prestamo> prestamos = new ArrayList<>();
        String query = "SELECT * FROM Prestamo";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Prestamo prestamo = new Prestamo(
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
    
    
    
    public boolean agregar(Prestamo prestamo) {
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
     public List<Prestamo> obtenerPorUsuarioID(int usuarioID) {
    List<Prestamo> prestamos = new ArrayList<>();
    String query = "SELECT * FROM Prestamo WHERE usuarioID = ?";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, usuarioID); 
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
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
    String query = "UPDATE Prestamo SET estado= ?, fechaDevolucion = ?, multa = ? WHERE prestamoID = ?";

    try (Connection conn = Conexion.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        // Obtenemos la fecha límite de entrega
        Date fechaLimite = obtenerFechaFinalizacionPorID(idPrestamo);
        double multa = 0.0;

        if (fechaDevolucion.after(fechaLimite)) {
            long diffMilis = fechaDevolucion.getTime() - fechaLimite.getTime();
            long diasAtraso = diffMilis / (1000 * 60 * 60 * 24);
            multa = diasAtraso * 500; // ₡500 por día
        }

        pstmt.setString(1, "Finalizado");
        pstmt.setDate(2, fechaDevolucion);
        pstmt.setDouble(3, multa);
        pstmt.setInt(4, idPrestamo);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
     
     
     public Date obtenerFechaFinalizacionPorID(int idPrestamo) {
    String query = "SELECT fechaFinalizacion FROM Prestamo WHERE prestamoID = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, idPrestamo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDate("fechaFinalizacion");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

     public boolean tieneMultasActivas(int usuarioID) {
    String query = "SELECT COUNT(*) FROM Prestamo WHERE usuarioID = ? AND multa > 0";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, usuarioID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
     
     public double obtenerMultaPorID(int idPrestamo) {
    String query = "SELECT multa FROM Prestamo WHERE prestamoID = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, idPrestamo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble("multa");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0;
}
     
     public boolean pagarMultas(int usuarioID) {
    String query = "UPDATE prestamo SET multa = 0.0 WHERE usuarioID = ? AND multa > 0";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, usuarioID);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error al pagar multas: " + e.getMessage());
        return false;
    }
}
    public double obtenerMultaTotal(int usuarioID) {
        String query = "SELECT SUM(multa) FROM prestamo WHERE usuarioID = ? AND multa > 0";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, usuarioID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
     
     

    
  
}
