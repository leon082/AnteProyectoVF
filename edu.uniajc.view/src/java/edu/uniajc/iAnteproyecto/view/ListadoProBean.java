/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.iAnteproyecto.view;

import edu.uniajc.Anteproyecto.util.LeerPropiedades;
import edu.uniajc.anteproyecto.interfaces.IEntregaProyectoEstudiante;
import edu.uniajc.anteproyecto.interfaces.IIntegrantes;
import edu.uniajc.anteproyecto.interfaces.IProyecto;
import edu.uniajc.anteproyecto.interfaces.IUsuario;
import java.util.List;
import javax.faces.bean.ManagedBean;

import edu.uniajc.anteproyecto.interfaces.model.*;
import edu.uniajc.anteproyecto.logic.services.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Leon
 */
@ManagedBean(name = "listadoProyectoBean")
@ApplicationScoped
public class ListadoProBean {

    public static final String KEY = "proyecto";
    private IProyecto servicios;
    private IUsuario usu;
    private IEntregaProyectoEstudiante metodologia;
    private String config = "ProyectoServices";
    private String config2 = "UsuarioServices";
    private List<Proyecto> listaProyectos;
    private List<Proyecto> listaProyectos2;
    private List<Opciones_menu> listaModulos;
    //metodologias
    private List<MetodologiaModel> listaMetodologias;
    private List<MetodologiaModel> listaMetodologias2;
    private List<MetodologiaModel> listaMetodologias3;
    private List<MetodologiaModel> notas;

    //private Proyecto proyecto;
    private Proyecto proyectoTable;
    private IIntegrantes servicioIntegrante;
    private String configIntegrante = "IntegrantesServices";
    private String v_select_estado;
    private String v_select_lineamiento;
    private ArrayList<SelectItem> itemsEstadoProyecto;
    private ArrayList<SelectItem> itemsLineamiento;

    //dfsdsfsdfs    
    private String calificacion1;
    private String calificacion2;
    private String calificacion3;
    private String observacion1;
    private String observacion2;
    private String observacion3;    
    private String urlFile1;
    private String urlFile2;
    private String urlFile3;

    //crear entrega
    private int idProyecto;
    private int corte;
    private String ruta;
    private int creadoPor;
    private EntregaProyectoEstudiante entrega;
    public int auxCodProyecto;
    public String urlFile;

    private LeerPropiedades leer = new LeerPropiedades();

    public ListadoProBean() throws NamingException {
        InitialContext ctx = new InitialContext();
        servicios = (IProyecto) ctx.lookup(leer.leerArchivo(config));
        servicioIntegrante = (IIntegrantes) ctx.lookup(leer.leerArchivo(configIntegrante));
        listaProyectos = servicios.getAllProyectos();
        listaProyectos2 = servicios.getAllProyectos2(LoginBean.getUsuarioLogin().getId());
        listaModulos = servicios.getModulos(LoginBean.getUsuarioLogin().getId());
        System.out.println("lista modulos:" + listaModulos.size());
        this.itemsEstadoProyecto = Consultar_Estado_combo();
        this.itemsLineamiento = Consultar_Lineamiento_combo();
        entrega = new EntregaProyectoEstudiante();
    }

    public String navega() throws NamingException {

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(KEY, proyectoTable);
        return "ProyectoDetalle.xhtml";
        //return "ProyectoGestion.xhtml";

    }

