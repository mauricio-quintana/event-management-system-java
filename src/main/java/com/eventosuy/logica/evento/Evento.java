package com.eventosuy.logica.evento;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.time.LocalDate;
import java.util.Set;

import com.eventosuy.logica.contenedores.DuplaFoto;
import com.eventosuy.persistencia.EdicionDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

public class Evento {

    private String nombre;
    private String sigla;
    private String descripcion;
    private LocalDate fechaAlta;
    private Map<String, Categoria> categorias;
    private Map<String, Edicion> ediciones;
    private byte[] foto;
    private Boolean finalizado;

    public Evento(String nomb, String sig, String desc, LocalDate fecha, byte[] foto) {
        this.nombre = nomb;
        this.sigla = sig;
        this.descripcion = desc;
        this.fechaAlta = fecha;
        this.categorias = new HashMap<String, Categoria>();
        //this.categorias.put(cat.getNombre(), cat);
        this.ediciones = new HashMap<String, Edicion>();
        this.foto = foto;
        this.finalizado = false;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getSigla() {
        return sigla;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    
    public byte[] getFoto() {
    	return this.foto;
    }
    
    public Boolean getFinalizado() {
    	return this.finalizado;
    }

    public void setNombre(String nomb) {
        this.nombre = nomb;
    } 

    public void setSigla(String str) {
        this.sigla = str;
    }

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }
    
    public void setDTFecha(LocalDate fecha) {
        this.fechaAlta = fecha;
    }
    
    public Set<String> listarPatrocinios(String nombreEdicion){
    	Edicion edi = ediciones.get(nombreEdicion);
    	Set<String> res = edi.listarPatrocinios();
    	return res;
    }
    
    public DTPatrocinio mostrarPatrocinio(String nombreEdicion, String Institucion) {
    	Edicion edi = ediciones.get(nombreEdicion);
    	return edi.mostrarPatrocinio(Institucion);
    }
    
    public Boolean asociado(String nombreCat) {
    	return categorias.containsKey(nombreCat);
    }
    
    public void asociarCategoria(Categoria cat) {
    	categorias.put(cat.getNombre(), cat);
    }
    
    public DTEvento getDTEvento() {
    	
    	Set<String> cats = new HashSet<String>();
    	Iterator<String> nombresCat = categorias.keySet().iterator();
    	while (nombresCat.hasNext()) {
    		String nombreAgregar = nombresCat.next();
    		cats.add(nombreAgregar);
    	}
    	Set<DuplaFoto> edis = new HashSet<DuplaFoto>();
    	for (Edicion e: ediciones.values()) {
    		edis.add(new DuplaFoto(e.getFoto(), e.getNombre()));
    	}
    	//cambio para que pase Dupla en lugar de string nombre edicion a DTEvento
    	/*Set<String> edis = new HashSet<String>();
    	Iterator<String> nombresEdis = ediciones.keySet().iterator();
    	while (nombresEdis.hasNext()) {
    		String nombreAgregar = nombresEdis.next();
    		edis.add(nombreAgregar);
    	}*/
    	
    	DTEvento dte = new DTEvento(this.nombre, this.sigla, this.descripcion, this.fechaAlta, cats, edis, foto, this.finalizado);
    	return dte;
    }
    
    public DTEvento getDTEventoWEB() {
    	Set<String> cats = new HashSet<String>();
    	Iterator<String> nombresCat = categorias.keySet().iterator();
    	while (nombresCat.hasNext()) {
    		String nombreAgregar = nombresCat.next();
    		cats.add(nombreAgregar);
    	}
    	Set<DuplaFoto> edis = new HashSet<DuplaFoto>();
    	for (Edicion e: ediciones.values()) {
    		if (e.getEstado() == EnumEstadoEdicion.Confirmada )
    			edis.add(new DuplaFoto(e.getFoto(), e.getNombre()));
    	}
    	
    	DTEvento dte = new DTEvento(this.nombre, this.sigla, this.descripcion, this.fechaAlta, cats, edis, foto, this.finalizado);
    	return dte;
    }
    
    public Boolean nombreEdicionValido(String nombre) {
    	Boolean res = true;
    	Iterator<String> iterator = ediciones.keySet().iterator();
    	while (res && iterator.hasNext()) {
    		String nombreEdi = iterator.next();
    		if (nombre.equals(nombreEdi)) {
    			res = false;
    		}
    	}
    	return res;
    }
    
    public Edicion nuevaEdicion(String nombre, String sigla, String ciudad, String pais, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, byte[] foto, String video) {
    	Edicion edi = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, foto, video);
    	ediciones.put(nombre, edi);
    	return edi;
    }
    
