/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.DAO;

import servidor.Conexion;
import shared.Autor;
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

    public boolean agregar(Autor autor) {
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

    public ArrayList<Autor> obtener() {
        ArrayList<Autor> autores = new ArrayList<>();
        String query = "SELECT * FROM Autor";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Autor autor = new Autor(
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

    public boolean actualizar(Autor autor) {
        //System.out.println(categoria.getCategoriaID() + categoria.getNombre()+ "en el DAO");
        String query = "UPDATE Autor SET nombre = ?,primerApellido = ?, fechaNacimiento = ?, fechaFallecimiento= ? WHERE autorID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
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

    public boolean eliminar(int id) {
        String query = "DELETE FROM Autor WHERE autorID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String buscarPorID(int id) {
        String query = "SELECT nombre,primerApellido FROM Autor WHERE autorID=?";
        String nombreAutor = "";
        String apellidoAutor="";

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nombreAutor = rs.getString("nombre");
                    apellidoAutor = rs.getString("primerApellido");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(autorID + "ES AUTOR ID ENCONTRADO");
        return (nombreAutor+" "+apellidoAutor);
    }
}
