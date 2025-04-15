/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.Controllers;
import java.util.List;
import servidor.DAO.CategoriaDAO;
import shared.Categoria;



/**
 *
 * @author Usuario
 */
public class CategoriaController {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    
     public boolean agregar(Categoria categoria){
        return categoriaDAO.agregar(categoria);
                
    }
    public boolean actualizar(Categoria categoria){
        return categoriaDAO.actualizar(categoria);
                
    }
    public boolean eliminar(int id){
        return categoriaDAO.eliminar(id);
                
    }
    
    public List<Categoria> obtener(){
        return categoriaDAO.obtener();
    }
    public String obtenerPorID(int id){
        return categoriaDAO.buscarPorID(id);
    }
}
