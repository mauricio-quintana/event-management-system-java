package com.eventosuy.logica.contenedores;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.eventosuy.logica.usuario.Institucion;

import java.util.HashSet;
import java.util.Iterator;

public class ManejadorInstitucion {

	private Map<String, Institucion> instituciones;
    private static ManejadorInstitucion instancia = null;
    
    
    private ManejadorInstitucion() {
        instituciones = new HashMap<String, Institucion>();
    }

    public static ManejadorInstitucion getInstance() {
        if (instancia == null)
            instancia = new ManejadorInstitucion();
        return instancia;
    }

    public Institucion obtenerInstitucion(String nombreInst) {
    	
    	return instituciones.get(nombreInst);
        
    }
    
    
    public Boolean crearInstitucion(String nombreInst, String descripcion, String sitioWeb, byte[] foto) {
    	
    	
    	Boolean pertenece=instituciones.containsKey(nombreInst);
    	if (!pertenece) {
    		Institucion insti= new Institucion(nombreInst, descripcion, sitioWeb, foto);
    		instituciones.put(nombreInst, insti);
    		
    	}
    	
    	
    	
    	
    	return !pertenece;
        
    }
    
    
    
public Set<String> listarInstituciones() {
    Set<String> retorno= new HashSet<String>();
	Iterator<String> nombresInst = instituciones.keySet().iterator();
	while (nombresInst.hasNext()) {
	    String nombreAgregar = nombresInst.next();
	    retorno.add(nombreAgregar);
	    
	}
	
	return retorno;
        
    }
    

	
	
}
