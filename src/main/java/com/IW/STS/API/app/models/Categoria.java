package com.IW.STS.API.app.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Categoria")
@OnDelete(action=OnDeleteAction.CASCADE)
public class Categoria {
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false) 
	private Integer id;
		
	@Column(nullable=false)  private String nombre;
	@Column(nullable=true)  private String  descripcion;
	@Column(nullable=false)  private String  codigo;
	@Column(nullable=false)  private Boolean  estado=true;
	@Column(nullable=true)   private LocalDate updated_at;
	@Column(nullable=true)   private LocalDate deleted_at;
	@Column(nullable=true)   private LocalDate create_at=LocalDate.now();
	
	
	@OneToMany(mappedBy="categoria")
	private List<Producto> producto;
	
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
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
	public LocalDate getCreate_at() {
		return create_at;
	}
	public void setCreate_at(LocalDate create_at) {
		this.create_at = create_at;
	}
	

}
