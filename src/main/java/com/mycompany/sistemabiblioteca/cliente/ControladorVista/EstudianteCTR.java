/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.ControladorVista;

import com.mycompany.sistemabiblioteca.cliente.Controllers.AutorController;
import com.mycompany.sistemabiblioteca.cliente.Controllers.CategoriaController;
import com.mycompany.sistemabiblioteca.cliente.Controllers.LibroController;
import com.mycompany.sistemabiblioteca.cliente.Controllers.PrestamoController;
import com.mycompany.sistemabiblioteca.cliente.Controllers.ReservaController;

import shared.Autor;
import shared.Categoria;
import shared.Libro;
import shared.Prestamo;
import com.mycompany.sistemabiblioteca.cliente.Vista.NuevoPrestamoEstudiante;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrestamosEstudiante;
import com.mycompany.sistemabiblioteca.cliente.Vista.PrincipalEstudiante;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import shared.Reserva;

/**
 *
 * @author Usuario
 */
public class EstudianteCTR implements ActionListener {

    private final PrincipalEstudiante vistaPrincipal;
    private final NuevoPrestamoEstudiante vistaNuevoPrestamo;
    private final PrestamosEstudiante vistaPrestamos;

    private Libro modeloLibro;
    private List<Libro> modelosLibro;
    private final LibroController libroController;

    private final ReservaController reservaController;

    private Prestamo modeloPrestamo;
    private List<Prestamo> modelosPrestamo;
    private final PrestamoController prestamoController;

    private Autor modeloAutor;
    private List<Autor> modelosAutor;
    private AutorController autorController;

    private Categoria modeloCategoria;
    private CategoriaController categoriaController;
    private List<Categoria> modelosCategoria;