    //Lista Ediciones confirmadas
    public Set<String> listarEdiciones(){
    	Set<String> res = new HashSet<String>();
    	for (Entry<String, Edicion> entry : ediciones.entrySet()) {
    		Edicion edi = entry.getValue();    	    
    	    res.add(edi.getNombre());
    	}
    	return res;
    	
    	
    }
    
    public DTEdicion getDTEdicionEvento(String nombreEdicion) {
    	Edicion edi = ediciones.get(nombreEdicion);
    	if (edi == null) 
    		return null;
    	return edi.getDTEdicion();
    }
    
    public DTEdicion getDTEdicionEventoWEB(String nombreEdicion, String miNick, EntityManagerFactory fab){
    	Edicion edi = ediciones.get(nombreEdicion);
    	if (edi != null) //la edicion esta en memoria
    		return edi.getDTEdicionWEB(miNick);
    	
    	EntityManager man = fab.createEntityManager();
    	Query consulta = man.createQuery("SELECT e FROM Ediciones e WHERE e.nombre = ?1");
    	consulta.setParameter(1, nombreEdicion);
    	
    	if (!consulta.getResultList().isEmpty()) { //esta en base de datos
    		DTEdicion res = ((EdicionDAO) consulta.getResultList().getFirst()).toDTE();
    		man.close();
    		return res;
    	}
    	man.close();
    	//no esta en ningun lado
    	return null; 
    }
    
    public Boolean nuevoTipoRegistroEdicion(String nombreEdicion, String nombre, String descripcion, Float costo, Integer cupo) {
    	Edicion edi = ediciones.get(nombreEdicion);
    	return edi.nuevoTipoRegistro(nombre, descripcion, costo, cupo);
    }
    
    public DTTipoRegistro mostrarDTTipoRegistroEdicion(String nombreEdicion, String nombreTipoRegistro){
    	Edicion edi = ediciones.get(nombreEdicion);
    	return edi.getDTTipoRegistroTR(nombreTipoRegistro);
    }
    
    public Set<String> listarTipoRegistroEdicion(String nombreEdicion){
    	Edicion edi = ediciones.get(nombreEdicion);
    	return edi.listarTipoRegistro();
    }
    
    public Edicion obtenerEdicion(String nombreEdicion) {
    	return ediciones.get(nombreEdicion);
    }
    
    public Set<String> listarEdicionesIngresadas() {
    	Set<String> res = new HashSet<String>();
    	for (Entry<String, Edicion> entry : ediciones.entrySet()) {
    		Edicion edi = entry.getValue();
    	    if (edi.getEstado() == EnumEstadoEdicion.Ingresada)
    	    	res.add(edi.getNombre());
    	}
    	return res;
    } 
   //Lista todas las ediciones
    public Set<String> listarEdicionesConfirmadas() {
    	Set<String> res = new HashSet<String>();
    	for (Entry<String, Edicion> entry : ediciones.entrySet()) {
    		Edicion edi = entry.getValue();
    	    if (edi.getEstado() == EnumEstadoEdicion.Confirmada)
    	    	res.add(edi.getNombre());
    	}
    	return res;
    }
    
    public void cambiarEstado(String nombreEdicion, EnumEstadoEdicion nuevoEstado) {
    	ediciones.get(nombreEdicion).setEstado(nuevoEstado);
    }
    
    public void finalizar() {
    	this.finalizado = true;
    }
    
    public void removerEdicion(String nombre) {
    	ediciones.remove(nombre);
    }
}





