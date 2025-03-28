/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador;

import com.mycompany.sistemabiblioteca.cliente.Modelo.UsuarioMOD;
import com.mycompany.sistemabiblioteca.cliente.Vista.InicioSesion;
import com.mycompany.sistemabiblioteca.cliente.Vista.RegistroUsuario;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalAdmin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class UsuarioCTR implements ActionListener {

    private final ArrayList<UsuarioMOD> modelos; //Para las consultas.
    private UsuarioMOD modelo; //Agregar , Editar y Elimninar.
    private final InicioSesion vistaInicio;
    private final RegistroUsuario vistaRegistro;
    private final PrincipalAdmin vistaAdmin;

    public UsuarioCTR() {
        this.modelos = new ArrayList();
        this.modelo = new UsuarioMOD();
        this.vistaInicio = new InicioSesion();
        this.vistaRegistro = new RegistroUsuario();
        this.vistaAdmin= new PrincipalAdmin();

        this.vistaInicio.btnRegistrarse.addActionListener(this);
        this.vistaInicio.btnInicioSesion.addActionListener(this);
        this.vistaRegistro.btnRegistrar.addActionListener(this);

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
                modelo.setRol("Default");
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
            if (iniciarSesion(modelo)) {
                
                vistaInicio.dispose();
                modelo = SesionUsuario.getInstancia().getUsuario();
                System.out.println(modelo.getRol());
                if (modelo.getRol().equals("Default")) {
                   
                    AdminCTR adcrt = new AdminCTR();
                    adcrt.inciar();
        
                } else {
                    System.out.println("FALLO AL ENCONTRAR ROL");
                }

            } else {
                JOptionPane.showMessageDialog(vistaRegistro, "Error al inciar sesion", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == vistaRegistro.btnRegistrar) {
            System.out.println("REGISTRANDO...");
            if (agregar(modelo)) {
                JOptionPane.showMessageDialog(vistaRegistro, "Registro completado", "Success", JOptionPane.ERROR_MESSAGE);
                vistaRegistro.dispose();
                vistaInicio.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vistaRegistro, "Error al registrar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //METODOS CRUD
    private static boolean agregar(UsuarioMOD modelo) {
        String query = "INSERT INTO Usuario (nombre,primerApellido,segundoApellido,correo,contrasena,rol) VALUES (?,?,?,?,?,?)";
        try (Connection conn = Conexion.getConnection(); 
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, modelo.getNombre());
            pstmt.setString(2, modelo.getPrimerApellido());
            pstmt.setString(3, modelo.getSegundoApellido());
            pstmt.setString(4, modelo.getCorreo());
            pstmt.setString(5, modelo.getContrasena());
            pstmt.setString(6, modelo.getRol());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean iniciarSesion(UsuarioMOD modelo) {
        String query = "SELECT usuarioID,nombre,primerApellido,segundoApellido,rol,correo,contrasena FROM Usuario WHERE correo = ? AND contrasena=?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, modelo.getCorreo());
            pstmt.setString(2, modelo.getContrasena());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UsuarioMOD usuario = new UsuarioMOD(
                        rs.getInt("usuarioID"),
                        rs.getString("nombre"),
                        rs.getString("primerApellido"),
                        rs.getString("segundoApellido"),
                        rs.getString("rol"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                        
                );
                
                SesionUsuario.getInstancia().iniciarSesion(usuario);
                System.out.println(usuario.getNombre() + " METODO INICIAR SESION");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
 