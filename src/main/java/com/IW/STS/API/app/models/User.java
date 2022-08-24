package com.IW.STS.API.app.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  
  @Column(nullable=true)   private String foto;
  @Column(nullable=true)  private LocalDate created_at=LocalDate.now();
  @Column(nullable=true)   private LocalDate updated_at;
  @Column(nullable=true)   private LocalDate deleted_at;
  @Column(nullable=false)  private Boolean estado=true;
  @Column(nullable=false)  private Boolean restablecer=false;
  @Column(nullable=false)  private String nombre;   
  @Column(nullable=false)  private String apellido; 

  private String email;

 
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable( name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles = new ArrayList<Role>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

public LocalDate getCreated_at() {
	return created_at;
}

public void setCreated_at(LocalDate created_at) {
	this.created_at = created_at;
}

public LocalDate getUpdated_at() {
	return updated_at;
}

public void setUpdated_at(LocalDate updated_at) {
	this.updated_at = updated_at;
}

public LocalDate getDeleted_at() {
	return deleted_at;
}

public void setDeleted_at(LocalDate deleted_at) {
	this.deleted_at = deleted_at;
}

public Boolean getEstado() {
	return estado;
}

public void setEstado(Boolean estado) {
	this.estado = estado;
}

public Boolean getRestablecer() {
	return restablecer;
}

public void setRestablecer(Boolean restablecer) {
	this.restablecer = restablecer;
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

public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
}
