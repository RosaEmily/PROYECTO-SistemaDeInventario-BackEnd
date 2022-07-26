package com.IW.STS.API.app.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="producto")
@OnDelete(action=OnDeleteAction.CASCADE)
public class Producto {
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false,name="id_producto") 
	private Integer id;
		
	@Column(nullable=false)  private String nombre;
	@Column(nullable=true)   private String  descripcion;
	@Column(nullable=false)  private String  codigo;
	@Column(nullable=false)  private String  marca;
	@Column(nullable=false)  private Integer  stock;
	@Column(nullable=false)  private String  unidad;
	@Column(nullable=false)  private Double  precio;
	@Column(nullable=false)  private Boolean  estado=true;
	@Column(nullable=true)   private LocalDate updated_at;
	@Column(nullable=true)   private LocalDate deleted_at;
	@Column(nullable=true)   private LocalDate created_at=LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name="id_categoria", referencedColumnName = "id_categoria")
	private Categoria categoria;

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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
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

	public LocalDate  getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(LocalDate deleted_at) {
		this.deleted_at = deleted_at;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

}
