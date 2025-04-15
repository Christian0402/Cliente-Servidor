package servidor;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import servidor.Controllers.AutorController;
import servidor.Controllers.CategoriaController;
import servidor.Controllers.LibroController;
import servidor.Controllers.PrestamoController;
import servidor.Controllers.ReservaController;
import servidor.Controllers.UsuarioController;
import shared.Autor;
import shared.Categoria;
import shared.Libro;
import shared.Prestamo;
import shared.Reserva;
import shared.Usuario;

public class ManejadorCliente implements Runnable {

    private Socket socket;
    private UsuarioController usuarioController = new UsuarioController();
    private CategoriaController categoriaController = new CategoriaController();
    private AutorController autorController = new AutorController();
    private LibroController libroController = new LibroController();
    private PrestamoController prestamoController = new PrestamoController();
    private ReservaController reservaController = new ReservaController();

    public ManejadorCliente(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        System.out.println("Cliente conectado: " + socket.getInetAddress());

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String request = in.readLine();
            System.out.println("La petición del cliente es: " + request);

            String response = processRequest(request);
            out.println(response);
            System.out.println("La respuesta  del server es: " + response);

        } catch (IOException e) {
            System.out.println("Error al manejar cliente: " + e.getMessage());
        }
    }

    private String processRequest(String request) {
        try {
            //USUARIO
            if (request.startsWith("INICIAR_SESION:")) {
                String datos = request.substring(15);
                String[] partes = datos.split(",");
                if (partes.length == 2) {
                    String correo = partes[0];
                    String contrasena = partes[1];
                    List<Usuario> encontrados = usuarioController.iniciarSesion(correo, contrasena);
                    if (encontrados != null) {
                        return usuariosToString(encontrados);
                    }
                }
                return "ERROR";
            } else if (request.startsWith("AGREGAR_USUARIO:")) {
                Usuario usuario = parseUsuario(request.substring(16));
                System.out.println(usuario);
                if (usuarioController.agregar(usuario)) {
                    return "Usuario agregado correctamente";
                } else {
                    return "Error al agregar usuario";
                }
            } else if (request.startsWith("ACTUALIZAR_USUARIO:")) {
                Usuario usuario = parseUsuario(request.substring(19));
                System.out.println(usuario);
                if (usuarioController.actualizar(usuario)) {
                    return "Usuario actualizado correctamente";
                } else {
                    return "Error al actualizar usuario";
                }
            } else if (request.startsWith("ELIMINAR_USUARIO:")) {
                int id = Integer.parseInt(request.substring(17));
                if (usuarioController.eliminar(id)) {
                    return "Usuario eliminado correctamente";
                } else {
                    return "Error al eliminar usuario";
                }
            } else if (request.startsWith("GET_USUARIOS:")) {
                List<Usuario> usuarios = usuarioController.obtener();
                return usuariosToString(usuarios);
            } else if (request.startsWith("GET_NOMBRE_USUARIOS_BY_ID:")) {
                int id = Integer.parseInt(request.substring(26));
                return usuarioController.obtenerNombre(id);

                // CATEGORIA
            } else if (request.startsWith("AGREGAR_CATEGORIA:")) {
                Categoria categoria = parseCategoria(request.substring(18));
                System.out.println(categoria);
                if (categoriaController.agregar(categoria)) {
                    return "Categoria agregada correctamente";
                } else {
                    return "Error al agregar categoria";
                }
            } else if (request.startsWith("ACTUALIZAR_CATEGORIA:")) {
                Categoria categoria = parseCategoria(request.substring(21));
                System.out.println(categoria);
                if (categoriaController.actualizar(categoria)) {
                    return "Categoria actualizada correctamente";
                } else {
                    return "Error al actualizar categoria";
                }
            } else if (request.startsWith("ELIMINAR_CATEGORIA:")) {
                int id = Integer.parseInt(request.substring(19));
                if (categoriaController.eliminar(id)) {
                    return "Categoria eliminada correctamente";
                } else {
                    return "Error al eliminar usuario";
                }

            } else if (request.startsWith("GET_CATEGORIAS:")) {
                List<Categoria> categorias = categoriaController.obtener();
                return categoriasToString(categorias);

            } else if (request.startsWith("CATEGORIA_BUSCAR_POR_ID:")) {
                int id = Integer.parseInt(request.substring(24));
                return categoriaController.obtenerPorID(id);
                //AUTOR
            } else if (request.startsWith("AGREGAR_AUTOR:")) {
                Autor autor = parseAutor(request.substring(14));

                if (autorController.agregar(autor)) {
                    return "Autor agregado correctamente";
                } else {
                    return "Error al agregar autor";
                }

            } else if (request.startsWith("ACTUALIZAR_AUTOR:")) {
                Autor autor = parseAutor(request.substring(17));
                System.out.println(autor);
                if (autorController.actualizar(autor)) {
                    return "Autor actualizado correctamente";
                } else {
                    return "Error al actualizar autor";
                }

            } else if (request.startsWith("ELIMINAR_AUTOR:")) {
                int id = Integer.parseInt(request.substring(15));
                if (autorController.eliminar(id)) {
                    return "Autor eliminado correctamente";
                } else {
                    return "Error al eliminar autor";
                }

            } else if (request.startsWith("GET_AUTOR:")) {
                List<Autor> autores = autorController.obtener();
                return autoresToString(autores);

            } else if (request.startsWith("AUTOR_BUSCAR_POR_ID:")) {
                int id = Integer.parseInt(request.substring(20));
                return autorController.obtenerPorID(id);
            } //LIBRO
            else if (request.startsWith("AGREGAR_LIBRO:")) {
                Libro libro = parseLibro(request.substring(14));

                if (libroController.agregar(libro)) {
                    return "Libro agregado correctamente";
                } else {
                    return "Error al agregar autor";
                }

            } else if (request.startsWith("ACTUALIZAR_LIBRO:")) {
                Libro libro = parseLibro(request.substring(17));

                if (libroController.actualizar(libro)) {
                    return "Libro actualizado correctamente";
                } else {
                    return "Error al actualizar autor";
                }

            } else if (request.startsWith("ELIMINAR_LIBRO:")) {
                int id = Integer.parseInt(request.substring(15));
                if (libroController.eliminar(id)) {
                    return "Libro eliminado correctamente";
                } else {
                    return "Error al eliminar autor";
                }

            } else if (request.startsWith("GET_LIBRO:")) {
                List<Libro> libros = libroController.obtener();
                return librosToString(libros);

            } else if (request.startsWith("LIBRO_BUSCAR_POR_ID:")) {
                int id = Integer.parseInt(request.substring(20));
                return libroController.obtenerNombrePorID(id);
            } else if (request.startsWith("LIBRO_CAMBIAR_DISPONIBILIDAD:")) {
                int id = Integer.parseInt(request.substring(28));
                if (libroController.cambiarDisponibilidad(id)) {
                    return "Cambio de disponibilidad realizada";
                } else {
                    return "Error al cambiar la disponibilidad";
                }
            } else if (request.startsWith("LIBRO_SET_DISPONIBILIDAD:")) {
                int id = Integer.parseInt(request.substring(26));
                if (libroController.setDisponible(id)) {
                    return "Libro disponible";
                } else {
                    return "Error al cambiar la disponibilidad";
                }
            }
            else if (request.startsWith("FILTRO_LIBRO:")) {
                String filtro = request.substring(13);
                List<Libro> libros = libroController.buscarPorFiltroGeneral(filtro);
                return librosToString(libros);
            }else if (request.startsWith("OBTENER_FECHA_FINALIZACION_LIBRO:")) {
                int id = Integer.parseInt(request.substring(33));
                Date fecha = libroController.obtenerFechaFinalizacionPorLibro(id);
                return fecha.toString();
                
            } else if (request.startsWith("AGREGAR_PRESTAMO:")) {
                Prestamo prestamo = parsePrestamo(request.substring(17));

                if (prestamoController.agregar(prestamo)) {
                    return "Prestamo agregado correctamente";
                } else {
                    return "Error al agregar prestamo";
                }
            } else if (request.startsWith("GET_PRESTAMOS:")) {
                List<Prestamo> prestamos = prestamoController.obtener();
                System.out.println(prestamos);
                return prestamosToString(prestamos);

            } else if (request.startsWith("OBTENER_PRESTAMOS_POR_USUARIO_ID:")) {
                int id = Integer.parseInt(request.substring(33));
                List<Prestamo> prestamos = prestamoController.obtenerPorUsuarioID(id);
                return prestamosToString(prestamos);
            } else if (request.startsWith("OBTENER_ID_LIBRO_POR_PRESTAMO_ID:")) {
                int id = Integer.parseInt(request.substring(33));
                int idlibro = prestamoController.obtenerIDLibroPorIDPrestamo(id);

                return String.valueOf(idlibro);
            } else if (request.startsWith("FINALIZAR_PRESTAMO:")) {
                String datos = request.substring(19);
                String[] partes = datos.split(",");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    Date fecha = Date.valueOf(partes[1]);

                    if (prestamoController.finalizarPrestamo(id, fecha)) {
                        return "Finalizacion exitosa";
                    } else {
                        return "Error al finalizar";
                    }
                }
            } else if (request.startsWith("OBTENER_FECHA_FINALIZACION:")) {
                int id = Integer.parseInt(request.substring(27));
                Date fecha = prestamoController.obtenerFechaFinalizacionPorID(id);
                if (fecha != null) {
                    return fecha.toString();
                } else {
                    return "Fecha no encontrada";
                }

            } else if (request.startsWith("TIENE_MULTAS_ACTIVAS:")) {
                int usuarioID = Integer.parseInt(request.substring(22));
                boolean tieneMultas = prestamoController.tieneMultasActivas(usuarioID);
                return String.valueOf(tieneMultas);

            } else if (request.startsWith("OBTENER_MULTA_POR_ID:")) {
                int idPrestamo = Integer.parseInt(request.substring(22));
                double multa = prestamoController.obtenerMultaPorID(idPrestamo);
                return String.valueOf(multa);

            } else if (request.startsWith("PAGAR_MULTAS:")) {
                int usuarioID = Integer.parseInt(request.substring(13));
                if (prestamoController.pagarMultas(usuarioID)) {
                    return "Multas pagadas exitosamente";
                } else {
                    return "Error al pagar multas";
                }

            } else if (request.startsWith("OBTENER_MULTA_TOTAL:")) {
                int usuarioID = Integer.parseInt(request.substring(21));
                double total = prestamoController.obtenerMultaTotal(usuarioID);
                return String.valueOf(total);


              
                
             
            //RESERVA
            }else if (request.startsWith("AGREGAR_RESERVA:")) {
                 Reserva reserva = parseReserva(request.substring(16));

                if (reservaController.agregar(reserva)) {
                    return "Reserva agregada correctamente";
                } else {
                    return "Error al agregar reserva";
                }
            }else if (request.startsWith("RESERVA_POR_ID_USUARIO:")) {
                int id = Integer.parseInt(request.substring(23));
                List<Reserva> reservas = reservaController.obtenerPorUsuarioID(id);
                return reservasToString(reservas);
            }
            
           
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }

        return "COMANDO_NO_RECONOCIDO";
    }

    private Usuario parseUsuario(String datos) {
        String[] partes = datos.split(",");
        Usuario usuario = new Usuario();

        if (partes.length >= 7) {
            usuario.setUsuarioID(Integer.parseInt(partes[0]));
            usuario.setNombre(partes[1]);
            usuario.setPrimerApellido(partes[2]);
            usuario.setSegundoApellido(partes[3]);
            usuario.setRol(partes[4]);
            usuario.setCorreo(partes[5]);
            usuario.setContrasena(partes[6]);
        } else if (partes.length == 2) {
            usuario.setCorreo(partes[0]);
            usuario.setContrasena(partes[1]);
        }

        return usuario;
    }

    private String usuariosToString(List<Usuario> usuarios) {
        StringBuilder result = new StringBuilder();
        for (Usuario usuario : usuarios) {
            result.append(usuario.getUsuarioID()).append(",")
                    .append(usuario.getNombre()).append(",")
                    .append(usuario.getPrimerApellido()).append(",")
                    .append(usuario.getSegundoApellido()).append(",")
                    .append(usuario.getRol()).append(",")
                    .append(usuario.getCorreo()).append(",")
                    .append(usuario.getContrasena()).append(";");
        }
        return result.toString();
    }

    private Categoria parseCategoria(String data) {
        String[] parts = data.split(",");
        try {
            return new Categoria(Integer.parseInt(parts[0]), parts[1]);
        } catch (Exception e) {
            System.out.println("Error al parsear categoría: " + e.getMessage());
            return null;
        }

    }

    private String categoriasToString(List<Categoria> categorias) {
        StringBuilder result = new StringBuilder();
        for (Categoria categoria : categorias) {
            result.append(categoria.getCategoriaID()).append(",").append(categoria.getNombre()).append(";");
        }
        return result.toString();
    }

    private Autor parseAutor(String data) {
        String[] parts = data.split(",");
        try {
            return new Autor(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2],
                    Date.valueOf(parts[3]),
                    Date.valueOf(parts[4])
            );
        } catch (Exception e) {
            System.out.println("Error al parsear autor: " + e.getMessage());
            return null;
        }
    }

    private String autoresToString(List<Autor> autores) {
        StringBuilder result = new StringBuilder();
        for (Autor autor : autores) {
            result.append(autor.getAutorID()).append(",")
                    .append(autor.getNombre()).append(",")
                    .append(autor.getPrimerApellido()).append(",")
                    .append(autor.getFechaNacimiento()).append(",")
                    .append(autor.getFechaFallecimiento()).append(";");
        }
        return result.toString();
    }

    private Libro parseLibro(String data) {
        String[] parts = data.split(",");
        try {
            return new Libro(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Boolean.parseBoolean(parts[4]),
                    Date.valueOf(parts[5])
            );
        } catch (Exception e) {
            System.out.println("Error al parsear libro: " + e.getMessage());
            return null;
        }
    }

    private String librosToString(List<Libro> libros) {
        StringBuilder result = new StringBuilder();
        for (Libro libro : libros) {
            result.append(libro.getLibroID()).append(",")
                    .append(libro.getTitulo()).append(",")
                    .append(libro.getAutorID()).append(",")
                    .append(libro.getCategoriaID()).append(",")
                    .append(libro.isDisponibilidad()).append(",")
                    .append(libro.getAnoPublicacion()).append(";");
        }
        return result.toString();
    }

    private Prestamo parsePrestamo(String data) {
        String[] parts = data.split(",");
        try {
            return new Prestamo(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Date.valueOf(parts[3]),
                    Date.valueOf(parts[4]),
                    parts[5],
                    Double.parseDouble(parts[6]),
                    Date.valueOf(parts[7])
            );
        } catch (Exception e) {
            System.out.println("Error al parsear prestamo: " + e.getMessage());
            return null;
        }
    }

    private String prestamosToString(List<Prestamo> prestamos) {
        StringBuilder result = new StringBuilder();
        for (Prestamo prestamo : prestamos) {
            result.append(prestamo.getPrestamoID()).append(",")
                    .append(prestamo.getUsuarioID()).append(",")
                    .append(prestamo.getLibroID()).append(",")
                    .append(prestamo.getFechaInicio()).append(",")
                    .append(prestamo.getFechaFinalizacion()).append(",")
                    .append(prestamo.getEstado()).append(",")
                    .append(prestamo.getMulta()).append(",")
                    .append(prestamo.getFechaDevolucion()).append(";");

        }
        return result.toString();
    }
    private Reserva parseReserva(String datos) {
    String[] partes = datos.split(",");
    Reserva reserva = new Reserva();

    if (partes.length >= 5) {
        reserva.setReservaID(Integer.parseInt(partes[0]));
        reserva.setUsuarioID(Integer.parseInt(partes[1]));
        reserva.setLibroID(Integer.parseInt(partes[2]));
        reserva.setFechaReserva(Date.valueOf(partes[3]));
        reserva.setFechaDisponible(Date.valueOf(partes[4]));
    }

    return reserva;
}

private String reservasToString(List<Reserva> reservas) {
    StringBuilder result = new StringBuilder();
    for (Reserva reserva : reservas) {
        result.append(reserva.getReservaID()).append(",")
              .append(reserva.getUsuarioID()).append(",")
              .append(reserva.getLibroID()).append(",")
              .append(reserva.getFechaReserva().toString()).append(",")
              .append(reserva.getFechaDisponible().toString()).append(";");
    }
    return result.toString();
}


}
