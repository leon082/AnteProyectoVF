/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.iAnteproyecto.view;

import edu.uniajc.Anteproyecto.util.LeerPropiedades;
import edu.uniajc.anteproyecto.interfaces.IEntregaProyectoEstudiante;
import edu.uniajc.anteproyecto.interfaces.model.EntregaProyectoEstudiante;
import java.sql.Date;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Usuario
 */
@ManagedBean
@ApplicationScoped
public class EntregaProyectoEstudianteBean{ 
    
    private int id;
    private int idProyecto;
    private int idPeriodoEntrega;
    private Date fechaEntrega;
    private String rutaProyecto;
    private String creadoPor;
    private Date creadoEn;
    private String calificacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdPeriodoEntrega() {
        return idPeriodoEntrega;
    }

    public void setIdPeriodoEntrega(int idPeriodoEntrega) {
        this.idPeriodoEntrega = idPeriodoEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getRutaProyecto() {
        return rutaProyecto;
    }

    public void setRutaProyecto(String rutaProyecto) {
        this.rutaProyecto = rutaProyecto;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Date creadoEn) {
        this.creadoEn = creadoEn;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
    
    

    private IEntregaProyectoEstudiante servicios;
    private EntregaProyectoEstudiante entrega;
    private String configProyecto = "EntregaProyectoEstudianteServices";
    
    private LeerPropiedades leer = new LeerPropiedades();
    
    public EntregaProyectoEstudianteBean() throws NamingException {
        InitialContext ctx = new InitialContext();
        //proyecto
        
        servicios = (IEntregaProyectoEstudiante) ctx.lookup(leer.leerArchivo(configProyecto));
        entrega = new EntregaProyectoEstudiante();
       
    }
    
    
    
    
    public void crearEntrega() {
        
            entrega.setCreadoPor("Leon");
            
            /*proyecto.setId_T_Metodologia(Integer.parseInt(this.v_select_lineamiento));
            proyecto.setId_T_LV_estadoProyecto(Integer.parseInt(this.v_select_estado));
            idProyectoCreado = 0;
            idProyectoCreado = servicios.createProyecto(proyecto);
            if (idProyectoCreado != 0) {
                boolean flag = false;
                //Metodo para crear integrantes  
                for (int i = 0; i < listaIntegrantes.size(); i++) {
                    listaIntegrantes.get(i).setID_T_Proyecto(idProyectoCreado);
                    flag = servicioIntegrante.createintegrantes(listaIntegrantes.get(i));
                }

                if (flag) {
                    limpiarForma();
                    limpiarCombox();
                    limpiarLista();

                } else {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo vincular los participantes");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                }

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo crear el proyecto");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "Debe registrar los integrantes");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }*/
    }
    
}
