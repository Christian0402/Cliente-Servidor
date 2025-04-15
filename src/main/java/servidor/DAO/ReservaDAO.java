/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.DAO;
import java.sql.Connection;
import servidor.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import shared.Reserva;

/**
 *
 * @author Usuario
 */
public class ReservaDAO {
    

    public boolean insertar(Reserva reserva) {
        String query = "INSERT INTO reserva (usuarioID, libroID, fechaReserva, fechaDisponible) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, reserva.getUsuarioID());
            pstmt.setInt(2, reserva.getLibroID());
            pstmt.setDate(3, reserva.getFechaReserva());
            pstmt.setDate(4, reserva.getFechaDisponible());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error al intentar reservar: " + ex.getMessage());
            return false;
        }
    }

    public List<Reserva> obtenerPorUsuario(int usuarioID) {
        List<Reserva> reservas = new ArrayList<>();
        String query = "SELECT * FROM reserva WHERE usuarioID = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, usuarioID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setReservaID(rs.getInt("reservaId"));
                reserva.setUsuarioID(rs.getInt("usuarioID"));
                reserva.setLibroID(rs.getInt("libroID"));
                reserva.setFechaReserva(rs.getDate("fechaReserva"));
                reserva.setFechaDisponible(rs.getDate("fechaDisponible"));
                reservas.add(reserva);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener reservas: " + ex.getMessage());
        }

        return reservas;
    }
}
