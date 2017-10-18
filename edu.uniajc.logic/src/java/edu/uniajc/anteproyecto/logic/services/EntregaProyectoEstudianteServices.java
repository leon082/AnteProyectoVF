/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.anteproyecto.logic.services;

import edu.uniajc.Anteproyecto.DAO.EntregaProyectoEstudianteDao;
import edu.uniajc.anteproyecto.interfaces.IEntregaProyectoEstudiante;
import edu.uniajc.anteproyecto.interfaces.model.EntregaProyectoEstudiante;
import edu.uniajc.anteproyecto.interfaces.model.MetodologiaModel;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Usuario
 */
@Stateless
public class EntregaProyectoEstudianteServices implements IEntregaProyectoEstudiante{
    
     @Override
    public int createEntrega(EntregaProyectoEstudiante entrega) {
        try {

            // validacion de Data
            if (entrega != null)
            {
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
                EntregaProyectoEstudianteDao dao = new EntregaProyectoEstudianteDao(cn.conexion());
                	

                
                int flag = dao.createEntrega(entrega);
                 cn.conexion().close();
               
                        
                return flag;
            } else {
                System.out.println("Faltan Datos en pantalla");
                return 0;
                
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public List<MetodologiaModel> getMetodologia(int id,int idCorte) {
        List<MetodologiaModel> metodologias = new ArrayList<>();
       try {

            // validacion de Data
           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
                EntregaProyectoEstudianteDao dao = new EntregaProyectoEstudianteDao(cn.conexion());
                
                metodologias = dao.getMetodologia(id,idCorte);
                 cn.conexion().close();
                
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
       return metodologias;
    }
    
}
