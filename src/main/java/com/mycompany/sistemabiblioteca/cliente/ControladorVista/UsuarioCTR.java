/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.ControladorVista;

import com.mycompany.sistemabiblioteca.cliente.Controllers.UsuarioController;

import shared.Usuario;
import com.mycompany.sistemabiblioteca.cliente.Vista.InicioSesion;
import com.mycompany.sistemabiblioteca.cliente.Vista.RegistroUsuario;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class UsuarioCTR implements ActionListener {

    private final ArrayList<Usuario> modelos; //Para las consultas.
    private Usuario modelo; //Agregar , Editar y Elimninar.
    private UsuarioController usuarioController;
    private final InicioSesion vistaInicio;
    private final RegistroUsuario vistaRegistro;
    private final PrincipalAdmin vistaAdmin;
    //private Cliente cliente;

    public UsuarioCTR() {
        this.modelos = new ArrayList();
        this.modelo = new Usuario();
        this.vistaInicio = new InicioSesion();
        this.vistaRegistro = new RegistroUsuario();
        this.vistaAdmin = new PrincipalAdmin();
        this.usuarioController = new UsuarioController();
        this.vistaInicio.btnRegistrarse.addActionListener(this);
        this.vistaInicio.btnInicioSesion.addActionListener(this);
        this.vistaRegistro.btnRegistrar.addActionListener(this);
        //this.cliente = new Cliente();
    }

    public void inciar() {

        vistaInicio.setTitle("Inicio Sesion");
        vistaInicio.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vistaRegistro.isVisible() == true) {
            try {
                modelo.setNombre(vistaRegistro.inputNombre.getText());
                modelo.setPrimerApellido(vistaRegistro.inputPrimerApellido.getText());
                modelo.setSegundoApellido(vistaRegistro.inputSegundoApellido.getText());
                modelo.setCorreo(vistaRegistro.inputCorreo.getText());
                modelo.setRol("Estudiante");
                modelo.setContrasena(vistaRegistro.inputContrasena.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaRegistro, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (vistaInicio.isVisible() == true) {
            try {
                modelo.setCorreo(vistaInicio.inputCorreo.getText());
                modelo.setContrasena(vistaInicio.inputContrasena.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaRegistro, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == vistaInicio.btnRegistrarse) {
            vistaInicio.dispose();
            vistaRegistro.setVisible(true);

        }
        if (e.getSource() == vistaInicio.btnInicioSesion) {
            System.out.println("INCIANDO SESION...");
            Usuario usuarioIngresado = null;

            try {
                List<Usuario> usuarios = usuarioController.iniciarSesion(modelo);

                if (usuarios == null) {
                    {
                        JOptionPane.showMessageDialog(vistaRegistro, "No hubo coincidencia con ese correo y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {

                    usuarioIngresado = usuarios.get(0);
                    SesionUsuario.getInstancia().iniciarSesion(usuarioIngresado);
                    vistaInicio.dispose();
                    if (usuarioIngresado.getRol().equals("Funcionario")) {

                        AdminCTR adcrt = new AdminCTR();

                        adcrt.inciar();

                    } else if ((usuarioIngresado.getRol().equals("Estudiante"))) {
                        EstudianteCTR escrt = new EstudianteCTR();
                        escrt.iniciar();
                    }

                }

            } catch (IOException ex) {
                ex.getMessage();
            }
        }

        if (e.getSource()
                == vistaRegistro.btnRegistrar) {
            try {
                //System.out.println("REGISTRANDO...");
                if (modelo.getNombre().equals("") || modelo.getPrimerApellido().equals("") || modelo.getSegundoApellido().equals("") || modelo.getCorreo().equals("") || modelo.getContrasena().equals("")) {
                    JOptionPane.showMessageDialog(vistaRegistro, "Es necesario rellenar todos los espacios.", "Error", JOptionPane.ERROR_MESSAGE);

                } else if (usuarioController.agregar(modelo)) {
                    JOptionPane.showMessageDialog(vistaRegistro, "Registro completado", "Success", JOptionPane.INFORMATION_MESSAGE);
                    vistaRegistro.dispose();
                    vistaInicio.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vistaRegistro, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }

}
