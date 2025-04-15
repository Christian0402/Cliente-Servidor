/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.DAO;

import servidor.Conexion;
import shared.Categoria;
import java.sql.Connection;
import java.sql.Date;

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
public class CategoriaDAO {

    public boolean agregar(Categoria categoria) {
        String query = "INSERT INTO Categoria (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoria.getNombre());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Categoria> obtener() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String query = "SELECT * FROM Categoria";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Categoria categoria = new Categoria(
                        rs.getInt("categoriaID"),
                        rs.getString("nombre")
                );

                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public boolean actualizar(Categoria categoria) {
        System.out.println(categoria.getCategoriaID() + categoria.getNombre() + "en el DAO");
        String query = "UPDATE Categoria SET nombre = ? WHERE categoriaID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, categoria.getNombre());
            pstmt.setInt(2, categoria.getCategoriaID());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM Categoria WHERE categoriaID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int buscarPorNombre(String nombre) {
        String query = "SELECT categoriaID FROM Categoria WHERE nombre=?";
        int categoriaID = -1;

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    categoriaID = rs.getInt("categoriaID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(categoriaID + "ES CATEGORIA ID ENCONTRADO");
        return categoriaID;
    }
    public String buscarPorID(int id) {
        String query = "SELECT nombre FROM Categoria WHERE categoriaID=?";
        String nombreCategoria = "";
      

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nombreCategoria = rs.getString("nombre");
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return (nombreCategoria);
    }

}
