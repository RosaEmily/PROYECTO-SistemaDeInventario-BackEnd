package com.IW.STS.API.app.models;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name="Usuario")
public class Usuario {
	
	  @Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(unique=true, nullable=false) 
	  private Integer id;
	  

	  @Column(nullable=false)  private LocalDate usu_created_at=LocalDate.now();
	  @Column(nullable=true)   private Date usu_updated_at;
	  @Column(nullable=true)   private Date usu_deleted_at;
	  @Column(nullable=false)  private Boolean usu_estado=true;
	  @Column(nullable=false)  private String usu_nombre;   
	  @Column(nullable=false)  private String usu_apellido;   
	  @Column(nullable=false)  private String usu_email;  
	  @Column(nullable=false)  private String usu_password;
	  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getUsu_created_at() {
		return usu_created_at;
	}
	public void setUsu_created_at(LocalDate usu_created_at) {
		this.usu_created_at = usu_created_at;
	}
	public Date getUsu_updated_at() {
		return usu_updated_at;
	}
	public void setUsu_updated_at(Date usu_updated_at) {
		this.usu_updated_at = usu_updated_at;
	}
	public Date getUsu_deleted_at() {
		return usu_deleted_at;
	}
	public void setUsu_deleted_at(Date usu_deleted_at) {
		this.usu_deleted_at = usu_deleted_at;
	}
	public Boolean getUsu_estado() {
		return usu_estado;
	}
	public void setUsu_estado(Boolean usu_estado) {
		this.usu_estado = usu_estado;
	}
	public String getUsu_nombre() {
		return usu_nombre;
	}
	public void setUsu_nombre(String usu_nombre) {
		this.usu_nombre = usu_nombre;
	}
	public String getUsu_apellido() {
		return usu_apellido;
	}
	public void setUsu_apellido(String usu_apellido) {
		this.usu_apellido = usu_apellido;
	}
	public String getUsu_email() {
		return usu_email;
	}
	public void setUsu_email(String usu_email) {
		this.usu_email = usu_email;
	}
	public String getUsu_password() {
		return usu_password;
	}
	public void setUsu_password(String usu_password) {
		this.usu_password = usu_password;
	}   
		
	
}
