package com.IW.STS.API.app.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rol")
public class Rol {
	
	  @Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(unique=true, nullable=false,name="id_rol") 
	  private Integer id;
	  
	
	  @Column(nullable=false)  private LocalDate created_at=LocalDate.now();
	  @Column(nullable=true)   private LocalDate updated_at;
	  @Column(nullable=true)   private LocalDate deleted_at;
	  @Column(nullable=false)  private Boolean estado=true;
	  @Column(nullable=false)  private String rol;
	
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
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}	  

}
