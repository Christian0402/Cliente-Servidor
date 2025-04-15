/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.DAO;

import servidor.Conexion;
import shared.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class LibroDAO {

    public boolean insertar(Libro libro) {
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

    public ArrayList<Libro> obtener() {
        ArrayList<Libro> libros = new ArrayList<>();
        String query = "SELECT * FROM Libro";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Libro libro = new Libro(
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

    public boolean actualizar(Libro libro) {
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

    public boolean setDisponible(int id) {
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

    public List<Libro> buscarPorFiltroGeneral(String filtro) {
        List<Libro> lista = new ArrayList<>();
        String query = "SELECT * FROM libro WHERE "
                + "titulo LIKE ? OR "
                + "autorID IN (SELECT autorID FROM autor WHERE nombre LIKE ?) OR "
                + "categoriaID IN (SELECT categoriaID FROM categoria WHERE nombre LIKE ?)";

        try (PreparedStatement ps = Conexion.getConnection().prepareStatement(query)) {
            String likeFiltro = "%" + filtro + "%";
            ps.setString(1, likeFiltro);
            ps.setString(2, likeFiltro);
            ps.setString(3, likeFiltro);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setLibroID(rs.getInt("libroID"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutorID(rs.getInt("autorID"));
                libro.setCategoriaID(rs.getInt("categoriaID"));
                libro.setDisponibilidad(rs.getBoolean("disponibilidad"));
                libro.setAnoPublicacion(rs.getDate("anoPublicacion"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Error en búsqueda general: " + e.getMessage());
        }

        return lista;
    }
    
   
    public Date obtenerFechaFinalizacionPorLibro(int libroID) {
    String query = "SELECT fechaFinalizacion FROM prestamo WHERE libroID = ? AND estado = 'Activo' "
                 + "ORDER BY fechaFinalizacion ASC LIMIT 1";

    try (PreparedStatement ps = Conexion.getConnection().prepareStatement(query)) {
        ps.setInt(1, libroID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDate("fechaFinalizacion");
        } else {
            
            String fallbackQuery = "SELECT fechaFinalizacion FROM prestamo WHERE libroID = ? "
                                 + "ORDER BY fechaFinalizacion DESC LIMIT 1";

            try (PreparedStatement ps2 = Conexion.getConnection().prepareStatement(fallbackQuery)) {
                ps2.setInt(1, libroID);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    return rs2.getDate("fechaFinalizacion");
                }
            }
        }
    } catch (Exception e) {
        System.out.println("Error al obtener la fecha de finalización: " + e.getMessage());
    }

    return null;
}
    

}
