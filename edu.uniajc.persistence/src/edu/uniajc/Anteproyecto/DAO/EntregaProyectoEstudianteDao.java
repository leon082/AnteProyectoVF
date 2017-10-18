/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.Anteproyecto.DAO;

import edu.uniajc.anteproyecto.interfaces.model.EntregaProyectoEstudiante;
import edu.uniajc.anteproyecto.interfaces.model.Idea;
import edu.uniajc.anteproyecto.interfaces.model.MetodologiaModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class EntregaProyectoEstudianteDao {

    private Connection DBConnection = null;

    public EntregaProyectoEstudianteDao(Connection openConnection) {
        this.DBConnection = openConnection;
    }

    public int createEntrega(EntregaProyectoEstudiante entrega) {
        try {
            
            CallableStatement ps = null;              
            String SQL = "CALL SP_ENTREGA(?,?,?,?,?,?)";            
            ps = this.DBConnection.prepareCall(SQL);  
            ps.setInt(1, entrega.getIdProyecto());
            ps.setInt(2, entrega.getIdPeriodoEntrega());
            ps.setString(3, entrega.getRutaProyecto());
            ps.setString(4, entrega.getCreadoPor());
            ps.setString(5, entrega.getCalificacion());
            ps.setString(6, entrega.getObservacion());
            ps.execute();
            //Falta capturar el Id del ultimo registro

            ps.close();

            //Le asigno el id al objeto proyecto            
            return 2;
        } catch (SQLException e) {
            System.out.println("Error en Proyecto DAO" + e.getMessage());
            Logger.getLogger(ProyectoDao.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return 0;
        }

    }

    public ArrayList<MetodologiaModel> getMetodologia(int codProyecto, int corte) {
        ArrayList<MetodologiaModel> list = new ArrayList<MetodologiaModel>();
        try {

            PreparedStatement ps = null;

            String SQL = "SELECT  METDET.DESCRIPCION AS DESCRIPCION ,nvl(METDET.PORCENTAJE,0) AS PORCENTAJE FROM TB_METODOLOGIA METO INNER JOIN TB_METODOLOGIADETALLE METDET ON METO.ID=METDET.ID_T_METODOLOGIA INNER JOIN TB_PROYECTO PROYECTO ON PROYECTO.ID_T_METODOLOGIA=METO.ID WHERE METDET.CORTE=" + corte + " AND PROYECTO.ID=" + codProyecto + "";
            ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                MetodologiaModel metodologia = new MetodologiaModel();
                metodologia.setDescripcion(rs.getString("DESCRIPCION"));
                metodologia.setPorcentaje(rs.getInt("PORCENTAJE"));

                list.add(metodologia);
            }
            ps.close();

            return list;
        } catch (SQLException e) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return null;
        }

    }

    public ArrayList<MetodologiaModel> getMetodologiaCalificacion(int codProyecto) {
        ArrayList<MetodologiaModel> list = new ArrayList<MetodologiaModel>();
        try {

            PreparedStatement ps = null;

            String SQL = "SELECT  CALIFICACION,CORTE,OBSERVACION,RUTAPROYECTO FROM TB_ENTREGA WHERE ID_T_PROYECTO=" + codProyecto + "";
            ps = this.DBConnection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                MetodologiaModel metodologia = new MetodologiaModel();
                metodologia.setCalificacion(rs.getString("CALIFICACION"));
                metodologia.setCorte(rs.getInt("CORTE"));
                metodologia.setDescripcion(rs.getString("OBSERVACION"));
                metodologia.setRuta(rs.getString("RUTAPROYECTO"));

                list.add(metodologia);
            }
            ps.close();

            return list;
        } catch (SQLException e) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, e.getMessage());
            return null;
        }

    }

}
