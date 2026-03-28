package com.eventosuy.logica.evento;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import com.eventosuy.logica.contenedores.Dupla;
import com.eventosuy.logica.usuario.Institucion;
import com.eventosuy.logica.usuario.Organizador;
import com.eventosuy.persistencia.EdicionDAO;
import com.eventosuy.persistencia.OrganizadorDAO;
import com.eventosuy.persistencia.RegistroDAO;

public class Edicion {
	
    private String nombre;
    private String sigla;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaAlta;
    private String ciudad;
    private String pais;
    private EnumEstadoEdicion estado;
    private byte[] foto;
    private String video;
    
    private Set<Registro> registros;
    private Map<String, TipoRegistro> tipoRegistros;
    private Organizador organizador;
    private Set<Patrocinio> patrocinios;
    private Evento evento;
    
    public Edicion(String nombre, String sigla, LocalDate
    		fechaInicio, LocalDate fechaFin, 
                   LocalDate fechaAlta, String ciudad, String pais, byte[] foto, String video) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaAlta = fechaAlta;
        this.ciudad = ciudad;
        this.pais = pais;
        this.foto = foto;
        this.video = video;
        this.registros = new HashSet<Registro>();
        this.tipoRegistros = new HashMap<String, TipoRegistro>();
        
        this.patrocinios= new HashSet<Patrocinio>();
        this.estado = EnumEstadoEdicion.Ingresada;
    }
    
    public void asociarOrganizador(Organizador organ) {
    	this.organizador=organ; //recomendaría que se le pase por parámetro.
    }

    public String getNombre() {
        return nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }
    
    public EnumEstadoEdicion getEstado() {
    	return estado;
    }
    
    public byte[] getFoto() {
    	return this.foto;
    }
    
    public String getVideo() {
    	return this.video;
    }
    
    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
        public void setEstado(EnumEstadoEdicion nuevoEstado) {
    	this.estado = nuevoEstado;
    }
        
    public Boolean nuevoTipoRegistro(String nombre, String descripcion, Float costo, Integer cupo) {
   
    	Boolean pertenece=tipoRegistros.containsKey(nombre);
    	if (!pertenece) {
    		TipoRegistro tipo= new TipoRegistro(nombre, descripcion, costo, cupo);
    		tipoRegistros.put(nombre, tipo);
    		tipo.setEdicion(this);
    		
        }
    	
    	return !pertenece;
        
    }
    
    
     public void asociarRegistro(Registro regis) {
    	
    	registros.add(regis);
    	
    }
     
    public Boolean validarPatrocinio(String nombreInstitucion) {
    	Boolean retorno=false;
    	Iterator<Patrocinio> iterator = patrocinios.iterator();
    	while (iterator.hasNext() && retorno==false) {
            Patrocinio patr = iterator.next();  // Obtener el siguiente elemento
            retorno=patr.relacionadoInstitucion(nombreInstitucion);
        }
    	
    	return !retorno;
    }
     
    
    public Set<String> listarTipoRegistro() {
        Set<String> retorno= new HashSet<String>();
    	Iterator<String> nombresTipoRegistro = tipoRegistros.keySet().iterator();
    	while (nombresTipoRegistro.hasNext()) {
    	    String nombreAgregar = nombresTipoRegistro.next();
    	    retorno.add(nombreAgregar);
    	    
    	}
    	
    	return retorno;
            
        }
    
    
    public Set<String> listarPatrocinios() {    	
    	Set<String> retorno= new HashSet<String>();
    	for (Patrocinio p : patrocinios) {
    		retorno.add(p.getNombreInstitucion());
    	}
    	return retorno; 
    }
    
    public DTPatrocinio mostrarPatrocinio(String institucion) {
    	Iterator<Patrocinio> iterator = patrocinios.iterator();
        while (iterator.hasNext()) {
            Patrocinio patr = iterator.next();
            if (patr.getNombreInstitucion().equals(institucion)) { 
                return patr.getDTPatrocinio();
            }
        }
        return null; 
    }
    
    public TipoRegistro obtenerTipoRegistro(String nombreTipoRegistro) {
    	return tipoRegistros.get(nombreTipoRegistro);
    }
    
    
    public DTTipoRegistro getDTTipoRegistroTR(String nombreTipoRegistro) {
    	
    	TipoRegistro tipo=tipoRegistros.get(nombreTipoRegistro);
    	DTTipoRegistro tipoRegistroData= new DTTipoRegistro(tipo.getNombre(), tipo.getCupo(), tipo.getCosto(), tipo.getDescripcion() );
    	return tipoRegistroData;
    	
    }
    
    
    
    public DTEdicion getDTEdicion() {
        Set<String> tiposRegistro= new HashSet<String>();
        for (String clave : tipoRegistros.keySet()) {
        	tiposRegistro.add(clave);
        }
        
        Set<DTRegistro> reg= new HashSet<DTRegistro>();
        for (Registro r: registros) {
        	reg.add(r.obtenerDetallesRegistroDeUsuario());
        }
    
    	return new DTEdicion(this.nombre, this.pais, this.ciudad, this.fechaInicio, this.fechaFin, this.fechaAlta, this.sigla, this.organizador.getNickname(), tiposRegistro, reg, listarPatrocinios(), this.foto, this.estado, this.video, evento.getNombre());	
    }
    
    public DTEdicion getDTEdicionWEB(String miNick) {
    	if (miNick==null) {
    		miNick = "";
    	}
        Set<String> tiposRegistro= new HashSet<String>();
        for (String clave : tipoRegistros.keySet()) {
        	tiposRegistro.add(clave);
        }
        
        Set<DTRegistro> reg= new HashSet<DTRegistro>();
        	if (!miNick.equals("")) {
	        	for (Registro r: registros) {	
	        		if (miNick.equals(organizador.getNickname()))
	        			reg.add(r.obtenerDetallesRegistroDeUsuario());
	        		else if (r.getAsistente().getNickname().equals(miNick)) {
	        			reg.add(r.obtenerDetallesRegistroDeUsuario());
	        			break;
	        		}
	        	}
        	}
        	
    	return new DTEdicion(this.nombre, this.pais, this.ciudad, this.fechaInicio, this.fechaFin, this.fechaAlta, this.sigla, this.organizador.getNickname(), tiposRegistro, reg, listarPatrocinios(), this.foto, this.estado, this.video, evento.getNombre());	
    }
    
    public Dupla obtenerEventoEdicion() {
    	return new Dupla(evento.getNombre(), this.nombre);
    }
    
    public void asociarEvento(Evento evento) {
    	this.evento = evento;
    }
    
    		
    public Patrocinio nuevoPatrocinioEvento(TipoRegistro tipoRegistro, EnumPatrocinio nivelPatrocinio, Float aporteEconomico, Integer cantidadGratuitos, // con aporteConomico nos referimos a monto
    				String codigoPatrocinio, LocalDate fechaActual, Institucion insti) {
      Patrocinio patrocinio= new Patrocinio(codigoPatrocinio, fechaActual, aporteEconomico, nivelPatrocinio,  cantidadGratuitos,  insti, tipoRegistro);
      this.patrocinios.add(patrocinio);
    	
    return patrocinio;	
    }
    
    public float validarRegistroGratuito(String inst, String codigo, TipoRegistro tipo) {
    	if (codigo != "" && inst != null) {
    		Iterator<Patrocinio> iter = patrocinios.iterator();
    		while (iter.hasNext()) {
    			Patrocinio pat = iter.next();
    			if (pat.getNombreInstitucion().equals(inst) && pat.validarGratuito(codigo, tipo)) {
    				return 0;
    			}
    		}
    	}
    	if (codigo.equals(""))
    		return tipo.getCosto();
    	return -1;
    }

	
	public void persistirEdicion(EntityManager man) {
		EntityTransaction trans = man.getTransaction();
		trans.begin();
		OrganizadorDAO organizadorPersistido = organizador.persistirOrganizador(man);
		organizador.removerEdicion(this.nombre);
		EdicionDAO edicionPersistida = new EdicionDAO(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, organizadorPersistido, this.evento.getNombre());
		Set<RegistroDAO> registrosPersistidos = new HashSet<RegistroDAO>();
		for (Registro reg : registros) {
			registrosPersistidos.add(reg.persistirRegistro(man, edicionPersistida));
		}
		
		for (RegistroDAO regis : registrosPersistidos) {
			edicionPersistida.addRegistro(regis);
			man.persist(regis);
		}
		man.persist(edicionPersistida);
		trans.commit();
		this.estado = EnumEstadoEdicion.Archivada;
	}

}



