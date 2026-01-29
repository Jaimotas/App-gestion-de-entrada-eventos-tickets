package com.tickets4u.login.DTO;

public class RegisterRequest {
	private String nombreUsuario;
    private String email;
    private String contrasena;
    
    public String getNombreUsuario() {
    	return nombreUsuario;
    }
    public String getEmail() {
    	return email;
    }
    public String getContrasena() {
    	return contrasena;
    }
}
