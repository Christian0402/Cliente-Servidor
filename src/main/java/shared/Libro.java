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
public class Libro {

    int libroID;
    String titulo;
    int autorID;
    int categoriaID;
    boolean disponibilidad;
    Date anoPublicacion;

    public Libro(int libroID, String titulo, int autorID, int categoriaID, boolean disponibilidad, Date anoPublicacion) {
        this.libroID = libroID;
        this.titulo = titulo;
        this.autorID = autorID;
        this.categoriaID = categoriaID;
        this.disponibilidad = disponibilidad;
        this.anoPublicacion = anoPublicacion;
    }

    public Libro() {
    }

    public int getLibroID() {
        return libroID;
    }

    public void setLibroID(int libroID) {
        this.libroID = libroID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAutorID() {
        return autorID;
    }

    public void setAutorID(int autorID) {
        this.autorID = autorID;
    }

    public int getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(int categoriaID) {
        this.categoriaID = categoriaID;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Date getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(Date anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }
    
    

}
