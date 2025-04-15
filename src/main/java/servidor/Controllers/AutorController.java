/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.Controllers;

import java.util.List;
import servidor.DAO.AutorDAO;
import shared.Autor;


/**
 *
 * @author Usuario
 */
public class AutorController {
     private final AutorDAO autorDAO = new AutorDAO();
    
     public boolean agregar(Autor autor){
        return autorDAO.agregar(autor);
                
    }
    public boolean actualizar(Autor categoria){
        return autorDAO.actualizar(categoria);
                
    }
    public boolean eliminar(int id){
        return autorDAO.eliminar(id);
                
    }
    
    public List<Autor> obtener(){
        return autorDAO.obtener();
    }
    public String obtenerPorID(int id){
        return autorDAO.buscarPorID(id);
    }

    
}
