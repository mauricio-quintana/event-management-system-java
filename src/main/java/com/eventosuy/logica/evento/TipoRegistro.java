package com.eventosuy.logica.evento;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Hay dos obtener nombre edicion
public class TipoRegistro {

    private String nombre;
    private String descripcion;
    private Float costo;
    private Integer cupo;
    private Edicion edicion;
    private Set<Registro> registros;

    public TipoRegistro(String nombre, String descripcion, Float costo, Integer cupo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.cupo = cupo;
        this.registros = new HashSet<Registro>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Float getCosto() {
        return costo;
    }

    public Integer getCupo() {
        return cupo;
    }
    
    public Edicion getEdicion() {
    	return edicion;
    }
    
    
    public void setEdicion(Edicion edi) {
    	this.edicion = edi;
    }
    
    public String obtenerNombreEdicionDeTRegistro() {
    	return this.edicion.getNombre();
    }
    
    public Set<DTRegistro> getRegistros(){
    	Set<DTRegistro> res = new HashSet<DTRegistro>();
    	Iterator<Registro> iterator = this.registros.iterator();
    	if (iterator != null){
    		while (iterator.hasNext()){
    			Registro reg = iterator.next();
    			res.add(reg.obtenerDetallesRegistroDeUsuario());
    		}
    		return res;
    	}
    	return null;
    }
    
    public String obtenerNombreEdicion() {
    	return edicion.getNombre();
    }
    
    public void asociarRegistro(Registro reg) {
    	registros.add(reg);
    	cupo--;
    }
    
    public Boolean esPosibleRegistro() {
    	if (cupo > 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    public Boolean montoValido(Float monto, Integer cantidadGratuitos, Float costo) {
    	return costo*cantidadGratuitos <= 0.2*monto;
    	
    }
}
