/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.ControladorVista;
import shared.Usuario;

/**
 *
 * @author Usuario
 */
public class SesionUsuario {
    private static SesionUsuario instancia;
    private Usuario usuario;

    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}


