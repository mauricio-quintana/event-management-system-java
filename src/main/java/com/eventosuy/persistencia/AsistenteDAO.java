package com.eventosuy.persistencia;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity(name="Asistentes")
public class AsistenteDAO extends UsuarioDAO {
	private String apellido;
	private LocalDate fechaDeNacimiento;
	@OneToMany(mappedBy="asistente", cascade = CascadeType.PERSIST)
	private Set<RegistroDAO> registros;

	public AsistenteDAO(String nickname, String nombre, String email, String apellido, LocalDate fecha) {
		super(nickname, nombre, email);
		this.apellido = apellido;
		this.fechaDeNacimiento = fecha;
		this.registros = new HashSet<RegistroDAO>();
	}
	
	public AsistenteDAO() {}
	
	public void addRegistro(RegistroDAO reg) {
		this.registros.add(reg);
	}
}
