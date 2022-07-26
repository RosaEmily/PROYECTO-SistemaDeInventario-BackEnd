package com.IW.STS.API.app.models;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class Usuario {
	
	  @Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(unique=true, nullable=false,name="id_usuario") 
	  private Integer id;
	  

	  @Column(nullable=false)  private LocalDate created_at=LocalDate.now();
	  @Column(nullable=true)   private LocalDate updated_at;
	  @Column(nullable=true)   private LocalDate deleted_at;
	  @Column(nullable=false)  private Boolean estado=true;
	  @Column(nullable=false)  private Boolean restablecer=false;
	  @Column(nullable=false)  private String nombre;   
	  @Column(nullable=false)  private String apellido;   
	  @Column(nullable=false)  private String email;  
	  @Column(nullable=false)  private String password= "password";
	  
		@ManyToOne
		@JoinColumn(name="id_rol", referencedColumnName = "id_rol")
		private Rol rol;
	  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Boolean getRestablecer() {
		return restablecer;
	}
	public void setRestablecer(Boolean restablecer) {
		this.restablecer = restablecer;
	}
	
	
}
