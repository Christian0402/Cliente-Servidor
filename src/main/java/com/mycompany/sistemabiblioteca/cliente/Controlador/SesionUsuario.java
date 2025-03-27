/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador;
import com.mycompany.sistemabiblioteca.cliente.Modelo.UsuarioMOD;

/**
 *
 * @author Usuario
 */
public class SesionUsuario {
    private static SesionUsuario instancia;
    private UsuarioMOD usuario;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(UsuarioMOD usuario) {
        this.usuario = usuario;
    }

    public UsuarioMOD getUsuario() {
        return usuario;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}


