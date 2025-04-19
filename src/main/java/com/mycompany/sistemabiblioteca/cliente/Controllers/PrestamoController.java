/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemabiblioteca.cliente.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import shared.Prestamo;

/**
 *
 * @author Usuario
 */
public class PrestamoController {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public boolean agregar(Prestamo prestamo) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("AGREGAR_PRESTAMO:" + prestamoToString(prestamo));
            String response = in.readLine();
            return response.equals("Prestamo agregado correctamente");
        }
    }

    public List<Prestamo> obtener() throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("GET_PRESTAMOS:");
            String response = in.readLine();
            System.out.println("LO QUE MANDA EL SERVIDOR: " + response);
            return parsePrestamos(response);
        }
    }

    public List<Prestamo> obtenerPorUsuarioID(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("OBTENER_PRESTAMOS_POR_USUARIO_ID:" + id);
            String response = in.readLine();
            return parsePrestamos(response);
        }
    }

    public int obtenerIDLibroPorIDPrestamo(int id) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("OBTENER_ID_LIBRO_POR_PRESTAMO_ID:" + id);
            String response = in.readLine();
            int idLibro = Integer.parseInt(response);
            return idLibro;
        }
    }

    public boolean finalizar(int id, Date fechaDevolucion) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("FINALIZAR_PRESTAMO:" + id + "," + fechaDevolucion);
            String response = in.readLine();
            return response.equals("Finalizacion exitosa");
        }
    }

    public Date obtenerFechaFinalizacionPorID(int idPrestamo) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("OBTENER_FECHA_FINALIZACION:" + idPrestamo);
            String response = in.readLine();
            if (!response.equals("Fecha no encontrada")) {
                return Date.valueOf(response);
            }
        }
        return null;
    }

    public boolean tieneMultasActivas(int usuarioID) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("TIENE_MULTAS_ACTIVAS:" + usuarioID);
            String response = in.readLine();
            return response.equals("Tiene multas activas");
        }
    }

    public double obtenerMultaPorID(int idPrestamo) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("OBTENER_MULTA_POR_ID:" + idPrestamo);
            String response = in.readLine();
            return Double.parseDouble(response);
        }
    }

    public boolean pagarMultas(int usuarioID) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("PAGAR_MULTAS:" + usuarioID);
            String response = in.readLine();
            return response.equals("Multas pagadas exitosamente");
        }
    }

    public double obtenerMultaTotal(int usuarioID) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("OBTENER_MULTA_TOTAL:" + usuarioID);
            String response = in.readLine();
            System.out.println(response);
            return Double.parseDouble(response);
        }
    }

    private List<Prestamo> parsePrestamos(String data) {
        List<Prestamo> prestamos = new ArrayList<>();

        String[] prestamosStrings = data.split(";");
        for (String prestamoString : prestamosStrings) {
            String[] parts = prestamoString.split(",");
            
            if (parts.length == 8) {
                try {
                    Date fechaDevolucionSolucion = null;
                    if (!parts[7].equalsIgnoreCase("null")){
                        fechaDevolucionSolucion = Date.valueOf(parts[7]);
                    }
                           
                    
                    
                    int id = Integer.parseInt(parts[0]);
                    int usuarioID = Integer.parseInt(parts[1]);
                    int libroID = Integer.parseInt(parts[2]);
                    Date fechaInicio = Date.valueOf(parts[3]);
                    Date fechaFinalizacion = Date.valueOf(parts[4]);
                    String estado = parts[5];
                    Double multa = Double.parseDouble(parts[6]);
                    Date fechaDevolucion = fechaDevolucionSolucion;

                    prestamos.add(new Prestamo(id, usuarioID, libroID, fechaInicio, fechaFinalizacion, estado, multa, fechaDevolucion));

                } catch (Exception e) {
                    System.out.println("Error al parsear libro: " + e.getMessage());
                }
            }
        }
        return prestamos;
    }

    private String prestamoToString(Prestamo prestamo) {
        return prestamo.getPrestamoID() + ","
                + prestamo.getUsuarioID() + ","
                + prestamo.getLibroID() + ","
                + prestamo.getFechaInicio() + ","
                + prestamo.getFechaFinalizacion() + ","
                + prestamo.getEstado() + ","
                + prestamo.getMulta() + ","
                + prestamo.getFechaDevolucion();
    }

}
