package com.eventosuy.logica.contenedores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.eventosuy.logica.evento.DTEdicion;
import com.eventosuy.logica.evento.DTEvento;
import com.eventosuy.logica.evento.DTTipoRegistro;
import com.eventosuy.logica.evento.Edicion;
import com.eventosuy.logica.evento.EnumEstadoEdicion;
import com.eventosuy.logica.evento.Evento;

import jakarta.persistence.EntityManagerFactory;

public class ManejadorEvento {
	
		private Map<String, Evento> eventos;
	   
	    private static ManejadorEvento instancia = null;

	    private ManejadorEvento() {
	        eventos = new HashMap<String, Evento>();
	        
	    }

	    public static ManejadorEvento getInstance() {
	        if (instancia == null)
	            instancia = new ManejadorEvento();
	        return instancia;
	    }
	    
	    
	    public void agregarEvento(Evento evento) {
	        String nombre = evento.getNombre();
	        eventos.put(nombre, evento);
	    }

	    public Evento obtenerEvento(String nombre ) {
	        return eventos.get(nombre);
	    }
	    
	    
	    public Boolean existeEvento(String nombre) {
	    	
	    	return eventos.containsKey(nombre);
	    	
	    }
	
	    public Set<String> listarEventos() {
	        Set<String> retorno= new HashSet<String>();
	    	Iterator<String> nombres = eventos.keySet().iterator();
	    	while (nombres.hasNext()) {
	    	    String nombreAgregar = nombres.next();
	    	    retorno.add(nombreAgregar);
	    	    
	    	}
	    	
	    	return retorno;
	            
	    }
	    
	    public Boolean edicionValida(String nombreEdicion) {
	    	
	    	Boolean retorno=true;
	    	Iterator<Map.Entry<String, Evento>> iterator = eventos.entrySet().iterator();
	    	
	    	
	    	while (iterator.hasNext() && retorno==true) {
	    		Map.Entry<String, Evento> entry = iterator.next();
	    		Evento even=entry.getValue();
	    		retorno=even.nombreEdicionValido(nombreEdicion);
	    		
	        }
	    	
	    	return retorno;
	    	
	    }
	    
	    
	    public Set<String> listarEdicionesDeEvento(String nombreEvento){
	    	
	    	 
	    	Evento even= eventos.get(nombreEvento);
	    	Set<String> ediciones=even.listarEdiciones();
	    	
	    	
	    	return ediciones;
	    }
	    
	    public Set<String> listarEdicionesConfirmadas(String nombreEvento){
	    	
	    	 
	    	Evento even= eventos.get(nombreEvento);
	    	Set<String> ediciones=even.listarEdicionesConfirmadas();
	    	
	    	
	    	return ediciones;
	    }
	    
        public DTEdicion listarDetallesEdicionDeEvento(String nombreEdicion, String nombreEvento){

	    	Evento even= eventos.get(nombreEvento);
	    	DTEdicion infoEdicion=even.getDTEdicionEvento(nombreEdicion);
	    	
	    	return infoEdicion;
	    }
	    
        public DTEdicion listarDetallesEdicionWEB(String nombreEdicion, String nombreEvento, String miNick, EntityManagerFactory fab) {
	    	Evento even = eventos.get(nombreEvento);
	    	DTEdicion infoEdicion = even.getDTEdicionEventoWEB(nombreEdicion, miNick, fab);
	    	
	    	return infoEdicion;
        }

        public Boolean nuevoTipoRegistroEvento(String nombreEvento, String nombreEdicion, String nombreTR, String descripcion, 
        		Float costo, Integer cupos) {
        	Evento even= eventos.get(nombreEvento);
        	Boolean retorno=even.nuevoTipoRegistroEdicion(nombreEdicion, nombreTR, descripcion, costo, cupos);
        	
        	return retorno;
        }
	
        
        public Set<String> listarTipoRegistroEvento(String nombreEvento, String nombreEdicion){
	    	
        	 
	    	Evento even= eventos.get(nombreEvento);
	    	Set<String> tipoRegistros=even.listarTipoRegistroEdicion(nombreEdicion);
	    	
	    	
	    	return tipoRegistros;
	    }
        
