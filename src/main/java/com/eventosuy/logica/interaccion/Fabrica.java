package com.eventosuy.logica.interaccion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Fabrica {

    private static Fabrica instancia;
    private EntityManagerFactory fab = Persistence.createEntityManagerFactory("EventosUY");;
    private Fabrica() {};

    public static Fabrica getInstance() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    public IControladorUsuario getIControladorUsuario() {
        return new ControladorUsuario();
    }
    
    public IControladorEvento getIControladorEvento() {
        return new ControladorEvento(fab);
    }

    public void cerrar() {
    	fab.close();
    	System.out.print("cerrando app\n");
    	try (Connection con = DriverManager.getConnection(
		    "jdbc:hsqldb:file:C:/Users/Usuario/Desktop/EventosUYDB;shutdown=true", "sa", "")) {
		} catch (SQLException e) {
		    e.printStackTrace();
		}
    }
    
    public EntityManagerFactory getDB() {
		return fab;
    	
    }
}
