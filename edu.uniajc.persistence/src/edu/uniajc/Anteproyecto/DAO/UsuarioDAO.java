/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.Anteproyecto.DAO;

import edu.uniajc.anteproyecto.interfaces.model.Opciones_menu;
import edu.uniajc.anteproyecto.interfaces.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luis.leon
 */
public class UsuarioDAO {

    private Connection DBConnection = null;

    public UsuarioDAO(Connection openConnection) {
        this.DBConnection = openConnection;
    }

    public Usuario getUsuariobyUsername(String valor) throws SQLException {

        try {
            Usuario usuario = null;

            String SQL = "select * from TB_USUARIO where USUARIO  ='" + valor + "' ";
            //System.out.println(SQL);
            PreparedStatement ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                usuario = new Usuario();
                rs.next();
                usuario.setId(rs.getInt("ID"));
                usuario.setId_t_Persona(rs.getInt("ID_T_PERSONA"));
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setContrasena(rs.getString("CONTRASENA"));
                usuario.setId_t_lv_estadousuario(rs.getInt("ID_T_LV_ESTADOUSUARIO"));
                usuario.setCreadoPor(rs.getString("CREADOPOR"));
                usuario.setModificadoPor(rs.getString("MODIFICADOPOR"));
                usuario.setCreadoEn(rs.getDate("CREADOEN"));
                usuario.setModificadoEn(rs.getDate("MODIFICADOEN"));

            }
            ps.close();
            return usuario;

        } catch (SQLException e) {
            System.out.println("Error en  UsuarioDAO " + e.getMessage());
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return null;
        }

    }

    public int getUsuariobyidPersona(int id) {
        try {
            Usuario usuario = new Usuario();

            String SQL = "select * from TB_USUARIO where ID_T_PERSONA  =" + id + " ";
            System.out.println(SQL);
            PreparedStatement ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                rs.next();
                usuario.setId(rs.getInt("ID"));
                usuario.setId_t_Persona(rs.getInt("ID_T_PERSONA"));
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setContrasena(rs.getString("CONTRASENA"));
                usuario.setId_t_lv_estadousuario(rs.getInt("ID_T_LV_ESTADOUSUARIO"));
                usuario.setCreadoPor(rs.getString("CREADOPOR"));
                usuario.setModificadoPor(rs.getString("MODIFICADOPOR"));
                usuario.setCreadoEn(rs.getDate("CREADOEN"));
                usuario.setModificadoEn(rs.getDate("MODIFICADOEN"));

            }
            ps.close();
            return usuario.getId();

        } catch (SQLException e) {
            System.out.println("Error en  UsuarioDAO " + e.getMessage());
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return 0;
        }
    }

    public Usuario getUsuarioById(int id) {
        try {
            Usuario usuario = new Usuario();

            String SQL = "select * from TB_USUARIO where ID  =" + id + " ";
            System.out.println(SQL);
            PreparedStatement ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                rs.next();
                usuario.setId(rs.getInt("ID"));
                usuario.setId_t_Persona(rs.getInt("ID_T_PERSONA"));
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setContrasena(rs.getString("CONTRASENA"));
                usuario.setId_t_lv_estadousuario(rs.getInt("ID_T_LV_ESTADOUSUARIO"));
                usuario.setCreadoPor(rs.getString("CREADOPOR"));
                usuario.setModificadoPor(rs.getString("MODIFICADOPOR"));
                usuario.setCreadoEn(rs.getDate("CREADOEN"));
                usuario.setModificadoEn(rs.getDate("MODIFICADOEN"));

            }
            ps.close();
            return usuario;

        } catch (SQLException e) {
            System.out.println("Error en  UsuarioDAO " + e.getMessage());
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return null;
        }
    }

    public ArrayList<Opciones_menu> getModulos(int idUsuario) {

        ArrayList<Opciones_menu> list = new ArrayList<>(0);
        try {

            PreparedStatement ps = null;

            final String SQL = "SELECT MODULO.DESCRIPCION AS DESCRIPCION , MODULO.RUTA AS RUTA, MODULO.ID AS CODIGO, MODUSU.ESTADO AS ESTADO FROM TB_MODULO MODULO INNER JOIN TB_MODULO_ROL MODUSU ON MODULO.ID=MODUSU.COD_MODULO INNER JOIN TB_ROL ROL ON ROL.ID=MODUSU.COD_ROL INNER JOIN TB_USUARIOROL USUROL ON USUROL.ID_T_ROL=ROL.ID WHERE MODUSU.ESTADO='A' AND  USUROL.ID_T_USUARIO =" + idUsuario + " ";
            ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Opciones_menu menu = new Opciones_menu();
                //menu.setCodModulo(rs.getInt("CODIGO"));
                //menu.setEstado(rs.getString("ESTADO"));
                menu.setDescripcion(rs.getString("DESCRIPCION"));
                menu.setRuta(rs.getString("RUTA"));

                list.add(menu);
            }
            ps.close();

            return list;
        } catch (SQLException e) {
            Logger.getLogger(ProyectoDao.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return null;
        }
    }
    
    public int getRol(int idUsuario) {

        ArrayList<Opciones_menu> list = new ArrayList<>(0);
        try {

            PreparedStatement ps = null;

            final String SQL = "SELECT  distinct ID_T_ROL FROM TB_USUARIOROL WHERE  ID_T_USUARIO =" + idUsuario + " ";
            ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            int codigo=0;
            while (rs.next()) {
               codigo = rs.getInt("ID_T_ROL");
            }
            ps.close();

            return codigo;
        } catch (SQLException e) {
            Logger.getLogger(ProyectoDao.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return 0;
        }
    }
    
    public int getSecFile(){
        
        try {
            PreparedStatement ps = null;

            final String SQL = "SELECT SEQFILE.nextval AS SEC from dual";
            ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            int codigo=0;
            while (rs.next()) {
               codigo = rs.getInt("SEC");
            }
            ps.close();

            return codigo;
        } catch (SQLException e) {
            Logger.getLogger(ProyectoDao.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return 0;
        }
    
    }

}
