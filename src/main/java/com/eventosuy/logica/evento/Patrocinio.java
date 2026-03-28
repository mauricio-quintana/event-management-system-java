package com.eventosuy.logica.evento;
import java.time.LocalDate;

import com.eventosuy.logica.usuario.Institucion;
public class Patrocinio {
	
	private String codigo;
	private LocalDate fechaRealizado;
	private Float monto;
	private EnumPatrocinio nivel;
	private Integer cantidadGratuitos;
	private Institucion institucion; 
	private TipoRegistro tipoRegistro;
	
	public Patrocinio(String codigo, LocalDate fecha, Float monto, EnumPatrocinio nivel, Integer cantGratuitos, Institucion inst, TipoRegistro tipo) {
		this.codigo = codigo;
		this.fechaRealizado = fecha;
		this.monto = monto;
		this.nivel = nivel;
		this.cantidadGratuitos = cantGratuitos;
		this.institucion = inst;
		this.tipoRegistro = tipo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public LocalDate getDTFecha() {
		return fechaRealizado;
	}
	
	public Float getMonto() {
		return monto;
	}
	
	public EnumPatrocinio getNivel() {
		return nivel;
	}
	
	public Integer getCantidadGratuitos() {
		return cantidadGratuitos;
	}
	
	public Institucion getInstitucion() {
		return institucion;
	}
	
	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}
	
	/*public void setCodigo(String cod) {
		codigo = cod;
	}
	
	public void setDTFecha(LocalDate fecha) {
		fechaRealizado = fecha;
	}
	
	public void setMonto(Float monto) {
		this.monto = monto;
	}
	
	public void setNivel(EnumPatrocinio nivel) {
		this.nivel = nivel;
	}
	
	public void setCantidadGratuitos(Integer cant) {
		cantidadGratuitos = cant;
	}
	
	public void setInstitucion(Institucion inst) {
		institucion = inst;
	}
	
	public void setTipoRegistro(TipoRegistro tipo) {
		tipoRegistro = tipo;
	}*/
	
	public DTPatrocinio getDTPatrocinio() {
		DTPatrocinio patro = new DTPatrocinio(this.institucion.getNombre(), this.tipoRegistro.obtenerNombreEdicion(), codigo, fechaRealizado, monto, nivel, cantidadGratuitos, this.tipoRegistro.getNombre()); /*REVISAR SI HAY QUE AGREGAR LA INSTITUCION Y EL TIPOREGISTRO AL DATATYPE */
		return patro;
	}
	
	public Boolean relacionadoInstitucion(String nombreInstitucion) {
		String nom = institucion.getNombre();
		if (nom.equals(nombreInstitucion)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getNombreInstitucion() {
		return this.institucion.getNombre();
	}
	
	public boolean validarGratuito(String codigo, TipoRegistro tipo) {
		if (getCodigo().equals(codigo) && getCantidadGratuitos() > 0 && getTipoRegistro() == tipo) {
			this.cantidadGratuitos -= 1;
			return true;
		}
		return false;
	}
	
}
