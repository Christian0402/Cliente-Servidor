/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;

import servidor.Controllers.LibroController;

/**
 *
 * @author Usuario
 */
public class EmailService {
     public static void enviarCorreoReserva(String destinatario, Reserva reserva) {
         LibroController libroController= new LibroController();
        String asunto = "Reserva de libro confirmada";
        String mensaje = "Su libro "+libroController.obtenerNombrePorID(reserva.getLibroID())+" estará disponible el día: " + reserva.getFechaDisponible();

        
        CorreoUtil.enviar(destinatario, asunto, mensaje);
    }

   
}

