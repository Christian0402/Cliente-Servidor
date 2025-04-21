/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controllers;


import com.mycompany.sistemabiblioteca.cliente.ControladorVista.SesionUsuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import shared.Libro;

import shared.Reserva;

/**
 *
 * @author Usuario
 */
public class ReservaController {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public boolean agregar(Reserva reserva) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("AGREGAR_RESERVA:" + reservaToString(reserva)+ "|CORREO:" +SesionUsuario.getInstancia().getUsuario().getCorreo());
            String response = in.readLine();
            return response.equals("Reserva agregada correctamente");
        }
    }

    public List<Reserva> buscarPorFiltroGeneral(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("RESERVA_POR_ID_USUARIO:" + id);
            String response = in.readLine();
            return parseReservas(response);
        }
    }

    private String reservaToString(Reserva reserva) {
        return reserva.getReservaID() + ","
                + reserva.getUsuarioID() + ","
                + reserva.getLibroID() + ","
                + reserva.getFechaReserva().toString() + ","
                + reserva.getFechaDisponible().toString();
    }

    private List<Reserva> parseReservas(String data) {
        List<Reserva> reservas = new ArrayList<>();
        String[] reservaStrings = data.split(";");
        for (String reservaString : reservaStrings) {
            String[] parts = reservaString.split(",");
            if (parts.length == 5) {
                try {
                    int id = Integer.parseInt(parts[0]);
                    int usuarioID = Integer.parseInt(parts[1]);
                    int libroID = Integer.parseInt(parts[2]);
                    Date fechaReserva = Date.valueOf(parts[3]);
                    Date fechaDisponible = Date.valueOf(parts[4]);

                    reservas.add(new Reserva(id, usuarioID, libroID, fechaReserva, fechaDisponible));
                } catch (Exception e) {
                    System.out.println("Error al parsear reserva: " + e.getMessage());
                }
            }
        }

        return reservas;
    }

}