    public EstudianteCTR() {
        this.vistaPrincipal = new PrincipalEstudiante();
        this.vistaNuevoPrestamo = new NuevoPrestamoEstudiante();
        this.vistaPrestamos = new PrestamosEstudiante();
        this.autorController = new AutorController();
        this.categoriaController = new CategoriaController();
        this.libroController = new LibroController();
        this.reservaController = new ReservaController();
        this.modeloAutor = new Autor();
        this.modelosAutor = new ArrayList<>();
        this.modeloCategoria = new Categoria();
        this.modelosCategoria = new ArrayList<>();
        this.modeloPrestamo = new Prestamo();
        this.modelosPrestamo = new ArrayList<>();
        this.prestamoController = new PrestamoController();

        this.vistaPrincipal.btnNuevoPrestamo.addActionListener(this);
        this.vistaPrincipal.btnMisPrestamos.addActionListener(this);

        this.vistaNuevoPrestamo.inputFechaInicio.addActionListener(this);
        this.vistaNuevoPrestamo.inputFechaFinalizacion.addActionListener(this);
        this.vistaNuevoPrestamo.btnSolicitar.addActionListener(this);
        this.vistaNuevoPrestamo.btnReservar.addActionListener(this);
        this.vistaPrestamos.btnPagarMulta.addActionListener(this);
        this.vistaNuevoPrestamo.btnBuscarLibros.addActionListener(this);
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

        if (e.getSource() == vistaNuevoPrestamo.btnBuscarLibros) {
            String filtro = vistaNuevoPrestamo.inputFiltro.getText();
            cargarLibrosFiltrados(filtro);
        }

        if (e.getSource() == vistaPrincipal.btnNuevoPrestamo) {

            vistaPrincipal.dispose();
            vistaNuevoPrestamo.setVisible(true);

            vistaNuevoPrestamo.seleccionLibroID.setVisible(false);
            vistaNuevoPrestamo.seleccionUsuarioID.setVisible(false);

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

                int usuarioID = SesionUsuario.getInstancia().getUsuario().getUsuarioID();
                try {
                    if (prestamoController.tieneMultasActivas(usuarioID)) {
                        JOptionPane.showMessageDialog(vistaNuevoPrestamo, "No puede solicitar libros hasta pagar su multa.", "Multa pendiente", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
                }

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

                    if (vistaNuevoPrestamo.inputDisponibilidad.getText().equals("Disponible")) {
                        modeloPrestamo.setEstado("Activo");
                        cargarLibros();
                    } else {
                        modeloPrestamo.setEstado("Inactivo");
                    }

                    modeloPrestamo.setMulta(0.0);

                    if (prestamoController.agregar(modeloPrestamo) && vistaNuevoPrestamo.inputDisponibilidad.getText().equals("Disponible")) {
                        JOptionPane.showMessageDialog(vistaNuevoPrestamo, "Se agrego el prestamo con exito", "Success", JOptionPane.INFORMATION_MESSAGE);
                        libroController.cambiarDisponibilidad(modeloPrestamo.getLibroID());
                        cargarPrestamos();
                    } else {
                        int libroID = Integer.parseInt(vistaNuevoPrestamo.seleccionLibroID.getText());
                        Date fechaDisponible = libroController.obtenerFechaFinalizacionPorLibro(libroID);

                        if (fechaDisponible != null) {
                            reservarLibro(libroID, fechaDisponible);
                        } else {
                            JOptionPane.showMessageDialog(vistaNuevoPrestamo, "Este libro está prestado, pero no se pudo obtener una fecha estimada para su devolución.");
                        }

                        cargarPrestamos();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (Exception ex) {
                Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (e.getSource() == vistaPrestamos.btnPagarMulta) {
                try {
                    int usuarioID = SesionUsuario.getInstancia().getUsuario().getUsuarioID();

                    double multa = prestamoController.obtenerMultaTotal(usuarioID);

                    if (multa <= 0) {
                        JOptionPane.showMessageDialog(vistaPrestamos, "No tiene multas pendientes.");
                        return;
                    }

                    int confirmacion = JOptionPane.showConfirmDialog(vistaPrestamos,
                            "Tiene una multa de ₡" + multa + ". ¿Desea pagarla ahora?",
                            "Confirmar pago",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        boolean pagado = prestamoController.pagarMultas(usuarioID);
                        if (pagado) {
                            JOptionPane.showMessageDialog(vistaPrestamos, "Multa pagada exitosamente.");
                            cargarPrestamos(); 
                        } else {
                            JOptionPane.showMessageDialog(vistaPrestamos, "Ocurrió un error al procesar el pago.");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (e.getSource() == vistaPrestamos.btnFinalizar) {

                try {
                    int idPrestamo = Integer.parseInt(vistaPrestamos.seleccionID.getText());
                    Date fechaActual = new Date(System.currentTimeMillis());

                    try {
                        if (prestamoController.finalizar(idPrestamo, fechaActual)) {
                            double multa = prestamoController.obtenerMultaPorID(idPrestamo);
                            if (multa > 0) {
                                JOptionPane.showMessageDialog(vistaPrestamos, "Libro devuelto con retraso" + "Multa aplicada: ₡" + multa);
                            } else {

                                JOptionPane.showMessageDialog(vistaPrestamos, "Préstamo finalizado sin multa");

                            }

                            libroController.setDisponible(prestamoController.obtenerIDLibroPorIDPrestamo(idPrestamo));
                            cargarPrestamos();

                        } else {
                            JOptionPane.showMessageDialog(vistaPrestamos, "El libro no esta disponible", "Error", JOptionPane.ERROR_MESSAGE);
                            cargarPrestamos();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vistaPrestamos, "Error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == vistaNuevoPrestamo.btnReservar) {
                try {
                    int usuarioID = SesionUsuario.getInstancia().getUsuario().getUsuarioID();

                    if (prestamoController.tieneMultasActivas(usuarioID)) {
                        JOptionPane.showMessageDialog(vistaNuevoPrestamo,
                                "No puede reservar libros hasta pagar su multa.",
                                "Multa pendiente",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int filaSeleccionada = vistaNuevoPrestamo.tbLibros.getSelectedRow();
                    if (filaSeleccionada == -1) {
                        JOptionPane.showMessageDialog(vistaNuevoPrestamo,
                                "Debe seleccionar un libro en la tabla antes de reservar.");
                        return;
                    }

                    try {
                        int libroID = Integer.parseInt(vistaNuevoPrestamo.tbLibros.getValueAt(filaSeleccionada, 0).toString());
                        Date fechaDisponible = libroController.obtenerFechaFinalizacionPorLibro(libroID);

                        if (fechaDisponible != null) {
                            reservarLibro(libroID, fechaDisponible);
                        } else {
                            JOptionPane.showMessageDialog(vistaNuevoPrestamo,
                                    "No se encontró una fecha estimada de disponibilidad.");
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(vistaNuevoPrestamo,
                                "Error al leer el ID del libro.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public void iniciar() {
        vistaPrincipal.setTitle("Menu Estudiante");
        vistaPrincipal.setVisible(true);
    }

    public void cargarLibros() {
        try {
            modelosLibro = libroController.obtener();
            DefaultTableModel modelTable = new DefaultTableModel();
            modelTable.addColumn("Codigo");
            modelTable.addColumn("Titulo");
            modelTable.addColumn("Autor");
            modelTable.addColumn("Categoria");
            modelTable.addColumn("Disponibilidad");
            modelTable.addColumn("Año Publicacion");

            if (!modelosLibro.isEmpty()) {
                for (Libro libro : modelosLibro) {
                    modelTable.addRow(new String[]{
                        String.valueOf(libro.getLibroID()),
                        libro.getTitulo(),
                        autorController.buscarPorID(libro.getAutorID()),
                        categoriaController.buscarPorID(libro.getCategoriaID()),
                        libro.isDisponibilidad() ? "Disponible" : "No Disponible",
                        libro.getAnoPublicacion().toString()

                    });
                }
            } else {
                JOptionPane.showMessageDialog(vistaNuevoPrestamo, "No hay libros", "Error", JOptionPane.ERROR_MESSAGE);
            }

            vistaNuevoPrestamo.tbLibros.setModel(modelTable);
        } catch (IOException ex) {
            Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método que permite registrar una reserva cuando el libro no está disponible
    public void reservarLibro(int libroID, Date fechaDisponible) {

        try {
            Reserva reserva = new Reserva();// Instancia
            reserva.setUsuarioID(SesionUsuario.getInstancia().getUsuario().getUsuarioID());//Asignamos el ID del usuario que está actualmente logueado
            reserva.setLibroID(libroID);//Le asignamos el libro que se intenta reservar
            reserva.setFechaReserva(new Date(System.currentTimeMillis()));//Asignamos la fecha actual como la fecha de reserva
            reserva.setFechaDisponible(fechaDisponible);//Se brindara la fecha estimada en la que estará disponible el libro
            
            if (reservaController.agregar(reserva)) { // Guardamos en la DB
                JOptionPane.showMessageDialog(vistaNuevoPrestamo, "Libro reservado exitosamente. Estará disponible el: " + fechaDisponible);
            } else {
                JOptionPane.showMessageDialog(vistaNuevoPrestamo, "Error al intentar reservar el libro.");
            }
        } catch (IOException ex) {
            Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarPrestamos() {
        try {
            modelosPrestamo = prestamoController.obtenerPorUsuarioID(SesionUsuario.getInstancia().getUsuario().getUsuarioID());
            DefaultTableModel modelTable = new DefaultTableModel();
            modelTable.addColumn("Codigo");
            modelTable.addColumn("Libro");
            modelTable.addColumn("Fecha Inicio");
            modelTable.addColumn("Posible Fecha Fin");
            modelTable.addColumn("Estado");
            modelTable.addColumn("Multa");
            modelTable.addColumn("Fecha Devolucion Real");

            if (!modelosPrestamo.isEmpty()) {
                for (Prestamo prestamo : modelosPrestamo) {
                    String fechaDevolucion = "";
                    if (prestamo.getFechaDevolucion() == null) {
                        fechaDevolucion = "No definida";
                    } else {
                        fechaDevolucion = prestamo.getFechaDevolucion().toString();
                    }
                    modelTable.addRow(new String[]{
                        String.valueOf(prestamo.getPrestamoID()),
                        libroController.obtenerNombrePorID(prestamo.getLibroID()),
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
        } catch (IOException ex) {
            Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarLibrosFiltrados(String filtro) {
        try {
            modelosLibro = libroController.buscarPorFiltroGeneral(filtro);

            DefaultTableModel modelTable = new DefaultTableModel();
            modelTable.addColumn("Codigo");
            modelTable.addColumn("Titulo");
            modelTable.addColumn("Autor");
            modelTable.addColumn("Categoria");
            modelTable.addColumn("Disponibilidad");
            modelTable.addColumn("Año Publicacion");

            if (!modelosLibro.isEmpty()) {
                for (Libro libro : modelosLibro) {
                    modelTable.addRow(new String[]{
                        String.valueOf(libro.getLibroID()),
                        libro.getTitulo(),
                        autorController.buscarPorID(libro.getAutorID()),
                        categoriaController.buscarPorID(libro.getCategoriaID()),
                        libro.isDisponibilidad() ? "Disponible" : "No Disponible",
                        libro.getAnoPublicacion().toString()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(vistaNuevoPrestamo, "No se encontraron libros con ese filtro.");
            }

            vistaNuevoPrestamo.tbLibros.setModel(modelTable);
        } catch (IOException ex) {
            Logger.getLogger(EstudianteCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
