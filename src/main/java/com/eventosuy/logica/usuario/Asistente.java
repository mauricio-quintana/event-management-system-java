package com.eventosuy.logica.usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import com.eventosuy.logica.evento.DTRegistro;
import com.eventosuy.logica.evento.Edicion;
import com.eventosuy.logica.evento.Registro;
import com.eventosuy.logica.evento.TipoRegistro;
import com.eventosuy.persistencia.AsistenteDAO;

import java.util.Iterator;
import java.util.List;

public class Asistente extends Usuario {
	private String apellido;
	private LocalDate fechaDeNacimiento;
	private Institucion institucion;
	private Set<Registro> registros;

	
	public Asistente(String nick, String nombre, String email, String apellido, LocalDate fecha, String pass, byte[] foto) {
        super(nick, nombre, email, pass, foto);
        this.apellido = apellido;
        this.fechaDeNacimiento = fecha;
        this.registros = new HashSet<>();
    }
	
	public String getApellido() {
        return apellido;
    }
	
	public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }
	
	public Institucion getInstitucion() {
        return this.institucion;
    }
	
	public void setApelldio(String apellido) {
        this.apellido = apellido;
    }
	
	public void setFechaDeNacimiento(LocalDate fecha) {
        fechaDeNacimiento = fecha;
    }
	
	@Override
	public DTAsistente getDTUsuario() {  
		String inst = null;
		if (this.institucion != null)
			inst = institucion.getNombre();
		return new DTAsistente(getNickname(), getNombre(), getEmail(), this.apellido, this.getFechaDeNacimiento(), obtenerRegistrosUsuario(), inst, getContrasena(), getFoto(), this.getSeguidos(), this.getSeguidores());
	}
	
	public void asociarInstitucion(Institucion inst) {
		this.institucion = inst;
	}
	
	public Set<String> obtenerRegistrosUsuario() {
        Set<String> nombresRegistros = new HashSet<>();
        for (Registro r : registros) {
            nombresRegistros.add(r.obtenerNombreRegistro());
        }
        return nombresRegistros;
    }
	
	public DTRegistro obtenerDetallesRegistroDeUsuario(String nombreEdicion) {
		Iterator<Registro> iterator = registros.iterator();
		while (iterator.hasNext()) {
		    Registro reg = iterator.next();
		    if (reg.obtenerNombreRegistro().equals(nombreEdicion)) {
		        return reg.obtenerDetallesRegistroDeUsuario();
		    }
		}
		return null;
	}
	
	
	public Boolean noRegistradoEdicion(String nombreEdicion) {
		Boolean okey=true;
		Iterator<Registro> iterator = registros.iterator();
		while (iterator.hasNext() && okey) {
		    Registro reg = iterator.next();
		    okey=reg.edicionDistinta(nombreEdicion);
	    }
		
		return okey;
		
	} 
	
	public Registro registrarseEdicion(Edicion edi, Float costo, LocalDate fecha, TipoRegistro tipoRegistro, String nombreEvento, String codigo) {
		Registro registro= new Registro(fecha, costo, tipoRegistro, this, edi, nombreEvento, codigo);
		this.registros.add(registro);
		tipoRegistro.asociarRegistro(registro);
		
		return registro;
	}

	public void confirmarAsistencia(String nombreEdicion) {
		Iterator<Registro> iterator = registros.iterator();
		while (iterator.hasNext()) {
		    Registro reg = iterator.next();
		    if (reg.obtenerNombreRegistro().equals(nombreEdicion)) {
		        reg.setAsistencia(true);
		    }
		}
		
	}
	
	public AsistenteDAO persistirAsistente(EntityManager man) {
		//pregunto si ya persisti este asistente
		Query consulta = man.createQuery("SELECT a FROM Asistentes a WHERE a.nickname = ?1");
		consulta.setParameter(1, this.getNickname());
		List<AsistenteDAO> res = consulta.getResultList();
		AsistenteDAO persistido;
		
		//si no persistido lo hago ahora
		if (res.isEmpty()) {
			persistido = new AsistenteDAO(this.getNickname(), this.getNombre(), this.getEmail(), apellido, fechaDeNacimiento);
			//man.persist(persistido); lo persiste el registro
		} else persistido = res.getFirst();
	return persistido;
	}
	
	
}

