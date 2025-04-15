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
public class Prestamo {

    int prestamoID;
    int usuarioID;
    int libroID;
    Date fechaInicio;
    Date fechaFinalizacion;
    String estado ;
    Double multa;
    Date fechaDevolucion;

    public Prestamo(int prestamoID, int usuarioID, int libroID, Date fechaInicio, Date fechaFinalizacion, String estado, Double multa, Date fechaDevolucion) {
        this.prestamoID = prestamoID;
        this.usuarioID = usuarioID;
        this.libroID = libroID;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estado = estado;
        this.multa = multa;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Prestamo() {
    }

    public int getPrestamoID() {
        return prestamoID;
    }

    public void setPrestamoID(int prestamoID) {
        this.prestamoID = prestamoID;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    
}
