/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador;

import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.AutorDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.CategoriaDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.LibroDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.PrestamoDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.UsuarioDAO;
import com.mycompany.sistemabiblioteca.cliente.Modelo.AutorMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.CategoriaMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.LibroMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.PrestamoMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.UsuarioMOD;
import com.mycompany.sistemabiblioteca.cliente.Vista.AutoresAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.sistemabiblioteca.cliente.Vista.CategoriasAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.LibrosAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrestamosAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.UsuariosAdmin;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    private final LibrosAdmin vistaLibros;
    private final UsuariosAdmin vistaUsuarios;
    private final PrestamosAdmin vistaPrestamos;
    //CATEGORIA
    private CategoriaMOD modeloCategoria;
    private ArrayList<CategoriaMOD> modelosCategoria;
    private final CategoriaDAO categoriaDAO;

    //AUTORES
    private AutorMOD modeloAutor;
    private ArrayList<AutorMOD> modelosAutor;
    private final AutorDAO autorDAO;

    //LIBROS
    private LibroMOD modeloLibro;
    private ArrayList<LibroMOD> modelosLibro;
    private final LibroDAO libroDAO;

    //USUARIOS
    private UsuarioMOD modeloUsuario;
    private ArrayList<UsuarioMOD> modelosUsuario;
    private final UsuarioDAO usuarioDAO;

    //PRESTAMOS
    private PrestamoMOD modeloPrestamo;
    private ArrayList<PrestamoMOD> modelosPrestamo;
    private final PrestamoDAO prestamoDAO;

    
    public AdminCTR() {

        this.vistaPrincipal = new PrincipalAdmin();
        this.vistaCategorias = new CategoriasAdmin();
        this.vistaAutores = new AutoresAdmin();
        this.vistaLibros = new LibrosAdmin();
        this.vistaUsuarios = new UsuariosAdmin();
        this.vistaPrestamos= new PrestamosAdmin(); 

        //MODELOS Y DAO's
        this.modeloCategoria = new CategoriaMOD();
        this.categoriaDAO = new CategoriaDAO();
        this.modelosCategoria = new ArrayList();
        this.modeloAutor = new AutorMOD();
        this.modelosAutor = new ArrayList();
        this.autorDAO = new AutorDAO();
        this.modeloLibro = new LibroMOD();
        this.libroDAO = new LibroDAO();
        this.modelosLibro = new ArrayList();
        this.usuarioDAO = new UsuarioDAO();
        this.modeloUsuario = new UsuarioMOD();
        this.modelosUsuario = new ArrayList();
        this.modelosPrestamo =new ArrayList();
        this.modeloPrestamo = new PrestamoMOD();
        this.prestamoDAO = new PrestamoDAO();
        
        
        //Botones y demas 
        this.vistaPrincipal.btnCategorias.addActionListener(this);
        this.vistaPrincipal.btnAutores.addActionListener(this);
        this.vistaPrincipal.btnLibros.addActionListener(this);
        this.vistaPrincipal.btnUsuarios.addActionListener(this);
        this.vistaPrincipal.btnPrestamos.addActionListener(this);

        //VISTA ADMIN CATEGORIAS
        this.vistaCategorias.btnVolverCategorias.addActionListener(this);
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
        //VISTA ADMIN AUTORES
        this.vistaAutores.btnVolverAutores.addActionListener(this);
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

        //VISTA ADMIN LIBROS
        this.vistaLibros.btnVolverLibros.addActionListener(this);
        this.vistaLibros.btnAgregar.addActionListener(this);
        this.vistaLibros.btnEditar.addActionListener(this);
        this.vistaLibros.btnEliminar.addActionListener(this);
        this.vistaLibros.inputNombre.addActionListener(this);
        this.vistaLibros.inputDisponibilidad.addActionListener(this);
        this.vistaLibros.inputPublicacion.addActionListener(this);
        this.vistaLibros.seleccionID.addActionListener(this);
        this.vistaLibros.seleccionAutorID.addActionListener(this);
        this.vistaLibros.seleccionCategoriaID.addActionListener(this);
        this.vistaLibros.inputAutor.addActionListener(this);
        this.vistaLibros.inputCategoria.addActionListener(this);
        this.vistaLibros.tbLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaLibros.tbLibros.getSelectedRow();
                vistaLibros.seleccionID.setText(vistaLibros.tbLibros.getValueAt(fila, 0).toString());
                vistaLibros.inputNombre.setText(vistaLibros.tbLibros.getValueAt(fila, 1).toString());
                vistaLibros.inputAutor.setSelectedItem(vistaLibros.tbLibros.getValueAt(fila, 2).toString());
                vistaLibros.inputCategoria.setSelectedItem(vistaLibros.tbLibros.getValueAt(fila, 3).toString());
                vistaLibros.inputDisponibilidad.setSelectedItem(vistaLibros.tbLibros.getValueAt(fila, 4).toString());
                vistaLibros.inputPublicacion.setText(vistaLibros.tbLibros.getValueAt(fila, 5).toString());

            }

        });

        //VISTA ADMIN USUARIOS
        this.vistaUsuarios.btnAgregar.addActionListener(this);
        this.vistaUsuarios.btnEditar.addActionListener(this);
        this.vistaUsuarios.btnEliminar.addActionListener(this);
        this.vistaUsuarios.btnVolverUsuarios.addActionListener(this);
        this.vistaUsuarios.inputCorreo.addActionListener(this);
        this.vistaUsuarios.inputNombre.addActionListener(this);
        this.vistaUsuarios.input1Apellido.addActionListener(this);
        this.vistaUsuarios.input2Apellido.addActionListener(this);
        this.vistaUsuarios.inputContra.addActionListener(this);
        this.vistaUsuarios.seleccionID.addActionListener(this);
        this.vistaUsuarios.inputRol.addActionListener(this);
        this.vistaUsuarios.tbUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaUsuarios.tbUsuarios.getSelectedRow();
                vistaUsuarios.seleccionID.setText(vistaUsuarios.tbUsuarios.getValueAt(fila, 0).toString());
                vistaUsuarios.inputNombre.setText(vistaUsuarios.tbUsuarios.getValueAt(fila, 1).toString());
                vistaUsuarios.input1Apellido.setText(vistaUsuarios.tbUsuarios.getValueAt(fila, 2).toString());
                vistaUsuarios.input2Apellido.setText(vistaUsuarios.tbUsuarios.getValueAt(fila, 3).toString());
                vistaUsuarios.inputRol.setSelectedItem(vistaUsuarios.tbUsuarios.getValueAt(fila, 4).toString());
                vistaUsuarios.inputCorreo.setText(vistaUsuarios.tbUsuarios.getValueAt(fila, 5).toString());
                vistaUsuarios.inputContra.setText(vistaUsuarios.tbUsuarios.getValueAt(fila, 6).toString());

            }

        });
        
        //VISTA ADMIN PRESTAMOS
        this.vistaPrestamos.btnFinalizar.addActionListener(this);
        this.vistaPrestamos.inputNombreEstudiante.addActionListener(this);
        this.vistaPrestamos.btnVolver.addActionListener(this);
        this.vistaPrestamos.inputNombreLibro.addActionListener(this);
        this.vistaPrestamos.inputInicio.addActionListener(this);
        this.vistaPrestamos.inputFin.addActionListener(this);
        this.vistaPrestamos.inputEstado.addActionListener(this);
        this.vistaPrestamos.inputMulta.addActionListener(this);
        this.vistaPrestamos.inputDevolucion.addActionListener(this);
        this.vistaPrestamos.tbPrestamos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaPrestamos.tbPrestamos.getSelectedRow();
                vistaPrestamos.seleccionID.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 0).toString());
                vistaPrestamos.inputNombreEstudiante.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 1).toString());
                vistaPrestamos.inputNombreLibro.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 2).toString());
                vistaPrestamos.inputInicio.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 3).toString());
                vistaPrestamos.inputFin.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 4).toString());
                vistaPrestamos.inputEstado.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 5).toString());
                vistaPrestamos.inputMulta.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 6).toString());
                vistaPrestamos.inputDevolucion.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 7).toString());
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
        if (e.getSource() == vistaCategorias.btnVolverCategorias || e.getSource() == vistaAutores.btnVolverAutores || e.getSource() == vistaLibros.btnVolverLibros || e.getSource() == vistaUsuarios.btnVolverUsuarios || e.getSource() == vistaPrestamos.btnVolver) {
            vistaCategorias.dispose();
            vistaAutores.dispose();
            vistaLibros.dispose();
            vistaUsuarios.dispose();
            vistaPrestamos.dispose();
            vistaPrincipal.setVisible(true);

        }
        if (e.getSource() == vistaPrincipal.btnAutores) {

            vistaPrincipal.dispose();
            vistaAutores.setVisible(true);
            vistaAutores.seleccionID.setVisible(false);
            
            cargarAutores();
        }
        if (e.getSource() == vistaPrincipal.btnLibros) {
            vistaPrincipal.dispose();
            //System.out.println("BTN LIBROS");
            vistaLibros.setVisible(true);

            vistaLibros.seleccionID.setVisible(false);
            vistaLibros.seleccionAutorID.setVisible(false);
            vistaLibros.seleccionCategoriaID.setVisible(false);
            cargarOpcionesDisponibilidad();
            cargarCategoriasLibros();
            cargarAutoresLibros();
            cargarLibros();
        }
        if (e.getSource() == vistaPrincipal.btnUsuarios) {
            vistaPrincipal.dispose();
            //System.out.println("BTN LIBROS");
            vistaUsuarios.setVisible(true);
            vistaUsuarios.seleccionID.setVisible(false);
            cargarOpcionesRol();
            cargarUsuarios();

        }
        if (e.getSource() == vistaPrincipal.btnPrestamos) {
            vistaPrincipal.dispose();
            
            vistaPrestamos.setVisible(true);
            vistaPrestamos.seleccionID.setVisible(false);
            cargarPrestamos();
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
            } catch (ParseException ex) {
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

        //VISTA LIBROS
        if (e.getSource() == vistaLibros.btnAgregar) {
            try {

                modeloLibro.setTitulo(vistaLibros.inputNombre.getText());

                String categoriaNombre = (String) vistaLibros.inputCategoria.getSelectedItem();
                int categoriaID = categoriaDAO.buscarPorNombre(categoriaNombre);
                modeloLibro.setCategoriaID(categoriaID);

                String autorNombre = (String) vistaLibros.inputAutor.getSelectedItem();
                int autorID = autorDAO.buscarPorNombre(autorNombre);
                modeloLibro.setAutorID(autorID);

                if (vistaLibros.inputDisponibilidad.getSelectedItem().equals("Disponible")) {
                    modeloLibro.setDisponibilidad(true);
                } else {
                    modeloLibro.setDisponibilidad(false);
                }

                String fechaPublicacion = vistaLibros.inputPublicacion.getText();

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilFechaPublicion = formato.parse(fechaPublicacion);

                java.sql.Date fechaPublicacionSQL = new java.sql.Date(utilFechaPublicion.getTime());

                modeloLibro.setAnoPublicacion(fechaPublicacionSQL);

                if (libroDAO.insertar(modeloLibro)) {
                    JOptionPane.showMessageDialog(vistaLibros, "Se agrego al libro con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarLibros();
                } else {
                    JOptionPane.showMessageDialog(vistaLibros, "Error al agregar el libro", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarLibros();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaLibros, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                System.out.println("Error al hacer el cambio de fecha");
            }
        }
        if (e.getSource() == vistaLibros.btnEditar) {
            try {

                modeloLibro.setTitulo(vistaLibros.inputNombre.getText());

                String categoriaNombre = (String) vistaLibros.inputCategoria.getSelectedItem();

                int categoriaID = categoriaDAO.buscarPorNombre(categoriaNombre);

                System.out.println("SE BUSCA EL NOMNRE" + categoriaNombre + "Se obteine por ID: " + categoriaID);

                modeloLibro.setCategoriaID(categoriaID);

                String autorNombre = (String) vistaLibros.inputAutor.getSelectedItem();
                int autorID = autorDAO.buscarPorNombre(autorNombre);
                modeloLibro.setAutorID(autorID);

                if (vistaLibros.inputDisponibilidad.getSelectedItem().equals("Disponible")) {
                    modeloLibro.setDisponibilidad(true);
                } else {
                    modeloLibro.setDisponibilidad(false);
                }

                String fechaPublicacion = vistaLibros.inputPublicacion.getText();

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilFechaPublicion = formato.parse(fechaPublicacion);

                java.sql.Date fechaPublicacionSQL = new java.sql.Date(utilFechaPublicion.getTime());

                modeloLibro.setAnoPublicacion(fechaPublicacionSQL);

                modeloLibro.setLibroID(Integer.parseInt(vistaLibros.seleccionID.getText()));

                if (libroDAO.actualizar(modeloLibro)) {
                    JOptionPane.showMessageDialog(vistaLibros, "Se agrego edito con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarLibros();
                } else {
                    JOptionPane.showMessageDialog(vistaLibros, "Error al editar", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarLibros();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaLibros, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                System.out.println("Error al hacer el cambio de fecha");
            }
        }
        if (e.getSource() == vistaLibros.btnEliminar) {
            try {

                System.out.println(vistaLibros.seleccionID.getText() + "ES EL ID DEL LIBRO QUE SE ASIGNA EN LA TABLA");

                if (libroDAO.eliminar(Integer.parseInt(vistaLibros.seleccionID.getText()))) {
                    JOptionPane.showMessageDialog(vistaLibros, "Se  elimino con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarLibros();
                } else {
                    JOptionPane.showMessageDialog(vistaLibros, "Error al editar", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarLibros();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaLibros, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        //VISTA USUARIOS
        if (e.getSource() == vistaUsuarios.btnAgregar) {
            try {

                modeloUsuario.setNombre(vistaUsuarios.inputNombre.getText());
                modeloUsuario.setPrimerApellido(vistaUsuarios.input1Apellido.getText());
                modeloUsuario.setSegundoApellido(vistaUsuarios.input2Apellido.getText());
                modeloUsuario.setRol(vistaUsuarios.inputRol.getSelectedItem().toString());
                modeloUsuario.setCorreo(vistaUsuarios.inputCorreo.getText());
                modeloUsuario.setContrasena(vistaUsuarios.inputContra.getText());
                if (usuarioDAO.insertar(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaUsuarios, "Se agrego al usuario con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(vistaUsuarios, "Error al agregar al usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaUsuarios, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == vistaUsuarios.btnEditar) {
            try {
                modeloUsuario.setNombre(vistaUsuarios.inputNombre.getText());
                modeloUsuario.setPrimerApellido(vistaUsuarios.input1Apellido.getText());
                modeloUsuario.setSegundoApellido(vistaUsuarios.input2Apellido.getText());
                modeloUsuario.setRol(vistaUsuarios.inputRol.getSelectedItem().toString());
                modeloUsuario.setCorreo(vistaUsuarios.inputCorreo.getText());
                modeloUsuario.setContrasena(vistaUsuarios.inputContra.getText());
                modeloUsuario.setUsuarioID(Integer.parseInt(vistaUsuarios.seleccionID.getText()));
                System.out.println(modeloUsuario);
                
                if (usuarioDAO.actualizar(modeloUsuario)) {
                    JOptionPane.showMessageDialog(vistaUsuarios, "Se  edito con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(vistaUsuarios, "Error al editar", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarUsuarios();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaUsuarios, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == vistaUsuarios.btnEliminar) {
            try {

                if (usuarioDAO.eliminar(Integer.parseInt(vistaUsuarios.seleccionID.getText()))) {
                    JOptionPane.showMessageDialog(vistaUsuarios, "Se  elimino con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cargarUsuarios();
                } else {
                    JOptionPane.showMessageDialog(vistaUsuarios, "Error al editar", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarUsuarios();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaUsuarios, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        //VISTA PRESTAMOS 
        if (e.getSource()== vistaPrestamos.btnFinalizar){
            int idPrestamo=Integer.parseInt(vistaPrestamos.seleccionID.getText());
            System.out.println("EL ide del prestamp por finalizar es "+idPrestamo);
            Date fechaActual = new Date(System.currentTimeMillis());
             if (prestamoDAO.finalizarPrestamo(idPrestamo,fechaActual) ){
                    JOptionPane.showMessageDialog(vistaPrestamos, "Se finalizo el prestamo con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    libroDAO.setDisponible(prestamoDAO.obtenerIDLibroPorIDPrestamo(idPrestamo));
                    cargarPrestamos();
                   
                } else {
                    JOptionPane.showMessageDialog(vistaPrestamos, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarPrestamos();
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

    public void cargarLibros() {
        modelosLibro = libroDAO.obtener();
        DefaultTableModel modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Titulo");
        modelTable.addColumn("Autor");
        modelTable.addColumn("Categoria");
        modelTable.addColumn("Disponibilidad");
        modelTable.addColumn("Año Publicacion");

        if (!modelosLibro.isEmpty()) {
            for (LibroMOD libro : modelosLibro) {
                modelTable.addRow(new String[]{
                    String.valueOf(libro.getLibroID()),
                    libro.getTitulo(),
                    autorDAO.buscarPorID(libro.getAutorID()),
                    categoriaDAO.buscarPorID(libro.getCategoriaID()),
                    libro.isDisponibilidad() ? "Disponible" : "No Disponible",
                    libro.getAnoPublicacion().toString()

                });
            }
        } else {
            JOptionPane.showMessageDialog(vistaLibros, "No hay libros", "Error", JOptionPane.ERROR_MESSAGE);
        }

        vistaLibros.tbLibros.setModel(modelTable);
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

    public void cargarCategoriasLibros() {
        modelosCategoria = categoriaDAO.obtener();
        vistaLibros.inputCategoria.removeAllItems();

        for (CategoriaMOD categoria : modelosCategoria) {
            vistaLibros.inputCategoria.addItem(categoria.getNombre());
        }
    }

    public void cargarAutoresLibros() {
        modelosAutor = autorDAO.obtener();
        vistaLibros.inputAutor.removeAllItems();

        for (AutorMOD autor : modelosAutor) {
            vistaLibros.inputAutor.addItem(autor.getNombre());
            System.out.println(autor.getNombre());
        }
    }

    public void cargarOpcionesDisponibilidad() {
        vistaLibros.inputDisponibilidad.removeAllItems();
        vistaLibros.inputDisponibilidad.addItem("Disponible");
        vistaLibros.inputDisponibilidad.addItem("No Disponible");
    }

    public void cargarOpcionesRol() {
        vistaUsuarios.inputRol.removeAllItems();
        vistaUsuarios.inputRol.addItem("Funcionario");
        vistaUsuarios.inputRol.addItem("Estudiante");
    }

    public void cargarUsuarios() {
        modelosUsuario = usuarioDAO.obtener();
        DefaultTableModel modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Nombre");
        modelTable.addColumn("Primer Apellido");
        modelTable.addColumn("Segundo Apellido");
        modelTable.addColumn("Rol");
        modelTable.addColumn("Correo");
        modelTable.addColumn("Contrasena");
        

        if (!modelosUsuario.isEmpty()) {
            for (UsuarioMOD usuario : modelosUsuario) {
                modelTable.addRow(new String[]{
                    String.valueOf(usuario.getUsuarioID()),
                    usuario.getNombre(),
                    usuario.getPrimerApellido(),
                    usuario.getSegundoApellido(),
                    usuario.getRol(),
                    usuario.getCorreo(),
                    usuario.getContrasena()
                    
                    

                });
            }
        } else {
            JOptionPane.showMessageDialog(vistaLibros, "No hay usuarios", "Error", JOptionPane.ERROR_MESSAGE);
        }

        vistaUsuarios.tbUsuarios.setModel(modelTable);
    }
    
   public void cargarPrestamos(){
        modelosPrestamo = prestamoDAO.obtener();
        DefaultTableModel modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Nombre Estudiante");
        modelTable.addColumn("Libro");
        modelTable.addColumn("Fecha Inicio");
        modelTable.addColumn("Posible Fecha Fin");
        modelTable.addColumn("Estado");
        modelTable.addColumn("Multa");
        modelTable.addColumn("Fecha Devolucion Real");

        if (!modelosPrestamo.isEmpty()) {
            for (PrestamoMOD prestamo : modelosPrestamo) {
                String fechaDevolucion="";
                System.out.println(prestamo.getPrestamoID());
                if (prestamo.getFechaDevolucion() ==null){
                     fechaDevolucion="No definida";
                }else {
                    fechaDevolucion=prestamo.getFechaDevolucion().toString();
                }
                modelTable.addRow(new String[]{
                    String.valueOf(prestamo.getPrestamoID()),
                    usuarioDAO.obtnerNombreCompletoPorID(prestamo.getUsuarioID()),
                    libroDAO.obtenerNombrePorID(prestamo.getLibroID()),
                    prestamo.getFechaInicio().toString(),
                    prestamo.getFechaFinalizacion().toString(),
                    prestamo.getEstado(),
                    prestamo.getMulta().toString(),
                    fechaDevolucion

                });
            }
        } else {
            JOptionPane.showMessageDialog(vistaPrestamos, "No hay prestamos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        vistaPrestamos.tbPrestamos.setModel(modelTable);
    }
}
