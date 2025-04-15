/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class Conexion {
    private static final String URL="jdbc:mysql://localhost:3306/SistemaBiblioteca ";
    private static final String USER = "root";
    private static final  String PASSWORD = "chris900";
    
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexion Existoa a la base de datos");
        }catch(SQLException e ){
           System.err.println("Error al concetar a la base de datos "+e.getMessage());
           
        }
        return conn;
    }
           
}
