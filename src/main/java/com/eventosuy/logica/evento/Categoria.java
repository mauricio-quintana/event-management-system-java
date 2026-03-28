package com.eventosuy.logica.evento;

public class Categoria {
	private String nombre;
	
	public Categoria(String nomb) {
		this.nombre = nomb;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
}
