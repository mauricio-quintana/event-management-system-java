package com.eventosuy.logica.usuario;

import java.util.HashSet;
import java.util.Set;

public abstract class Usuario {

    private String nickname;
    private String nombre;
    private String email;
    private String pass;
    private byte[] foto;
    private Set<String> seguidos;
    private Set<String> seguidores;
    
    public Usuario(String nick, String nomb, String email, String pass, byte[] foto) {
        this.nickname = nick;
        this.nombre = nomb;
        this.email = email;
        this.pass = pass;
        this.foto = foto;
        this.seguidos = new HashSet<>();
        this.seguidores = new HashSet<>();
        
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
    	return pass;
    }
    
    public byte[] getFoto() {
    	return this.foto;
    }
    
    public Set<String> getSeguidos(){
    	return this.seguidos;
    }
    
    public Set<String> getSeguidores() {
    	return this.seguidores;
    }
    
    /*public void setNickname(String nick) {
        nickname = nick;
    }*/

    public void setNombre(String nomb) {
        nombre = nomb;
    }

    /*public void setEmail(String email) {
        this.email = email;
    }*/
    
    public void setContrasena(String pass) {
    	this.pass = pass;
    }
    
    public void setFoto(byte[] foto) {
    	this.foto= foto;
    }
    
    public void seguirUsuario(String nickUsuario) {
    	if (nickUsuario != null) {
    		seguidos.add(nickUsuario);
    	}
    }
    
    public void nuevoSeguidor(String nickUsuario) {
    	if (nickUsuario != null) {
    		seguidores.add(nickUsuario);
    	}
    }
    
    public void dejarDeSeguirUsuario(String nickUsuario) {
    	if (nickUsuario != null) {
    		seguidos.remove(nickUsuario);
    	}
    }
    
    public void dejadoDeSeguid(String nickUsuario) {
    	if (nickUsuario != null) {
    		seguidores.remove(nickUsuario);
    	}
    }
    
    public abstract DTUsuario getDTUsuario();
    
    
}
