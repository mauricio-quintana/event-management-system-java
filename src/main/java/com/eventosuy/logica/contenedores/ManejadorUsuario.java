package com.eventosuy.logica.contenedores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.eventosuy.logica.evento.DTRegistro;
import com.eventosuy.logica.usuario.Asistente;
import com.eventosuy.logica.usuario.Organizador;
import com.eventosuy.logica.usuario.Usuario;

import java.time.LocalDate;

public class ManejadorUsuario {
    private Map<String, Usuario> usuarios;
    private Map<String, Asistente> asistentes;
    private Map<String, Organizador> organizadores;
    private static ManejadorUsuario instancia = null;

    private ManejadorUsuario() {
        usuarios = new HashMap<String, Usuario>();
        asistentes = new HashMap<String, Asistente>();
        organizadores = new HashMap<String, Organizador>();
    }

    public static ManejadorUsuario getInstance() {
        if (instancia == null)
            instancia = new ManejadorUsuario();
        return instancia;
    }
    
    public void agregarAsistente(Asistente asis) {
    	String cedula = asis.getNickname();
        usuarios.put(cedula, asis);
        asistentes.put(cedula, asis);
    }
    
    public void agregarOrganizador(Organizador organ) {
    	String cedula = organ.getNickname();
        usuarios.put(cedula, organ);
        organizadores.put(cedula, organ);
    }

    public Usuario obtenerUsuario(String nickname ) {
        return (Usuario) usuarios.get(nickname);
    }
    
    public Usuario obtenerUsuarioEmail(String mail ) {
        for (Usuario u : usuarios.values()) {
        	if (u.getEmail().equals(mail))
        		return u;
        }
    	return null;
    }
    
    public Boolean existeNicknameUsuario(String nickUsu) {
    	
    	return usuarios.containsKey(nickUsu);
    	
    }
    
    public Boolean existeEmailUsuario(String email) {
    	
    	Boolean existe=false;
    	
    	Iterator<Map.Entry<String, Usuario>> iterator = usuarios.entrySet().iterator();
    	
    	while (iterator.hasNext() && existe==false) {
    		 Map.Entry<String, Usuario> entry = iterator.next();  // Obtener el siguiente elemento
               Usuario usu=entry.getValue();
               String emailUsu= usu.getEmail();
               if (emailUsu.equals(email)) { 
            	   existe=true;
               }
        }
    	return existe;
    } 
    
    public Set<String> listarUsuarios() {
        Set<String> retorno= new HashSet<String>();
    	Iterator<String> nicks = usuarios.keySet().iterator();
    	while (nicks.hasNext()) {
    	    String nombreAgregar = nicks.next();
    	    retorno.add(nombreAgregar);
    	    
    	}
    	
    	return retorno;
            
        }
	
    
    public void modificarOrganizador(String nickname, String nombre, String descripcion, String link, String pass, byte[] img) {
    	
    	 Organizador organ=organizadores.get(nickname);
    	 organ.setDescripcion(descripcion);
    	 organ.setNombre(nombre);
    	 organ.setLink(link);
    	 organ.setContrasena(pass);
    	 organ.setFoto(img);
    }
    
    public void modificarAsistente(String nickname, String nombre, String apellido, LocalDate fechaNac, String pass, byte[] foto) {
    	
   	 Asistente asis=asistentes.get(nickname);
   	 asis.setNombre(nombre);
   	 asis.setApelldio(apellido);
   	 asis.setFechaDeNacimiento(fechaNac);
   	 asis.setContrasena(pass);
   	 asis.setFoto(foto);
   }
    
    public Asistente obtenerAsistente(String nickname ) {
        return  asistentes.get(nickname);
    }
    
    public Boolean existeAsistente(String nickAsis) {
    	
    	return asistentes.containsKey(nickAsis);
    	
    }
 
    public Set<String> listarOrganizadores() {
     Set<String> retorno= new HashSet<String>();
 	Iterator<String> nicks = organizadores.keySet().iterator();
 	while (nicks.hasNext()) {
 	    String nombreAgregar = nicks.next();
 	    retorno.add(nombreAgregar);
 	    
 	}
 	
 	return retorno;
         
     }
    public Set<String> listarAsistentes() {
        Set<String> retorno= new HashSet<String>();
    	Iterator<String> nicks = asistentes.keySet().iterator();
    	while (nicks.hasNext()) {
    	    String nombreAgregar = nicks.next();
    	    retorno.add(nombreAgregar);
    	    
    	}
    	
    	return retorno;
            
        }
    
    
    public Set<String> obtenerRegistrosUsuario(String nickAsis){
    	
    	Asistente asis=asistentes.get(nickAsis);
    	return asis.obtenerRegistrosUsuario();
    	
    }
    
    public DTRegistro obtenerDetallesRegistroDeUsuario(String nickAsis, String nombreEdicion) {
    	
    	Asistente asis=asistentes.get(nickAsis);
    	return asis.obtenerDetallesRegistroDeUsuario(nombreEdicion);
    	
    }

	public void confirmarAsistencia(String nick, String nombreEdicion) {
		Asistente asis=asistentes.get(nick);
		asis.confirmarAsistencia(nombreEdicion);
		
	}
       

}
