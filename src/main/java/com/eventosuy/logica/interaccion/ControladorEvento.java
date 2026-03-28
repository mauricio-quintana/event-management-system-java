package com.eventosuy.logica.interaccion;
 
import java.util.List;
import java.util.Set;

import com.eventosuy.cargadatos.CargaDeDatos; 
import com.eventosuy.excepciones.AsistenteErrorRegistroException;
import com.eventosuy.excepciones.EdicionEventoErrorFechaException;
import com.eventosuy.excepciones.EdicionEventoRepetidoException;
import com.eventosuy.excepciones.EventoRepetidoException;
import com.eventosuy.excepciones.PatrocinioErrorException;
import com.eventosuy.excepciones.TipoRegistroRepetidoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import com.eventosuy.logica.contenedores.ManejadorCategoria;
import com.eventosuy.logica.contenedores.ManejadorEvento;
import com.eventosuy.logica.contenedores.ManejadorInstitucion;
import com.eventosuy.logica.contenedores.ManejadorUsuario;
import com.eventosuy.logica.evento.Categoria;
import com.eventosuy.logica.evento.DTEdicion;
import com.eventosuy.logica.evento.DTEvento;
import com.eventosuy.logica.evento.DTPatrocinio;
import com.eventosuy.logica.evento.DTTipoRegistro;
import com.eventosuy.logica.evento.Edicion;
import com.eventosuy.logica.evento.EnumEstadoEdicion;
import com.eventosuy.logica.evento.EnumPatrocinio;
import com.eventosuy.logica.evento.Evento;
import com.eventosuy.logica.evento.Patrocinio;
import com.eventosuy.logica.evento.Registro;
import com.eventosuy.logica.evento.TipoRegistro;
import com.eventosuy.logica.usuario.Asistente;
import com.eventosuy.logica.usuario.Institucion;
import com.eventosuy.logica.usuario.Organizador;
import com.eventosuy.persistencia.EdicionDAO;

import java.io.ByteArrayOutputStream;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.time.LocalDate;

public class ControladorEvento implements IControladorEvento  {
	private EntityManagerFactory fabricaDB;
	
	public ControladorEvento(EntityManagerFactory fab) { fabricaDB = fab; }
	
	public void cargaDatosEvento(String nombreEvento, String descripcion, LocalDate fecha, String sigla, byte[] foto) throws EventoRepetidoException {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Boolean okNom = mane.existeEvento(nombreEvento);
		if (!okNom) {
			Evento evento = new Evento(nombreEvento, sigla, descripcion, fecha, foto);
			mane.agregarEvento(evento);
		}else {
			throw new EventoRepetidoException("Nombre evento ya existente.");
		}
	}
	
	public Set<String> listarCategorias(){
		ManejadorCategoria manc = ManejadorCategoria.getInstance();
		return manc.listarCategorias();
	}
	
