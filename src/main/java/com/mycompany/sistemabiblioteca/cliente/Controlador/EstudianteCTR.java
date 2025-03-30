/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controlador;

import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.AutorDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.CategoriaDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.LibroDAO;
import com.mycompany.sistemabiblioteca.cliente.Controlador.DAO.PrestamoDAO;
import com.mycompany.sistemabiblioteca.cliente.Modelo.AutorMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.CategoriaMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.LibroMOD;
import com.mycompany.sistemabiblioteca.cliente.Modelo.PrestamoMOD;
import com.mycompany.sistemabiblioteca.cliente.Vista.LibrosAdmin;
import com.mycompany.sistemabiblioteca.cliente.Vista.NuevoPrestamoEstudiante;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrestamosEstudiante;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalEstudiante;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class EstudianteCTR implements ActionListener {

    private final PrincipalEstudiante vistaPrincipal;
    private final NuevoPrestamoEstudiante vistaNuevoPrestamo;
    private final PrestamosEstudiante vistaPrestamos;

    private LibroMOD modeloLibro;
    private ArrayList<LibroMOD> modelosLibro;
    private final LibroDAO libroDAO;

    private PrestamoMOD modeloPrestamo;
    private ArrayList<PrestamoMOD> modelosPrestamo;
    private final PrestamoDAO prestamoDAO; 
    
    
    private AutorMOD modeloAutor;
    private ArrayList<AutorMOD> modelosAutor;
    private AutorDAO autorDAO;
    private CategoriaMOD modeloCategoria;
    private CategoriaDAO categoriaDAO;
    private ArrayList<CategoriaMOD> modelosCategoria;

    public EstudianteCTR() {
        this.vistaPrincipal = new PrincipalEstudiante();
        this.vistaNuevoPrestamo = new NuevoPrestamoEstudiante();
        this.vistaPrestamos = new PrestamosEstudiante();
        this.autorDAO = new AutorDAO();
        this.categoriaDAO = new CategoriaDAO();
        this.libroDAO = new LibroDAO();
        this.modeloAutor = new AutorMOD();
        this.modelosAutor = new ArrayList<>();
        this.modeloCategoria = new CategoriaMOD();
        this.modelosCategoria = new ArrayList<>();
        this.modeloPrestamo = new PrestamoMOD();
        this.modelosPrestamo= new ArrayList<>();
        this.prestamoDAO = new PrestamoDAO();
        
        
        

        this.vistaPrincipal.btnNuevoPrestamo.addActionListener(this);
        this.vistaPrincipal.btnMisPrestamos.addActionListener(this);

        this.vistaNuevoPrestamo.inputFechaInicio.addActionListener(this);
        this.vistaNuevoPrestamo.inputFechaFinalizacion.addActionListener(this);
        this.vistaNuevoPrestamo.btnSolicitar.addActionListener(this);
        this.vistaNuevoPrestamo.btnVolverLibros.addActionListener(this);
        this.vistaNuevoPrestamo.inputAutor.addActionListener(this);
        this.vistaNuevoPrestamo.inputCategoria.addActionListener(this);
        this.vistaNuevoPrestamo.inputDisponibilidad.addActionListener(this);
        this.vistaNuevoPrestamo.inputNombre.addActionListener(this);
        this.vistaNuevoPrestamo.inputPublicacion.addActionListener(this);

        this.vistaNuevoPrestamo.seleccionUsuarioID.addActionListener(this);
        this.vistaNuevoPrestamo.seleccionLibroID.addActionListener(this);
        this.vistaNuevoPrestamo.tbLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vistaNuevoPrestamo.tbLibros.getSelectedRow();
                vistaNuevoPrestamo.seleccionLibroID.setText(vistaNuevoPrestamo.tbLibros.getValueAt(fila, 0).toString());
                vistaNuevoPrestamo.inputNombre.setText(vistaNuevoPrestamo.tbLibros.getValueAt(fila, 1).toString());
                vistaNuevoPrestamo.inputAutor.setText(vistaNuevoPrestamo.tbLibros.getValueAt(fila, 2).toString());
                vistaNuevoPrestamo.inputCategoria.setText(vistaNuevoPrestamo.tbLibros.getValueAt(fila, 3).toString());
                vistaNuevoPrestamo.inputDisponibilidad.setText(vistaNuevoPrestamo.tbLibros.getValueAt(fila, 4).toString());
                vistaNuevoPrestamo.inputPublicacion.setText(vistaNuevoPrestamo.tbLibros.getValueAt(fila, 5).toString());

            }

        });
        
        
        this.vistaPrestamos.btnFinalizar.addActionListener(this);
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
                vistaPrestamos.inputNombreLibro.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 1).toString());
                vistaPrestamos.inputInicio.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 2).toString());
                vistaPrestamos.inputFin.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 3).toString());
                vistaPrestamos.inputEstado.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 4).toString());
                vistaPrestamos.inputMulta.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 5).toString());
                vistaPrestamos.inputDevolucion.setText(vistaPrestamos.tbPrestamos.getValueAt(fila, 6).toString());
            }

        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.btnNuevoPrestamo) {

            vistaPrincipal.dispose();
            vistaNuevoPrestamo.setVisible(true);

            vistaNuevoPrestamo.seleccionLibroID.setVisible(false);
            
            cargarLibros();
        }
        if (e.getSource() == vistaPrincipal.btnMisPrestamos) {

            vistaPrincipal.dispose();
            vistaPrestamos.setVisible(true);

            vistaPrestamos.seleccionID.setVisible(false);
            
            cargarPrestamos();
        }
        if (e.getSource() == vistaNuevoPrestamo.btnVolverLibros || e.getSource() == vistaPrestamos.btnVolver) {

            vistaPrincipal.setVisible(true);
            vistaPrestamos.setVisible(false);
            vistaNuevoPrestamo.setVisible(false);
        
            
            
        }
        
        
        
        if (e.getSource() == vistaNuevoPrestamo.btnSolicitar) {

            try {
                modeloPrestamo.setUsuarioID(SesionUsuario.getInstancia().getUsuario().getUsuarioID());
                modeloPrestamo.setLibroID(Integer.parseInt(vistaNuevoPrestamo.seleccionLibroID.getText()));
                
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                
                String fechaInicioString = vistaNuevoPrestamo.inputFechaInicio.getText();
                String fechaFinalizacionString = vistaNuevoPrestamo.inputFechaFinalizacion.getText();
                
                java.util.Date utilDateInicio = formato.parse(fechaInicioString);
                java.util.Date utilDateFin = formato.parse(fechaFinalizacionString);
                
                
                java.sql.Date fechaInicio = new java.sql.Date(utilDateInicio.getTime());
                java.sql.Date fechaFin = new java.sql.Date(utilDateFin.getTime());
                
                modeloPrestamo.setFechaInicio(fechaInicio);
                modeloPrestamo.setFechaFinalizacion(fechaFin);
                
                if (vistaNuevoPrestamo.inputDisponibilidad.getText().equals("Disponible")){
                    modeloPrestamo.setEstado("Activo");
                    cargarLibros();
                }else {
                    modeloPrestamo.setEstado("Inactivo");
                    
                }
                modeloPrestamo.setMulta(0.0);
                
                
                if (prestamoDAO.insertar(modeloPrestamo) && vistaNuevoPrestamo.inputDisponibilidad.getText().equals("Disponible")) {
                    JOptionPane.showMessageDialog(vistaNuevoPrestamo, "Se agrego el prestamo con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    libroDAO.cambiarDisponibilidad(modeloPrestamo.getLibroID());
                    cargarPrestamos();
                } else {
                    JOptionPane.showMessageDialog(vistaNuevoPrestamo, "El libro no esta disponible", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarPrestamos();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaNuevoPrestamo, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vistaPrestamos.btnFinalizar) {

            try {
                int idPrestamo=Integer.parseInt(vistaPrestamos.seleccionID.getText());
                Date fechaActual = new Date(System.currentTimeMillis());
                
                if (prestamoDAO.finalizarPrestamo(idPrestamo,fechaActual) ){
                    JOptionPane.showMessageDialog(vistaPrestamos, "Se finalizo el prestamo con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    libroDAO.setDisponible(prestamoDAO.obtenerIDLibroPorIDPrestamo(idPrestamo));
                    cargarPrestamos();
                   
                } else {
                    JOptionPane.showMessageDialog(vistaPrestamos, "El libro no esta disponible", "Error", JOptionPane.ERROR_MESSAGE);
                    cargarPrestamos();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaPrestamos, "Error", "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
        
      
    }
    
    

    public void iniciar() {
        vistaPrincipal.setTitle("Menu Estudiante");
        vistaPrincipal.setVisible(true);
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
            JOptionPane.showMessageDialog(vistaNuevoPrestamo, "No hay libros", "Error", JOptionPane.ERROR_MESSAGE);
        }

        vistaNuevoPrestamo.tbLibros.setModel(modelTable);
    }

    public void cargarPrestamos(){
        modelosPrestamo = prestamoDAO.obtenerPorUsuarioID(SesionUsuario.getInstancia().getUsuario().getUsuarioID());
        DefaultTableModel modelTable = new DefaultTableModel();
        modelTable.addColumn("Codigo");
        modelTable.addColumn("Libro");
        modelTable.addColumn("Fecha Inicio");
        modelTable.addColumn("Posible Fecha Fin");
        modelTable.addColumn("Estado");
        modelTable.addColumn("Multa");
        modelTable.addColumn("Fecha Devolucion Real");

        if (!modelosPrestamo.isEmpty()) {
            for (PrestamoMOD prestamo : modelosPrestamo) {
                String fechaDevolucion="";
                if (prestamo.getFechaDevolucion() ==null){
                     fechaDevolucion="No definida";
                }else {
                    fechaDevolucion=prestamo.getFechaDevolucion().toString();
                }
                modelTable.addRow(new String[]{
                    String.valueOf(prestamo.getPrestamoID()),
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
