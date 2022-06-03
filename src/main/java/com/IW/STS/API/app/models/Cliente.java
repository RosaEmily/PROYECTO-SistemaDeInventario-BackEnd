package com.IW.STS.API.app.models;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Cliente")
public class Cliente {
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false) 
	private Integer id;
		
	@Column(nullable=false)  private String nombre;
	@Column(nullable=false)  private String apellido;
	@Column(nullable=false)  private String  doi;
	@Column(nullable=true)   private String  email;
	@Column(nullable=false)  private String  tipoDoi;
	@Column(nullable=false)  private String  direccion;
	@Column(nullable=false)  private Boolean  estado=true;
	@Column(nullable=true)   private Date updated_at;
	@Column(nullable=true)   private Date deleted_at;
	@Column(nullable=true)   private LocalDate create_at=LocalDate.now();
	
	
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTipoDoi() {
		return tipoDoi;
	}
	public void setTipoDoi(String tipoDoi) {
		this.tipoDoi = tipoDoi;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public Date getDeleted_at() {
		return deleted_at;
	}
	public void setDeleted_at(Date deleted_at) {
		this.deleted_at = deleted_at;
	}
	public LocalDate getCreate_at() {
		return create_at;
	}
	public void setCreate_at(LocalDate create_at) {
		this.create_at = create_at;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
}
