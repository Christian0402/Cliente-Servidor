/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class Reserva {
     private int reservaID;
    private int usuarioID;
    private int libroID;
    private Date fechaReserva;
    private Date fechaDisponible;

    public Reserva(int reservaID, int usuarioID, int libroID, Date fechaReserva, Date fechaDisponible) {
        this.reservaID = reservaID;
        this.usuarioID = usuarioID;
        this.libroID = libroID;
        this.fechaReserva = fechaReserva;
        this.fechaDisponible = fechaDisponible;
    }

    public Reserva(){
        
    }
    public int getReservaID() {
        return reservaID;
    }

    public void setReservaID(int reservaID) {
        this.reservaID = reservaID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public int getLibroID() {
        return libroID;
    }

    public void setLibroID(int libroID) {
        this.libroID = libroID;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Date getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(Date fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }
}
