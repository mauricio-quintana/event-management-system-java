package com.eventosuy.logica.usuario;

import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTAsistente extends DTUsuario {
    private String apellido;
    private LocalDate fecha;
    private Set<String> registros;
    private String institucion;
    private String fechaAsString;
    
    public DTAsistente(String nick, String nombre, String email, String apellido, LocalDate fechaDeNacimiento, Set<String> reg, String institucion, String pass, byte[] foto,  Set<String> seguidos, Set<String> seguidores) {
        super(nick, nombre, email, pass, foto, seguidos, seguidores);
        this.apellido = apellido;
        this.fecha=fechaDeNacimiento;
        this.registros = reg;
        this.institucion = institucion;
        fechaAsString = fecha.toString();
    }

    public String getApellido() {
    	return this.apellido; 
    }
    
    public LocalDate getFechaDeNacimiento() {
    	return fecha; 
    }
    
    public Set<String> getRegistros(){
    	return this.registros;
    }
    
    public String getInstitucion() {
    	return this.institucion;
    }
    
    public String getFechaDeNAcimientoAsString() {
    	return fechaAsString;
    }
}
