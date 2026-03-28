package com.eventosuy.logica.contenedores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.eventosuy.logica.evento.Categoria;

import java.util.Iterator;
import java.util.Map;

public class ManejadorCategoria {

	
	private static ManejadorCategoria instancia = null;
	private Map<String, Categoria> categorias;
    private ManejadorCategoria() {
        categorias = new HashMap<String, Categoria>();
    }

    public static ManejadorCategoria getInstance() {
        if (instancia == null)
            instancia = new ManejadorCategoria();
        return instancia;
    }

    public Boolean crearCategoria(String nombreCat) {
    	
    	
    	Boolean pertenece=categorias.containsKey(nombreCat);
    	if (!pertenece) {
    		Categoria cat= new Categoria(nombreCat);
    		categorias.put(nombreCat, cat);
    		
    	}
    	
    	
    	
    	return !pertenece;
        
    }
 
 
    public Boolean member(String nombreCat) {
        return categorias.containsKey(nombreCat);
    }
	
   
    public Categoria obtenerCat(String nombreCat) {
        return categorias.get(nombreCat);
    }

    public Categoria obtenerCategoria(String nombreCat) {
        return categorias.get(nombreCat);
    }
	
    
    public Set<String> listarCategorias() {
        Set<String> retorno= new HashSet<String>();
    	Iterator<String> nombresCat = categorias.keySet().iterator();
    	while (nombresCat.hasNext()) {
    	    String nombreAgregar = nombresCat.next();
    	    retorno.add(nombreAgregar);
    	    
    	}
    	
    	return retorno;
            
        }
	
	
}
