package com.eventosuy.logica.usuario;
import java.util.Set;

import com.eventosuy.logica.evento.Patrocinio;

import java.util.HashSet;

public class Institucion {

    private String nombre;
    private String descripcion;
    private String sitioWeb;
    private Set<Patrocinio> patrocinios;
    private byte[] foto;

    public Institucion(String nomb, String desc, String web, byte[] foto) {
        this.nombre = nomb;
        this.descripcion = desc;
        this.sitioWeb = web;
        this.foto = foto;
        this.patrocinios= new HashSet<Patrocinio>();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public byte[] getFoto() {
    	return foto;
    }
    
    public void setNombre(String nom) {
        nombre = nom;
    }

    public void setdescripcion(String desc) {
        descripcion = desc;
    }

    public void setSitioWeb(String web) {
        sitioWeb = web;
    }
    
    public void agregarPatrocinio(Patrocinio pat) {
    	
    	patrocinios.add(pat);
    	
    }

}
