package com.eventosuy.persistencia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name="Usuaios")
@Inheritance(strategy=InheritanceType.JOINED)
//deicidi unilateralmente que no tiene sentido agregar la columna tipo_usuario
//a la base de datos si quieren que usemos herencia joined
public abstract class UsuarioDAO {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	private int idn; //clave de persistencia
    private String nickname;
    private String nombre;
    private String email;
    
    public UsuarioDAO(String nickname, String nombre, String email) {
    	this.nickname = nickname;
    	this.nombre = nombre;
    	this.email = email;
    }
    
    public UsuarioDAO() {}
    
    public String getNickname() {
    	return nickname;
    }
}