    public String navega2(Proyecto p) throws NamingException {
        InitialContext ctx = new InitialContext();
        /*metodologia = (IEntregaProyectoEstudiante) ctx.lookup(leer.leerArchivo(config2));
         listaMetodologias=metodologia.getMetodologia(p.getID());*/

        servicios = (IProyecto) ctx.lookup(leer.leerArchivo(config));
        servicioIntegrante = (IIntegrantes) ctx.lookup(leer.leerArchivo(configIntegrante));
        usu = (IUsuario) ctx.lookup(leer.leerArchivo(config2));
        listaProyectos = servicios.getAllProyectos();

        //EntregaProyectoEstudianteServices metodoloservices = new EntregaProyectoEstudianteServices();
        listaMetodologias = servicios.getMetodologia(p.getID(), 1);
        listaMetodologias2 = servicios.getMetodologia(p.getID(), 2);
        listaMetodologias3 = servicios.getMetodologia(p.getID(), 3);
        notas = servicios.getMetodologiaCalificacion(p.getID());
        auxCodProyecto = p.getID();

        for (MetodologiaModel obj : notas) {

            switch (obj.getCorte()) {
                case 1:
                    setCalificacion1(obj.getCalificacion());
                    setObservacion1(obj.getDescripcion());
                    setUrlFile1(obj.getRuta());
                    break;
                case 2:
                    setCalificacion2(obj.getCalificacion());
                    setObservacion2(obj.getDescripcion());
                    setUrlFile2(obj.getRuta());
                    break;
                case 3:
                    setCalificacion3(obj.getCalificacion());
                    setObservacion3(obj.getDescripcion());
                    setUrlFile3(obj.getRuta());
                    break;
            }
        }

        System.out.print("calif " + getCalificacion1());
        System.out.print("obse " + getObservacion1());

        String url = "/anteproyecto/faces/PNew2.xhtml";
        int idRol = usu.getRol(LoginBean.getUsuarioLogin().getId());
        System.out.print("cod_rol " + idRol);

        if (idRol == 1) {
            System.out.print("entro1");
            url = "/anteproyecto/faces/pNew.xhtml"; //url donde se redirige la pantalla           
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println("urll " + url);
        try {
            fc.getExternalContext().redirect(url);// redirecciona la página
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        // FacesContext context = FacesContext.getCurrentInstance();
        // context.getExternalContext().getSessionMap().put(KEY, proyectoTable);
        // RequestContext.getCurrentInstance().update("new1");
        // return "ProyectoDetalle.xhtml";

        if (idRol == 2) {
            return "pNew.xhtml";
        } else {
            return "PNew2.xhtml";
        }

    }

    public ArrayList<SelectItem> Consultar_Estado_combo() {
        ListaValorDetalleServices serviciosLine = new ListaValorDetalleServices();

        List<ListaValoresDetalle> lista = serviciosLine.getListaValorDetallebyID_Lista_Valor(21);
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        for (ListaValoresDetalle obj : (ArrayList<ListaValoresDetalle>) lista) {
            items.add(new SelectItem(obj.getValor(), obj.getDescripcion()));
        }
        return items;
    }

    public ArrayList<SelectItem> Consultar_Lineamiento_combo() {
        LineamientoServices serviciosLine = new LineamientoServices();

        List<Lineamiento> lista = serviciosLine.getLineamientos();
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        for (Lineamiento obj : (ArrayList<Lineamiento>) lista) {
            items.add(new SelectItem(obj.getID(), obj.getDescripcion()));
        }
        return items;
    }

    public void modificar(RowEditEvent event) {

        Object ob = event.getObject();
        Proyecto ln = (Proyecto) ob;

        ln.setModificadoPor("Leon");

        if (servicios.updateProyecto(ln)) {
            listaProyectos = servicios.getAllProyectos();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "Operacion realizado con exito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo realziar la operación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void eliminar(int IdProyecto) {

        boolean flag2 = servicioIntegrante.deleteIntegrantesByProyecto(IdProyecto);
        boolean flag = servicios.deleteProyecto(IdProyecto);
        listaProyectos = servicios.getAllProyectos();
        if (flag && flag2) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "El Proyecto Fue eliminado con sus integrantes.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo realziar la operación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void crearEntrega(int codigoProyecto) throws IOException {

        System.out.println("url file" + getUrlFile());
        System.out.println("Codigo de Proyecto" + codigoProyecto);
        entrega.setIdProyecto(codigoProyecto);
        entrega.setIdPeriodoEntrega(1);
        entrega.setRutaProyecto(getUrlFile());
        entrega.setCreadoPor("" + LoginBean.getUsuarioLogin().getId());
        entrega.setCalificacion(getCalificacion1());
        entrega.setObservacion(getObservacion1());
        System.out.print("calificacion:" + getCalificacion1());
        System.out.print("observ:" + getObservacion1());
        System.out.print("return:" + servicios.createEntrega(entrega));
        if (servicios.createEntrega(entrega) != 0) {
            System.out.print("exito");

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación realizada correctamente", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo crear el registro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void crearEntrega2(int codigoProyecto) {

        System.out.println("url file" + getUrlFile());
        System.out.println("Codigo de Proyecto" + codigoProyecto);
        entrega.setIdProyecto(codigoProyecto);
        entrega.setIdPeriodoEntrega(2);
        entrega.setRutaProyecto(getUrlFile());
        entrega.setCreadoPor("" + LoginBean.getUsuarioLogin().getId());
        entrega.setCalificacion("" + getCalificacion2());
        entrega.setObservacion(getObservacion2());
        System.out.print("return:" + servicios.createEntrega(entrega));
        if (servicios.createEntrega(entrega) != 0) {
            System.out.print("exito");

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación realizada correctamente", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo crear el registro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void crearEntrega3(int codigoProyecto) {

        System.out.println("url file" + getUrlFile());
        System.out.println("Codigo de Proyecto" + codigoProyecto);
        entrega.setIdProyecto(codigoProyecto);
        entrega.setIdPeriodoEntrega(3);
        entrega.setRutaProyecto(getUrlFile());
        entrega.setCreadoPor("" + LoginBean.getUsuarioLogin().getId());
        entrega.setCalificacion("" + getCalificacion3());
        entrega.setObservacion(getObservacion3());
        System.out.print("return:" + servicios.createEntrega(entrega));
        if (servicios.createEntrega(entrega) != 0) {
            System.out.print("exito");

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación realizada correctamente", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "informacion", "No se pudo crear el registro");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, Exception {

        System.out.println("si entra 4444:" + event.getFile().getFileName());
        String name = "_" + usu.getSecFile() + event.getFile().getFileName();
        String ruta = "C:\\DOCUMENTACION\\";

        String retorno = ruta + name;
        setUrlFile(retorno);
        System.out.println("si entra 222:" + retorno);
        File filePrueba = new File(ruta);
        //pregunto si el directorio existe sino lo creo
        if (!filePrueba.exists()) {
            filePrueba.mkdirs();
        }
        filePrueba = new File(retorno);
        //luego adiciono el archivo y lo mando a crear
        InputStream is = event.getFile().getInputstream();
        OutputStream out = new FileOutputStream(filePrueba);
        byte buf[] = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        is.close();
        out.close();

    }

    public StreamedContent prepDownload(String ruta) throws Exception {
        
        System.out.println("ruta = " + ruta);
        StreamedContent download = new DefaultStreamedContent();
        File file = new File(ruta);
        InputStream input = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        download = new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
        System.out.println("PREP = " + download.getName());
        return download;
    }

    public IProyecto getServicios() {
        return servicios;
    }

    public void setServicios(IProyecto servicios) {
        this.servicios = servicios;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getConfig2() {
        return config2;
    }

    public void setConfig2(String config2) {
        this.config2 = config2;
    }

    public List<Proyecto> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<Proyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public List<Proyecto> getListaProyectos2() {
        return listaProyectos2;
    }

    public void setListaProyectos2(List<Proyecto> listaProyectos2) {
        this.listaProyectos2 = listaProyectos2;
    }

    public Proyecto getProyectoTable() {
        return proyectoTable;
    }

    public void setProyectoTable(Proyecto proyectoTable) {
        this.proyectoTable = proyectoTable;
    }

    public IIntegrantes getServicioIntegrante() {
        return servicioIntegrante;
    }

    public void setServicioIntegrante(IIntegrantes servicioIntegrante) {
        this.servicioIntegrante = servicioIntegrante;
    }

    public String getConfigIntegrante() {
        return configIntegrante;
    }

    public void setConfigIntegrante(String configIntegrante) {
        this.configIntegrante = configIntegrante;
    }

    public LeerPropiedades getLeer() {
        return leer;
    }

    public void setLeer(LeerPropiedades leer) {
        this.leer = leer;
    }

    public String getV_select_estado() {
        return v_select_estado;
    }

    public void setV_select_estado(String v_select_estado) {
        this.v_select_estado = v_select_estado;
    }

    public String getV_select_lineamiento() {
        return v_select_lineamiento;
    }

    public void setV_select_lineamiento(String v_select_lineamiento) {
        this.v_select_lineamiento = v_select_lineamiento;
    }

    public ArrayList<SelectItem> getItemsEstadoProyecto() {
        return itemsEstadoProyecto;
    }

    public void setItemsEstadoProyecto(ArrayList<SelectItem> itemsEstadoProyecto) {
        this.itemsEstadoProyecto = itemsEstadoProyecto;
    }

    public ArrayList<SelectItem> getItemsLineamiento() {
        return itemsLineamiento;
    }

    public void setItemsLineamiento(ArrayList<SelectItem> itemsLineamiento) {
        this.itemsLineamiento = itemsLineamiento;
    }

    public List<MetodologiaModel> getListaMetodologias() {
        return listaMetodologias;
    }

    public void setListaMetodologias(List<MetodologiaModel> listaMetodologias) {
        this.listaMetodologias = listaMetodologias;
    }

    public List<MetodologiaModel> getListaMetodologias2() {
        return listaMetodologias2;
    }

    public void setListaMetodologias2(List<MetodologiaModel> listaMetodologias2) {
        this.listaMetodologias2 = listaMetodologias2;
    }

    public List<MetodologiaModel> getListaMetodologias3() {
        return listaMetodologias3;
    }

    public void setListaMetodologias3(List<MetodologiaModel> listaMetodologias3) {
        this.listaMetodologias3 = listaMetodologias3;
    }

    public String getCalificacion1() {
        return calificacion1;
    }

    public void setCalificacion1(String calificacion1) {
        this.calificacion1 = calificacion1;
    }

    public String getCalificacion2() {
        return calificacion2;
    }

    public void setCalificacion2(String calificacion2) {
        this.calificacion2 = calificacion2;
    }

    public String getCalificacion3() {
        return calificacion3;
    }

    public void setCalificacion3(String calificacion3) {
        this.calificacion3 = calificacion3;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getCorte() {
        return corte;
    }

    public void setCorte(int corte) {
        this.corte = corte;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<Opciones_menu> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Opciones_menu> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public int getAuxCodProyecto() {
        return auxCodProyecto;
    }

    public void setAuxCodProyecto(int auxCodProyecto) {
        this.auxCodProyecto = auxCodProyecto;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public String getUrlFile1() {
        return urlFile1;
    }

    public void setUrlFile1(String urlFile1) {
        this.urlFile1 = urlFile1;
    }

    public String getUrlFile2() {
        return urlFile2;
    }

    public void setUrlFile2(String urlFile2) {
        this.urlFile2 = urlFile2;
    }

    public String getUrlFile3() {
        return urlFile3;
    }

    public void setUrlFile3(String urlFile3) {
        this.urlFile3 = urlFile3;
    }
    
    

}