	public void agregarCategoria(String nombreEvento, String nombreCategoria) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		ManejadorCategoria manc = ManejadorCategoria.getInstance();
		Evento evento = mane.obtenerEvento(nombreEvento);
		boolean existe = manc.member(nombreCategoria);
		if (existe) {
			boolean asigHecha = evento.asociado(nombreCategoria);
			if (!asigHecha) {
				Categoria cat = manc.obtenerCat(nombreCategoria);
				evento.asociarCategoria(cat);
			}
		}
	}
	
	public Set<String> listarEventos(){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarEventos();
	}
	
	public DTEvento obtenerDatosEvento(String nombre) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Evento evento = mane.obtenerEvento(nombre);
		return evento.getDTEvento();
	}
	
	public DTEvento obtenerDatosEventoWEB(String nombre) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Evento evento = mane.obtenerEvento(nombre);
		return evento.getDTEventoWEB();
	}
	
	public Set<String> listarOrganizadores(){
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		return manu.listarOrganizadores();
	}
	
	public  void altaEdicionEvento(String nombreEvento, String nicknameOrganizador, String nombreEdicion, String sigla, String ciudad, String pais, 
			LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, byte[]  foto,  String video)throws EdicionEventoRepetidoException, EdicionEventoErrorFechaException {
		if (fechaInicio.isAfter(fechaFin)){
			throw new EdicionEventoErrorFechaException("Las fechas son inconsistentes.");
		}
		
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Boolean valido = mane.edicionValida(nombreEdicion);
		if (!valido) //fallo por nombre en edicion en memoria
			throw new EdicionEventoRepetidoException("Nombre edición ya existente.");
		
    	EntityManager man = fabricaDB.createEntityManager();
    	Query consulta = man.createQuery("SELECT e FROM Ediciones e WHERE e.nombre = ?1");
    	consulta.setParameter(1, nombreEdicion);
    	List<Object> lista = consulta.getResultList();
    	man.close();
    	if (!lista.isEmpty()) {//fallo por nombre en edicion archivada
    		throw new EdicionEventoRepetidoException("Nombre edición ya existente."); 
    	}
		Evento evento=mane.obtenerEvento(nombreEvento);
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Organizador organizador=(Organizador) manu.obtenerUsuario(nicknameOrganizador);
		Edicion edi=evento.nuevaEdicion(nombreEdicion, sigla, ciudad, pais, fechaInicio, fechaFin, fechaAlta, foto, video);
		organizador.asociarEdicion(edi);
		edi.asociarOrganizador(organizador);
		edi.asociarEvento(evento);
		
		}
	
	//Lista ediciones Confirmadas
	public Set<String> listarEdicionesDeEvento(String nombreEvento){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarEdicionesDeEvento(nombreEvento);
	}
	
	public Set<String> listarEdicionesConfirmadas(String nombreEvento){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarEdicionesConfirmadas(nombreEvento);
	}
	
	public DTEdicion listarDetallesDeEdicion(String nombreEdicion, String nombreEvento) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		DTEdicion resp = mane.listarDetallesEdicionDeEvento(nombreEdicion, nombreEvento);
		//si fue archivada devuelve null, busco en base de datos
		if (resp == null) {
			EntityManager man = fabricaDB.createEntityManager();
			Query consulta = man.createQuery("SELECT e FROM Ediciones e WHERE e.nombre = ?1");
			consulta.setParameter(1, nombreEdicion);
			resp = ((EdicionDAO) consulta.getResultList().getFirst()).toDTE();
			man.close();
		}
		
		return resp;
	}
	
	public DTEdicion listarDetallesDeEdicionWEB(String nombreEdicion, String nombreEvento, String miNick) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarDetallesEdicionWEB(nombreEdicion, nombreEvento, miNick, fabricaDB);
	}
	
	public void altaTipoRegistro(String nombreEvento, String nombreEdicion, String nombre, String descripcion, Float costo, Integer cupo) throws TipoRegistroRepetidoException {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		boolean okey = mane.nuevoTipoRegistroEvento(nombreEvento, nombreEdicion, nombre, descripcion, costo, cupo);
		if (!okey) {
			throw new TipoRegistroRepetidoException("Nombre tipo registro ya existente para esta edición.");
		}
	}
	
	public Set<String> listarTiposRegistroDeEdicion(String nombreEvento, String nombreEdicion){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarTipoRegistroEvento(nombreEvento, nombreEdicion);
	}
	
	public DTTipoRegistro mostrarTipoRegistroEdicion(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.mostrarDTTipoRegistroEvento(nombreEvento, nombreEdicion, nombreTipoRegistro);
	}
	
	public Set<String> listarAsistentes(){ //TODO esta funcion esta repetida, sacarla de aca y que todos los llamados la hagan desde ctrlusu
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		return manu.listarAsistentes();
	}
	
	public void registrarAsistentes(LocalDate fechaAlta, String nickNameAsistente, String nombreTipoRegistro, String nombreEdicion, String nombreEvento, String codigo)throws AsistenteErrorRegistroException {
		if (codigo==null){
			codigo = "";
		}
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Evento evento= mane.obtenerEvento(nombreEvento);
		Edicion edicion= evento.obtenerEdicion(nombreEdicion);
		TipoRegistro tipoRegistro= edicion.obtenerTipoRegistro(nombreTipoRegistro);
		Boolean okRegistro= tipoRegistro.esPosibleRegistro();
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Asistente asistente= manu.obtenerAsistente(nickNameAsistente);
		Boolean okAsistente=asistente.noRegistradoEdicion(nombreEdicion);
		String inst = null;
		if (asistente.getInstitucion() != null)
			inst = asistente.getInstitucion().getNombre();

		if (okRegistro && okAsistente) {
			float costo = edicion.validarRegistroGratuito(inst, codigo, tipoRegistro); //verifico sea valido el codigo de patrocinio
			
			if (costo >=0) {
				Registro registro=asistente.registrarseEdicion(edicion, costo, fechaAlta, tipoRegistro, nombreEvento, codigo);
				edicion.asociarRegistro(registro);	
			} else {
				throw new AsistenteErrorRegistroException("Código de registro invalido.");
			}
		}else {
			if (!okAsistente) {
				throw new AsistenteErrorRegistroException("Usuario ya habia sido registrado a esta edición de evento.");
			}
			if (!okRegistro) {
				throw new AsistenteErrorRegistroException("No es posible un registro de este tipo, no quedan cupos disponibles.");
			}	
		}
	}
	
	public Set<String> listarInstituciones(){
		ManejadorInstitucion mani = ManejadorInstitucion.getInstance();
		return mani.listarInstituciones();
	}
	
	public void altaPatrocinio(LocalDate fechaAlta, String nombreEdicion, String nombreEvento, String nombreTipoRegistro, String nombreInsti, EnumPatrocinio nivelPatrocinio, 
			Float aporteEconimico, Integer cantidadGratuitos, String codigoPatrocinio)throws PatrocinioErrorException {
		
		ManejadorInstitucion mani = ManejadorInstitucion.getInstance();
		Institucion insti=mani.obtenerInstitucion(nombreInsti);
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Edicion edi=mane.obtenerEdicion(nombreEvento, nombreEdicion);
		Boolean valido=edi.validarPatrocinio(nombreInsti);
		if (valido) {
			TipoRegistro tipoRegistro= edi.obtenerTipoRegistro(nombreTipoRegistro);
			Boolean valido2=tipoRegistro.montoValido(aporteEconimico, cantidadGratuitos, tipoRegistro.getCosto());
			if (valido2) {
				Patrocinio patrocinio= edi.nuevoPatrocinioEvento(tipoRegistro, nivelPatrocinio, aporteEconimico, cantidadGratuitos, codigoPatrocinio, 
						fechaAlta, insti);
				insti.agregarPatrocinio(patrocinio);
			}else {
				throw new PatrocinioErrorException("costo registros a otorgar supera el 20 % del aporte economico");
			}
				
		}else {
			throw new PatrocinioErrorException("Institucion ya estaba patrocinando");
		}
		
	}
	
	public Set<String> listarPatrocinios(String nombreEvento, String nombreEdicion){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Evento evento = mane.obtenerEvento(nombreEvento);
		return evento.listarPatrocinios(nombreEdicion);
	}
	
	public DTPatrocinio mostrarPatrocinio(String nombreEvento, String nombreEdicion, String Institucion) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Evento evento = mane.obtenerEvento(nombreEvento);
		return evento.mostrarPatrocinio(nombreEdicion, Institucion);
	}
	
	public void altaCategoria(String nombre) {
		ManejadorCategoria manc = ManejadorCategoria.getInstance();
		manc.crearCategoria(nombre);
	}
	
    public Set<String> listarEdicionesDeEventoIngresadas(String nombreEvento){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		Evento evento = mane.obtenerEvento(nombreEvento);
		return evento.listarEdicionesIngresadas();
    }
	
	 public void cambiarEstadoDeEdicion(String nombreEdicion, String nombreEvento, EnumEstadoEdicion nuevoEstado) {
		 ManejadorEvento mane = ManejadorEvento.getInstance();
		 Evento evento = mane.obtenerEvento(nombreEvento);
		 evento.cambiarEstado(nombreEdicion, nuevoEstado);
	 }
	
	public void cargarDatos() {
		new CargaDeDatos();
	}
	
	public Set<DTEvento> listarPorCategoria(String nombreCategoria){
		
		ManejadorEvento mane = ManejadorEvento.getInstance();
		
		return mane.listarPorCategoria(nombreCategoria);
	}
	
	public Set<String> listarEventosNoFinalizados(){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarEventosNoFinalizados();
	}
	
	public void finalizarEvento(String nombreEvento) {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		mane.finalizarEvento(nombreEvento);
	}
	
	public byte[] descargarPDF(String nicknameAsis, String nombreEdi, String nombreEven) {
		
		ManejadorEvento maneE = ManejadorEvento.getInstance();
		ManejadorUsuario maneU = ManejadorUsuario.getInstance();
		
	    Asistente asis = maneU.obtenerAsistente(nicknameAsis);
		Evento evento = maneE.obtenerEvento(nombreEven);
		Edicion edicion = evento.obtenerEdicion(nombreEdi);
		DTEdicion dte = edicion.getDTEdicion();
		
		ByteArrayOutputStream arraybytes = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(arraybytes);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf);
		
	    document.add(new Paragraph("CONSTANCIA DE ASISTENCIA").setTextAlignment(TextAlignment.CENTER).setFontSize(18));
	    document.add(new Paragraph("\nSe certifica que el asistente: " + asis.getNombre() + " " + asis.getApellido()).setFontSize(12));
	    document.add(new Paragraph("\nHa participado en la edición \"" + nombreEdi + "\" del evento \"" + nombreEven + "\"."));
	    document.add(new Paragraph("Ciudad: " + dte.getCiudad()));
	    document.add(new Paragraph("País: " + dte.getPais()));
	    document.add(new Paragraph("Fechas: " + dte.getFechaInicio() + " a " + dte.getFechaFin()));
	 
	    document.close();
	    return arraybytes.toByteArray();
		
	}
	
	public Set<DTEdicion> listarTodasLasEdiciones() {
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarTodasLasEdiciones();
	}
	
	public Set<DTEvento> listarEventosEnBuscador(String textoBuscador){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarEventosEnBuscador(textoBuscador);
	}
	
	public Set<DTEdicion> listarEdicionesEnBuscador(String textoBuscador){
		ManejadorEvento mane = ManejadorEvento.getInstance();
		return mane.listarEdicionesEnBuscador(textoBuscador);
	}
	
    public void archivarEdicion(String nombreEvento, String nombreEdicion) {
    	ManejadorEvento mane = ManejadorEvento.getInstance();
    	Edicion edi = mane.obtenerEdicion(nombreEvento, nombreEdicion);
    	EntityManager man = fabricaDB.createEntityManager();
    	edi.persistirEdicion(man);
    	mane.removerEdicion(nombreEvento, nombreEdicion);
    	man.close();
    }
    
}
