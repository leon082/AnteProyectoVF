/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniajc.iAnteproyecto.view;

/**
 *
 * @author jarteaga
 */
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "fileUploadController")
@ApplicationScoped
public class FileUploadController {
    
    UploadedFile file;
    UploadedFile file2;
    UploadedFile file3;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile2() {
        return file2;
    }

    public void setFile2(UploadedFile file2) {
        this.file2 = file2;
    }

    public UploadedFile getFile3() {
        return file3;
    }

    public void setFile3(UploadedFile file3) {
        this.file3 = file3;
    }
    
    
    

    private String destination = "D:\\pruebaFile\\";

    public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Exito! ", event.getFile().getFileName() + " Se ha subido al servidor.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {

            System.out.print("file " + destination + fileName);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String handleFileUpload(FileUploadEvent event) throws IOException, Exception {

        System.out.println("si entra 4444:" + event.getFile().getFileName());        
        
        String name = "_" + 4 + event.getFile().getFileName();
        String ruta = "C:\\DOCUMENTACION\\";

        String retorno = ruta + name;
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
        return retorno;
    }
    public String handleFileUpload3() throws IOException, Exception {

        System.out.println("si entra 4444:" +file.getFileName());
        String name = "_" + 6 + file.getFileName();
        String ruta = "C:\\DOCUMENTACION\\";

        String retorno = ruta + name;
        System.out.println("si entra 222:" + retorno);
        File filePrueba = new File(ruta);
        //pregunto si el directorio existe sino lo creo
        if (!filePrueba.exists()) {
            filePrueba.mkdirs();
        }
        filePrueba = new File(retorno);
        //luego adiciono el archivo y lo mando a crear
        InputStream is = file.getInputstream();
        OutputStream out = new FileOutputStream(filePrueba);
        byte buf[] = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        is.close();
        out.close();
        
        return retorno;
    }

    public byte[] read(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null) {
                    ous.close();
                }
            } catch (IOException e) {
            }

            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }
    
   
}
