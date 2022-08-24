package com.IW.STS.API.app.security.request;

import java.util.List;

import com.IW.STS.API.app.models.Role;


public class SignupRequest {
  
  private String username;

  
  private String email;
  
  private String nombre;
  
  private String apellido;

  private String foto;
  
  private List<Role> role;

  
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  

  public String getFoto() {
	return foto;
}

public void setFoto(String foto) {
	this.foto = foto;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getApellido() {
	return apellido;
}

public void setApellido(String apellido) {
	this.apellido = apellido;
}

public List<Role> getRole() {
    return this.role;
  }

  public void setRole(List<Role> role) {
    this.role = role;
  }
}
