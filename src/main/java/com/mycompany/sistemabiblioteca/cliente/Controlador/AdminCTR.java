/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.sistemabiblioteca.cliente.Vista.CategoriasAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalAdmin;

/**
 *
 * @author Usuario
 */
public class AdminCTR implements ActionListener{

    private final CategoriasAdmin vistaCategorias;
    private final PrincipalAdmin vistaPrincipal;

    public AdminCTR() {
        this.vistaCategorias = new CategoriasAdmin();
        this.vistaPrincipal = new PrincipalAdmin();
        
        this.vistaPrincipal.btnCategorias.addActionListener(this);
    }

   
    public void inciar() {
        vistaPrincipal.setTitle("Inicio Sesion");
        vistaPrincipal.setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.btnCategorias ) {
            System.out.println("BTN CATEGORIAS");
            vistaPrincipal.dispose();

            vistaCategorias.setVisible(true);

        }
    }
    
}
