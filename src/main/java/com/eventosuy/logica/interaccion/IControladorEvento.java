package com.eventosuy.logica.interaccion;

import com.eventosuy.excepciones.EventoRepetidoException;
import com.eventosuy.excepciones.EdicionEventoRepetidoException;
import com.eventosuy.excepciones.TipoRegistroRepetidoException;
import com.eventosuy.logica.evento.DTEdicion;
import com.eventosuy.logica.evento.DTEvento;
import com.eventosuy.logica.evento.DTPatrocinio;
import com.eventosuy.logica.evento.DTTipoRegistro;
import com.eventosuy.logica.evento.EnumEstadoEdicion;
import com.eventosuy.logica.evento.EnumPatrocinio;
import com.eventosuy.excepciones.AsistenteErrorRegistroException;
import com.eventosuy.excepciones.EdicionEventoErrorFechaException;
import com.eventosuy.excepciones.PatrocinioErrorException;

import java.util.Set;
import java.time.LocalDate;

public interface IControladorEvento {

    public abstract void cargaDatosEvento(String nombreEvento, String descripcion, LocalDate fecha, String sigla, byte[] foto)throws EventoRepetidoException;

    public abstract Set<String> listarCategorias();

    public abstract void agregarCategoria(String nombreEvento, String nombreCategoria);

    public abstract Set<String> listarEventos();
    
    public abstract DTEvento obtenerDatosEvento(String nombreEvento);
    
    public abstract DTEvento obtenerDatosEventoWEB(String nombreEvento);
    
    public abstract Set<String> listarOrganizadores();
    
    public abstract void altaEdicionEvento(String nombreEvento, String nickNameOrg, String nombre, String sigla, String ciudad, String pais, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, byte[] foto, String video) throws EdicionEventoRepetidoException, EdicionEventoErrorFechaException;
    
    //Lista todas las ediciones
    public abstract Set<String> listarEdicionesDeEvento(String nombreEvento);
    
    
    public abstract Set<String> listarEdicionesConfirmadas(String nombreEvento);

    public abstract DTEdicion listarDetallesDeEdicion(String nombreEdicion, String nombreEvento);
    
    public abstract DTEdicion listarDetallesDeEdicionWEB(String nombreEdicion, String nombreEvento, String miNick);

    public abstract void altaTipoRegistro(String nombreEvento, String nombreEdicion, String nombre, String descripcion, Float costo, Integer cupo) throws TipoRegistroRepetidoException;

    public abstract Set<String> listarTiposRegistroDeEdicion(String nombreEvento, String nombreEdicion);

    public abstract DTTipoRegistro mostrarTipoRegistroEdicion(String nombreEvento, String nombreEdicion, String nombreTipoRegistro);

    public abstract Set<String> listarAsistentes();

    public abstract void registrarAsistentes(LocalDate fechaAlta, String nickNameAsistente, String tipoRegistro, String nombreEdicion, String nombreEvento, String codigo)throws AsistenteErrorRegistroException;

    public abstract Set<String> listarInstituciones();

    public abstract void altaPatrocinio(LocalDate fechaAlta, String nombreEdicion, String nombreEvento, String nombreTipoRegistro, String nombreInstitucion, EnumPatrocinio nivelPatrocinio, Float aporteEconomico, Integer cantidadGratituos, String codigoPatrocinio) throws PatrocinioErrorException;

    public abstract Set<String> listarPatrocinios(String nombreEvento, String nombreEdicion);

    public abstract DTPatrocinio mostrarPatrocinio(String nombreEvento, String nombreEdicion, String Institucion);

    public abstract void altaCategoria(String nombre); //No hace falta exception ya que no se necesita GUI por ahora.
    
    public abstract Set<String> listarEdicionesDeEventoIngresadas(String nombreEvento);
    
    public abstract void cambiarEstadoDeEdicion(String nombreEdicion, String nombreEvento, EnumEstadoEdicion nuevoEstado);
    
    public abstract void cargarDatos();
    
    public abstract Set<DTEvento> listarPorCategoria(String nombreCategoria);
    
    public abstract Set<String> listarEventosNoFinalizados();
    
    public abstract void finalizarEvento(String nombreEvento); 
    
    public abstract byte[] descargarPDF(String nicknameAsis, String nombreEdi, String nombreEven);
    
    public abstract Set<DTEdicion> listarTodasLasEdiciones();
    
    public abstract Set<DTEvento> listarEventosEnBuscador(String textoBuscador);
    
    public abstract Set<DTEdicion> listarEdicionesEnBuscador(String textoBuscador);
    
    public abstract void archivarEdicion(String nombreEvento, String nombreEdicion);
    
}
