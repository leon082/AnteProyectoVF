/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.iAnteproyecto.view;

import edu.uniajc.Anteproyecto.util.LeerPropiedades;
import edu.uniajc.anteproyecto.interfaces.IUsuario;
import edu.uniajc.anteproyecto.interfaces.model.Usuario;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Usuario
 */
@ManagedBean
@ApplicationScoped

public class LoginBean {

    private String usuario;
    private String clave;

    private IUsuario iUsuario;
    private LeerPropiedades leer = new LeerPropiedades();
    private String configUsuario = "UsuarioServices";
    public static Usuario usuarioLogin = new Usuario();
    public static Usuario usuarioModulo = new Usuario();

    private Boolean validar() throws NamingException {
        InitialContext ctx = new InitialContext();

        Boolean valido = Boolean.TRUE;
        iUsuario = (IUsuario) ctx.lookup(leer.leerArchivo(configUsuario));
        Usuario usuarioTemp = iUsuario.getUsuariobyUsername(getUsuario());
        if (usuarioTemp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario con los datos ingresados no existe", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            valido = Boolean.FALSE;
        } else {
            if (!usuarioTemp.getContrasena().equals(getClave())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "La clave es incorrecta", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                valido = Boolean.FALSE;
            }
        }
        if (valido) {
            setUsuarioLogin(usuarioTemp);

        }
        return valido;
    }

    public String validUsuario() throws NamingException {
        if (!validar()) {
            return "";
        }
        String url = "/anteproyecto/faces/menu.xhtml"; //url donde se redirige la pantalla
        FacesContext fc = FacesContext.getCurrentInstance();
        System.out.println(url);
        try {
            fc.getExternalContext().redirect(url);// redirecciona la página
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public IUsuario getiUsuario() {
        return iUsuario;
    }

    public void setiUsuario(IUsuario iUsuario) {
        this.iUsuario = iUsuario;
    }

    public static Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public static void setUsuarioLogin(Usuario usuarioLogin) {
        LoginBean.usuarioLogin = usuarioLogin;
    }

    public static Usuario getUsuarioModulo() {
        return usuarioModulo;
    }

    public static void setUsuarioModulo(Usuario usuarioModulo) {
        LoginBean.usuarioModulo = usuarioModulo;
    }

}
