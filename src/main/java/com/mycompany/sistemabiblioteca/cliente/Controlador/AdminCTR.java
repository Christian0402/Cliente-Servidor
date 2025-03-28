/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador;

import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.AutorDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.CategoriaDAO;
import com.mycompany.sistemabiblioteca.cliente.Modelo.AutorMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.CategoriaMOD;
import com.mycompany.sistemabiblioteca.cliente.Vista.AutoresAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.sistemabiblioteca.cliente.Vista.CategoriasAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalAdmin;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class AdminCTR implements ActionListener {

    private final PrincipalAdmin vistaPrincipal;
    private final CategoriasAdmin vistaCategorias;

    private final AutoresAdmin vistaAutores;

    //CATEGORIA
    private CategoriaMOD modeloCategoria;
    private ArrayList<CategoriaMOD> modelosCategoria;
    private final CategoriaDAO categoriaDAO;

    //AUTORES
    private AutorMOD modeloAutor;
    private ArrayList<AutorMOD> modelosAutor;
    private final AutorDAO autorDAO;

    public AdminCTR() {

        this.vistaPrincipal = new PrincipalAdmin();
        this.vistaCategorias = new CategoriasAdmin();
        this.vistaAutores = new AutoresAdmin();

        //MODELOS Y DAO's
        this.modeloCategoria = new CategoriaMOD();
        this.categoriaDAO = new CategoriaDAO();
        this.modelosCategoria = new ArrayList();
        this.modeloAutor = new AutorMOD();
        this.modelosAutor = new ArrayList();
        this.autorDAO = new AutorDAO();

        //Botones y demas 
        this.vistaPrincipal.btnCategorias.addActionListener(this);
        this.vistaPrincipal.btnAutores.addActionListener(this);

        //VISTA ADMIN CATEGORIAS
        this.vistaAutores.btnAgregar.addActionListener(this);
        this.vistaAutores.btnEditar.addActionListener(this);
        this.vistaAutores.btnEliminar.addActionListener(this);
        this.vistaAutores.inputNombreAutor.addActionListener(this);
        this.vistaAutores.inpuFechaNacimientoAutor.addActionListener(this);
        this.vistaAutores.inputFechaMuerteAutor.addActionListener(this);
        this.vistaAutores.inputApellidoAutor.addActionListener(this);
        this.vistaAutores.seleccionID.addActionListener(this);
        this.vistaAutores.tbAutores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaAutores.tbAutores.getSelectedRow();
                vistaAutores.seleccionID.setText(vistaAutores.tbAutores.getValueAt(fila, 0).toString());
                vistaAutores.inputNombreAutor.setText(vistaAutores.tbAutores.getValueAt(fila, 1).toString());
                vistaAutores.inputApellidoAutor.setText(vistaAutores.tbAutores.getValueAt(fila, 2).toString());
                vistaAutores.inpuFechaNacimientoAutor.setText(vistaAutores.tbAutores.getValueAt(fila, 3).toString());
                vistaAutores.inputFechaMuerteAutor.setText(vistaAutores.tbAutores.getValueAt(fila, 4).toString());

            }
        });
        //VISTA ADMIN AUTORES
        this.vistaCategorias.btnAgregar.addActionListener(this);
        this.vistaCategorias.btnEditar.addActionListener(this);
        this.vistaCategorias.btnEliminar.addActionListener(this);
        this.vistaCategorias.inputNombreCategoria.addActionListener(this);
        this.vistaCategorias.seleccionID.addActionListener(this);
        this.vistaCategorias.tbCategorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaCategorias.tbCategorias.getSelectedRow();
                vistaCategorias.seleccionID.setText(vistaCategorias.tbCategorias.getValueAt(fila, 0).toString());
                vistaCategorias.inputNombreCategoria.setText(vistaCategorias.tbCategorias.getValueAt(fila, 1).toString());
            }
        });

    }

    public void inciar() {
        vistaPrincipal.setTitle("Inicio Sesion");
        vistaPrincipal.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //PAGINA PRINCPAL 
        if (e.getSource() == vistaPrincipal.btnCategorias) {

            vistaPrincipal.dispose();
            vistaCategorias.setVisible(true);
            vistaCategorias.seleccionID.setVisible(false);
            cargarCategorias();
        }
        if (e.getSource() == vistaPrincipal.btnAutores) {

            vistaPrincipal.dispose();
            vistaAutores.setVisible(true);
            vistaAutores.seleccionID.setVisible(false);
            cargarAutores();
        }

        //SECCION DE CATEGORIAS
        if (e.getSource() == vistaCategorias.btnAgregar) {
            try {
                modeloCategoria.setNombre(vistaCategorias.inputNombreCategoria.getText());
                if (categoriaDAO.insertar(modeloCategoria)) {
                    JOptionPane.showMessageDialog(vistaCategorias, "Se agrego la categoria con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarCategorias();
                } else {
                    JOptionPane.showMessageDialog(vistaCategorias, "Error al agregar la categoria", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaCategorias, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == vistaCategorias.btnEditar) {

            try {
                modeloCategoria.setNombre(vistaCategorias.inputNombreCategoria.getText());
                modeloCategoria.setCategoriaID(Integer.parseInt(vistaCategorias.seleccionID.getText()));

//                System.out.println( modeloCategoria.getCategoriaID()+ "En el ADMIN CONTROLLER");
//                System.out.println(modeloCategoria.getNombre());
                if (categoriaDAO.actualizar(modeloCategoria)) {
                    JOptionPane.showMessageDialog(vistaCategorias, "Se actualizo la categoria con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarCategorias();
                } else {
                    JOptionPane.showMessageDialog(vistaCategorias, "Error al intentar actualizar", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaCategorias, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == vistaCategorias.btnEliminar) {

            try {
                modeloCategoria.setNombre(vistaCategorias.inputNombreCategoria.getText());
                modeloCategoria.setCategoriaID(Integer.parseInt(vistaCategorias.seleccionID.getText()));

                if (categoriaDAO.eliminar(modeloCategoria)) {
                    JOptionPane.showMessageDialog(vistaCategorias, "Se elimino la categoria con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarCategorias();
                } else {
                    JOptionPane.showMessageDialog(vistaCategorias, "Error al intentar eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaCategorias, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        //SECCION AUTORES
        if (e.getSource() == vistaAutores.btnAgregar) {
            try {

                modeloAutor.setNombre(vistaAutores.inputNombreAutor.getText());

                modeloAutor.setPrimerApellido(vistaAutores.inputApellidoAutor.getText());

                

                String fechaNacimientoString = vistaAutores.inpuFechaNacimientoAutor.getText();
                
                String fechaFallecimientoString = vistaAutores.inputFechaMuerteAutor.getText();
                
                
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDateNacimiento = formato.parse(fechaNacimientoString);
                java.util.Date utilDateMuerte = formato.parse(fechaFallecimientoString);
                
                java.sql.Date fechaNaciemiento = new java.sql.Date(utilDateNacimiento.getTime());
                java.sql.Date fechaMuerte = new java.sql.Date(utilDateMuerte.getTime());
                modeloAutor.setFechaNacimiento(fechaNaciemiento);
                
                modeloAutor.setFechaFallecimiento(fechaMuerte);
                

                
                
   
                if (autorDAO.insertar(modeloAutor)) {
                    JOptionPane.showMessageDialog(vistaAutores, "Se agrego al autor con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarAutores();
                } else {
                    JOptionPane.showMessageDialog(vistaAutores, "Error al agregar la categoria", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaAutores, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                System.out.println("Error al cambiar fecha");
            }
        }
        if (e.getSource() == vistaAutores.btnEditar) {

            try {
                modeloAutor.setNombre(vistaAutores.inputNombreAutor.getText());

                modeloAutor.setPrimerApellido(vistaAutores.inputApellidoAutor.getText());
                
                String fechaNacimientoString = vistaAutores.inpuFechaNacimientoAutor.getText();
                String fechaFallecimientoString = vistaAutores.inputFechaMuerteAutor.getText();
                
                
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDateNacimiento = formato.parse(fechaNacimientoString);
                java.util.Date utilDateMuerte = formato.parse(fechaFallecimientoString);
                
                java.sql.Date fechaNaciemiento = new java.sql.Date(utilDateNacimiento.getTime());
                java.sql.Date fechaMuerte = new java.sql.Date(utilDateMuerte.getTime());
                modeloAutor.setFechaNacimiento(fechaNaciemiento);
                modeloAutor.setFechaFallecimiento(fechaMuerte);
                

                modeloAutor.setAutorID(Integer.parseInt(vistaAutores.seleccionID.getText()));

                if (autorDAO.actualizar(modeloAutor)) {
                    JOptionPane.showMessageDialog(vistaAutores, "Se actualizo al autor con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarAutores();
                } else {
                    JOptionPane.showMessageDialog(vistaAutores, "Error al intentar actualizar", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaAutores, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }catch (ParseException ex) {
                System.out.println("Error al cambiar fecha");
            }
        }
        if (e.getSource() == vistaAutores.btnEliminar) {

            try {
                modeloAutor.setNombre(vistaAutores.inputNombreAutor.getText());

                modeloAutor.setPrimerApellido(vistaAutores.inputApellidoAutor.getText());

                String fechaTexto = vistaAutores.inpuFechaNacimientoAutor.getText();
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = formato.parse(fechaTexto);
                java.sql.Date fechaNaciemiento = new java.sql.Date(utilDate.getTime());

                modeloAutor.setFechaNacimiento(fechaNaciemiento);
                modeloAutor.setAutorID(Integer.parseInt(vistaAutores.seleccionID.getText()));

                if (autorDAO.eliminar(modeloAutor)) {
                    JOptionPane.showMessageDialog(vistaAutores, "Se elimino al autor con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarAutores();
                } else {
                    JOptionPane.showMessageDialog(vistaAutores, "Error al intentar eliminar", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaAutores, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                System.out.println("Error al cambiar fecha");
            }
        }

    }

    public void cargarCategorias() {
        modelosCategoria = categoriaDAO.obtener();
        DefaultTableModel modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Nombre");

        if (!modelosCategoria.isEmpty()) {
            for (CategoriaMOD categoria : modelosCategoria) {
                modelTable.addRow(new String[]{
                    String.valueOf(categoria.getCategoriaID()),
                    categoria.getNombre()
                });
            }
        } else {
            JOptionPane.showMessageDialog(vistaCategorias, "No hay categorias", "Error", JOptionPane.ERROR_MESSAGE);
        }

        vistaCategorias.tbCategorias.setModel(modelTable);
    }

    public void cargarAutores() {
        modelosAutor = autorDAO.obtener();
        DefaultTableModel modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Nombre");
        modelTable.addColumn("Apellido");
        modelTable.addColumn("Fecha Nacimiento");
        modelTable.addColumn("Fecha Muerte");

        if (!modelosAutor.isEmpty()) {
            for (AutorMOD autor : modelosAutor) {
                modelTable.addRow(new String[]{
                    String.valueOf(autor.getAutorID()),
                    autor.getNombre(),
                    autor.getPrimerApellido(),
                    autor.getFechaNacimiento().toString(),
                    autor.getFechaFallecimiento().toString()

                });
            }
        } else {
            JOptionPane.showMessageDialog(vistaAutores, "No hay autores registrados", "Error", JOptionPane.ERROR_MESSAGE);
        }

        vistaAutores.tbAutores.setModel(modelTable);
    }

}
