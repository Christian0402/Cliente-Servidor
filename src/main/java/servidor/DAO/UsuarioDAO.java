/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.DAO;

import servidor.Conexion;
import shared.Usuario;
import java.sql.Connection;
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
public class UsuarioDAO {

    public boolean agregar(Usuario usuario) {
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
    


    public List<Usuario> obtener() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {

                Usuario usuario = new Usuario(
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

    public boolean actualizar(Usuario usuario) {

        String query = "UPDATE Usuario SET nombre = ?,primerApellido=?,segundoApellido=?,rol=?,correo=?,contrasena=? WHERE usuarioID = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getPrimerApellido());
            pstmt.setString(3, usuario.getSegundoApellido());

            pstmt.setString(4, usuario.getRol());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getContrasena());
            pstmt.setInt(7, usuario.getUsuarioID());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM Usuario WHERE usuarioID = ?";
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

    public String obtnerNombreCompletoPorID(int id) {
        String query = "Select nombre,primerApellido from  Usuario  WHERE usuarioID = ?";
        String nombreCompleto = "";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nombreCompleto = rs.getString("nombre") + " " + rs.getString("primerApellido");

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return nombreCompleto;
    }

    public List<Usuario> iniciarSesion(String correo, String pwd) {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT usuarioID, nombre, primerApellido, segundoApellido, rol, correo, contrasena FROM Usuario WHERE correo = ? AND contrasena = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, correo);
            pstmt.setString(2, pwd);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
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

}
