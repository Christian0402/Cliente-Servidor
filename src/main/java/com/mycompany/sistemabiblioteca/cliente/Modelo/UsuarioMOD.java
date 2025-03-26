/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Modelo;

/**
 *
 * @author Usuario
 */
public class UsuarioMOD {
int usuarioID ;
String nombre ;
String primerApellido;
String segundoApellido ;
String rol ;
String correo ;
String contrasena ;

    public UsuarioMOD(int usuarioID, String nombre, String primerApellido, String segundoApellido, String rol, String correo, String contrasena) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.rol = rol;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public UsuarioMOD() {
    }

    @Override
    public String toString() {
        return "UsuarioID: "+usuarioID+"nombre: "+nombre+ "primer Apeliido: "+primerApellido + "segundoApellido: "+segundoApellido+"rol: "+rol+"correo: "+correo+"contrasenna: "+contrasena;
    }

    
    

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }



}
