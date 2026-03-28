package com.eventosuy.logica.interaccion;

import java.util.Set;

import com.eventosuy.excepciones.UsuarioRepetidoException;
import com.eventosuy.logica.evento.DTRegistro;
import com.eventosuy.logica.usuario.DTUsuario;
import com.eventosuy.excepciones.InstitucionRepetidoException;


import java.time.LocalDate;

public interface IControladorUsuario {

    public abstract void altaAsistente(String nickname, String nombreAsis, String email, String apellido, LocalDate fechaNacimiento, String inst, String Pass, byte[] foto) throws UsuarioRepetidoException;

    public abstract void altaOrganizador(String nickname, String nombre, String email, String descripcion, String link, String Pass, byte[] foto) throws UsuarioRepetidoException;

    public abstract Set<String> listarUsuarios();

    public abstract Set<String> listarAsistentes();

    public abstract void altaInstitucion(String nombre, String descripcion, String sitioWeb, byte[] foto) throws InstitucionRepetidoException;
    
    public abstract Set<String> listarInstituciones();

    public abstract DTUsuario listarDatosDeUsuario(String nickname);

    public abstract void modificarOrganizador(String nickname, String nombre, String descripcion, String link, String pass, byte[] foto);

    public abstract void modificarAsistente(String nickname, String nombre, String apellido, LocalDate fecha, String pass, byte[] foto);

    public abstract Set<String> obtenerRegistrosUsuario(String nick);

    public abstract DTRegistro obtenerDetallesRegistroDeUsuario(String nick, String nombreEdicion);
    
    public abstract DTUsuario iniciarSesion(String iden, String contrasena);
    
    public abstract void seguirUsuario(String nickSeguidor, String nickUsuario);
    
    public abstract void dejarDeSeguirUsuario(String nickSeguidor, String nickUsuario);
    
    public abstract Set<String> obtenerUsuariosSeguidos(String nickUsuario);
    
    public abstract Set<String> obtenerUsuariosSeguidores(String nickUsuario);
    public abstract Boolean existeEmail(String email);
    public abstract Boolean existeNick(String nick);
    
    public abstract void confirmarAsistencia(String nick, String nombreEdicion);
}