        public DTTipoRegistro mostrarDTTipoRegistroEvento(String nombreEvento, String nombreEdicion, String nombreTR){
	    	
	    	
	    	Evento even= eventos.get(nombreEvento);
	    	DTTipoRegistro infoTipoRegistro=even.mostrarDTTipoRegistroEdicion(nombreEdicion, nombreTR);
	    	
	    	
	    	return infoTipoRegistro;
	    }
        
        public Edicion obtenerEdicion(String nombreEvento, String nombreEdicion) {
        	Evento even= eventos.get(nombreEvento);
        	Edicion edicion=even.obtenerEdicion(nombreEdicion);
        	return edicion;
        
        }
        
        public Set<DTEvento> listarPorCategoria(String nombreCategoria){
        	
        	Iterator<Map.Entry<String, Evento>> iterator = eventos.entrySet().iterator();
	    	
        	Set<DTEvento> retorno= new HashSet<DTEvento>();
        	
        	
	    	while (iterator.hasNext()) {
	    		Map.Entry<String, Evento> entry = iterator.next();
	    		Evento even=entry.getValue();
	    		
	    		if (even.asociado(nombreCategoria)&&!even.getFinalizado()) {
	    			retorno.add(even.getDTEvento());
	    		}
	    		
	    		
	    		
	        }
 	    	
 	    	return retorno;
        }
        
        public Set<String> listarEventosNoFinalizados(){
        	Set<String> retorno= new HashSet<String>();
	    	Iterator<String> nombres = eventos.keySet().iterator();
	    	while (nombres.hasNext()) {
	    			String nombreEvento = nombres.next();
	    			Evento evento = eventos.get(nombreEvento);
	    			if (!evento.getFinalizado()) {
	    				retorno.add(nombreEvento);
	    		}
	    	}
	    	
	    	return retorno;
        }
        
        public void finalizarEvento(String nombreEvento) {
        	Evento eve = eventos.get(nombreEvento);
        	eve.finalizar();
        }
        
        public Set<DTEdicion> listarTodasLasEdiciones(){
        	Set<DTEdicion> retorno = new HashSet<>();
        	for (Evento evento : eventos.values()) {
    	    	Set<String> listaEdiciones = evento.listarEdiciones();
    	    	for (String edicion : listaEdiciones) {
    	    		DTEdicion edi = evento.getDTEdicionEvento(edicion);
    	    		if (edi.getEstado() == EnumEstadoEdicion.Confirmada)
    	    			retorno.add(edi);
    	    	}
	    	}
        	return retorno;
        }
        
        public Set<DTEvento> listarEventosEnBuscador(String textoBuscador){
        	Set<DTEvento> resultado = new HashSet<>();
            String texto = textoBuscador.trim().toLowerCase();
            for (Evento e : eventos.values()) {
                String nombre = e.getNombre().toLowerCase();
                String descripcion = (e.getDescripcion() != null) ? e.getDescripcion().toLowerCase() : "";
                if ((nombre.contains(texto) || descripcion.contains(texto)) && !e.getFinalizado()) {
                    resultado.add(e.getDTEvento());
                }
            }

            return resultado;
        }
        
        public Set<DTEdicion> listarEdicionesEnBuscador(String textoBuscador){
        	Set<DTEdicion> resultado = new HashSet<>();
            String texto = textoBuscador.trim().toLowerCase();
            for (Evento eve : eventos.values()) {
            	if (!eve.getFinalizado()) {
	                Set<String> ediciones = eve.listarEdiciones();
	                for (String edi : ediciones) {
	                    String nombre = edi.toLowerCase();
	                    if (nombre.contains(texto) && eve.getDTEdicionEvento(edi).getEstado() == EnumEstadoEdicion.Confirmada) {
	                        resultado.add(eve.getDTEdicionEvento(edi));
	                    }
                    }
                }
            }
            return resultado;
        }

        public void removerEdicion(String nombreEvento, String nombreEdicion) {
        	Evento eve = this.obtenerEvento(nombreEvento);
        	eve.removerEdicion(nombreEdicion);
        }
}
