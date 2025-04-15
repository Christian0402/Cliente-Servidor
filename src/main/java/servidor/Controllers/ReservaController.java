/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.Controllers;

import java.util.List;
import servidor.DAO.ReservaDAO;
import shared.Reserva;


/**
 *
 * @author Usuario
 */
public class ReservaController {
    private final ReservaDAO reservaDAO = new ReservaDAO();
    
    
    public boolean agregar(Reserva reserva){
        return reservaDAO.insertar(reserva);
                
    }
    
    public List<Reserva> obtenerPorUsuarioID(int id){
        return reservaDAO.obtenerPorUsuario(id);
    }
}
