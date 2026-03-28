package com.eventosuy.logica.interaccion;

import com.eventosuy.excepciones.UsuarioRepetidoException;
import com.eventosuy.logica.contenedores.ManejadorInstitucion;
import com.eventosuy.logica.contenedores.ManejadorUsuario;
import com.eventosuy.logica.evento.DTRegistro;
import com.eventosuy.logica.usuario.Asistente;
import com.eventosuy.logica.usuario.DTUsuario;
import com.eventosuy.logica.usuario.Institucion;
import com.eventosuy.logica.usuario.Organizador;
import com.eventosuy.logica.usuario.Usuario;
import com.eventosuy.excepciones.InstitucionRepetidoException;

import java.util.Set;
import java.time.LocalDate;

public class ControladorUsuario implements IControladorUsuario{
	
	public ControladorUsuario() {}
	
	public void altaAsistente(String nickname, String nombreAsis, String email, String apellido, LocalDate fechaNacimient, String inst, String Pass, byte[] foto) throws UsuarioRepetidoException {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		ManejadorInstitucion mani = ManejadorInstitucion.getInstance();
		boolean okNick = manu.existeNicknameUsuario(nickname);
		boolean okEmail = manu.existeEmailUsuario(email);
		if (!okNick&&!okEmail) {
			Asistente asis = new Asistente(nickname, nombreAsis, email, apellido, fechaNacimient, Pass, foto);
			Institucion institucion;
			if (inst=="") {
				institucion = null;
			}else {
				institucion =mani.obtenerInstitucion(inst);
			}
			asis.asociarInstitucion(institucion);
			manu.agregarAsistente(asis);
		}else {
			throw new UsuarioRepetidoException("Usuario o Email ya existente.");
		}
	}
	
	public void altaInstitucion(String nombre, String descripcion, String sitioWeb, byte[] foto)throws InstitucionRepetidoException {
		ManejadorInstitucion mani = ManejadorInstitucion.getInstance();
		boolean okey= mani.crearInstitucion(nombre, descripcion, sitioWeb, foto);
		if (!okey) {
			throw new InstitucionRepetidoException("Nombre de Institución Existente.");
		}
	}
	
	public Set<String> listarInstituciones(){
		ManejadorInstitucion mani = ManejadorInstitucion.getInstance();
		return mani.listarInstituciones();
	}
	
	public void altaOrganizador(String nickname, String nombre, String email, String descripcion, String link, String Pass, byte[] foto) throws UsuarioRepetidoException{
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		boolean okNick = manu.existeNicknameUsuario(nickname);
		boolean okEmail = manu.existeEmailUsuario(email);
		if (!okNick&&!okEmail) {
			Organizador org = new Organizador(nickname, nombre, email, descripcion, link, Pass, foto);
			manu.agregarOrganizador(org);
		}else {
			throw new UsuarioRepetidoException("Usuario o Email ya existente.");
		}
	}
	
	public Set<String> listarUsuarios(){
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		return manu.listarUsuarios();
	}
	
	public DTUsuario listarDatosDeUsuario(String nickname) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Usuario usr = manu.obtenerUsuario(nickname);
		return usr.getDTUsuario();
	}
	
	public void modificarOrganizador(String nickname, String nombre, String descripcion, String link, String pass, byte[] foto) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		manu.modificarOrganizador(nickname, nombre, descripcion, link, pass, foto);
	}
	
	public void modificarAsistente(String nickname, String nombre, String apellido, LocalDate fecha, String pass, byte[] foto) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		manu.modificarAsistente(nickname, nombre, apellido, fecha, pass, foto);
	}
	
	public Set<String> listarAsistentes(){
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		return manu.listarAsistentes();
	}
	
	public Set<String> obtenerRegistrosUsuario(String nick) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		return manu.obtenerRegistrosUsuario(nick);
	}
	
	public DTRegistro obtenerDetallesRegistroDeUsuario(String nick, String nombreEdicion) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		return manu.obtenerDetallesRegistroDeUsuario(nick, nombreEdicion);
	}
	
	public DTUsuario iniciarSesion(String iden, String contrasena) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		//this.listarDatosDeUsuario(id);
		
		Usuario usr = manu.obtenerUsuario(iden);
		if (usr == null) {
			usr = manu.obtenerUsuarioEmail(iden);
		}
		if (usr == null)
			return null;
		else {
			String pass = usr.getContrasena();
			if (!pass.equals(contrasena))
				return null;
		}
		return usr.getDTUsuario();
	}
	
	public void seguirUsuario(String nickSeguidor, String nickUsuario) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Usuario seguidor = manu.obtenerUsuario(nickSeguidor);
		Usuario seguido = manu.obtenerUsuario(nickUsuario);
		seguidor.seguirUsuario(nickUsuario);
		seguido.nuevoSeguidor(nickSeguidor);
	}
	
	public void dejarDeSeguirUsuario(String nickSeguidor, String nickUsuario) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Usuario seguidor = manu.obtenerUsuario(nickSeguidor);
		Usuario seguido = manu.obtenerUsuario(nickUsuario);
		seguido.dejadoDeSeguid(nickSeguidor);
		seguidor.dejarDeSeguirUsuario(nickUsuario);
		
	}
	
	//obtengo los usuarios que sigue el de nickname parametrico
	public Set<String> obtenerUsuariosSeguidos(String nickUsuario) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Usuario usu = manu.obtenerUsuario(nickUsuario);
		return usu.getSeguidos();
	}
	
	//obtengo los usuarios que siguen al de nickname parametrico
	public Set<String> obtenerUsuariosSeguidores(String nickUsuario) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		Usuario usu = manu.obtenerUsuario(nickUsuario);
		return usu.getSeguidores();
	}
	
	
	public Boolean existeEmail(String email) {
	ManejadorUsuario manu = ManejadorUsuario.getInstance();
		
	return manu.existeEmailUsuario(email); 	
		
	}
	
	public Boolean existeNick(String nick) {
	ManejadorUsuario manu = ManejadorUsuario.getInstance();
			
	return manu.existeNicknameUsuario(nick); 	
			
	}

	
	public void confirmarAsistencia(String nick, String nombreEdicion) {
		ManejadorUsuario manu = ManejadorUsuario.getInstance();
		manu.confirmarAsistencia(nick, nombreEdicion);
		
	}
	
	
}
