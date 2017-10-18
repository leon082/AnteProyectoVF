/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.anteproyecto.logic.services;

import edu.uniajc.Anteproyecto.DAO.*;
import edu.uniajc.anteproyecto.interfaces.*;
import edu.uniajc.anteproyecto.interfaces.model.*;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *@author Fabian Castro - IRIS
 *15/05/2017
 * Nombre Clase:ProyectoServices
 * Descripcion: logica de la clase proyecto
 */

@Stateless
public class ProyectoServices implements IProyecto
{

    @Override
    public int createProyecto(Proyecto proyecto) {
        try {

            // validacion de Data
            if (proyecto != null)
            {
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
                ProyectoDao dao = new ProyectoDao(cn.conexion());
                	

                
                int flag = dao.createProyecto(proyecto);
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
    public boolean deleteProyecto(int ID) {
        try {

           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
                //Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                 ConexionBD cn = new ConexionBD();
                ProyectoDao dao = new ProyectoDao(cn.conexion());
              dao.deleteProyecto(ID);
                         cn.conexion().close();
   
               return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateProyecto(Proyecto proyecto) {
        try {

                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD(); 
               ProyectoDao dao = new ProyectoDao(cn.conexion());
               dao.updateProyecto(proyecto);
                cn.conexion().close();

                
               return true;
                
                
                
           
                
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Proyecto> getAllProyectos() {
        try {

           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
               ProyectoDao dao = new ProyectoDao(cn.conexion());
                	

                
                ArrayList<Proyecto> list = dao.getAllProyectos();
                 cn.conexion().close();
                
                return list;
           
                
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
     @Override
    public ArrayList<Proyecto> getAllProyectos2(int codigoUuario) {
        try {

           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
               ProyectoDao dao = new ProyectoDao(cn.conexion());
                	

                
                ArrayList<Proyecto> list = dao.getAllProyectos2(codigoUuario);
                 cn.conexion().close();
                
                return list;
           
                
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public List<MetodologiaModel> getMetodologia(int id,int corte) {
        List<MetodologiaModel> metodologias = new ArrayList<>();
       try {

            // validacion de Data
           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
                EntregaProyectoEstudianteDao dao = new EntregaProyectoEstudianteDao(cn.conexion());
                
                metodologias = dao.getMetodologia(id,corte);
                 cn.conexion().close();
                
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
       return metodologias;
    }

    @Override
    public List<MetodologiaModel> getMetodologiaCalificacion(int id) {
        
         List<MetodologiaModel> metodologias = new ArrayList<>();
       try {

            // validacion de Data
           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
                EntregaProyectoEstudianteDao dao = new EntregaProyectoEstudianteDao(cn.conexion());
                
                metodologias = dao.getMetodologiaCalificacion(id);
                 cn.conexion().close();
                
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
       return metodologias;
        
    }

    @Override
    public int createEntrega(EntregaProyectoEstudiante entregaEstudiante) {
        
         try {            
                ConexionBD cn = new ConexionBD();
                EntregaProyectoEstudianteDao dao = new EntregaProyectoEstudianteDao(cn.conexion());              	

                
                int flag = dao.createEntrega(entregaEstudiante);
                 cn.conexion().close();
               
                        
                return flag;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
    }

   
    @Override
    public List<Opciones_menu> getModulos(int idUsuario) {
        
         List<Opciones_menu> modulos = new ArrayList<>();
       try {

            // validacion de Data
           
                // se adquiere la conexion a base de datos desde el servidor de aplicaciones
               // Connection dbConnection = ((DataSource) new InitialContext().lookup("jdbc/sample")).getConnection();
                ConexionBD cn = new ConexionBD();
                 UsuarioDAO dao = new UsuarioDAO(cn.conexion());
                
                modulos = dao.getModulos(idUsuario);
                 cn.conexion().close();
                
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
       return modulos;
        
    }    
    
}
