package com.eventosuy.logica.usuario;

import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import com.eventosuy.logica.contenedores.Dupla;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTOrganizador extends DTUsuario {
    private String descripcion;
    private String link;
    private Set<Dupla> eventosEdicionesAceptadas; 
    private Set<Dupla> eventosEdicionesIngresadas; 
    private Set<Dupla> eventosEdicionesRechazadas; 
    private Set<Dupla> eventosEdicionesArchivadas; 

    public DTOrganizador(String nick, String nombre, String email, String pass, byte[] foto,
                         String descripcion, String link, Set<Dupla> aceptadas, Set<Dupla> ingresadas, Set<Dupla> rechazadas, Set<String> seguidos, Set<String> seguidores, Set<Dupla> archivadas) {
        super(nick, nombre, email, pass, foto, seguidos, seguidores);
        this.descripcion = descripcion;
        this.link = link;
        this.eventosEdicionesAceptadas = aceptadas;
        this.eventosEdicionesIngresadas = ingresadas;
        this.eventosEdicionesRechazadas = rechazadas;
        this.eventosEdicionesArchivadas = archivadas;
    }

    public String getDescripcion() {
        return this.descripcion; 
    }

    public String getLink() {
        return this.link; 
    }

    public Set<Dupla> getEventosEdicionesAceptadas() {
        return this.eventosEdicionesAceptadas;
    }
    
    public Set<Dupla> getEventosEdicionesIngresadas() {
        return this.eventosEdicionesIngresadas;
    }
    
    public Set<Dupla> getEventosEdicionesRechazadas() {
        return this.eventosEdicionesRechazadas;
    }
    
    public Set<Dupla> getEventosEdicionesArchivadas() {
        return this.eventosEdicionesArchivadas;
    }
}
