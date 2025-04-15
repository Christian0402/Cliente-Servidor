/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor.Controllers;


import java.util.List;
import servidor.DAO.UsuarioDAO;
import shared.Usuario;

/**
 *
 * @author Usuario
 */
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public List<Usuario> iniciarSesion(String correo,String pwd) {
        System.out.println(correo);
        System.out.println(pwd);
        System.out.println("EN EL CONTROLLER DEL SERVER");
        return usuarioDAO.iniciarSesion(correo,pwd);
    }
    
    public boolean agregar(Usuario usuario){
        return usuarioDAO.agregar(usuario);
                
    }
    public boolean actualizar(Usuario usuario){
        return usuarioDAO.actualizar(usuario);
                
    }
    public boolean eliminar(int id){
        return usuarioDAO.eliminar(id);
                
    }
    
    public List<Usuario> obtener(){
        return usuarioDAO.obtener();
    }
    public String obtenerNombre(int id){
        return usuarioDAO.obtnerNombreCompletoPorID(id);
    }
}

