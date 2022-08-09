package com.IW.STS.API.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "DetalleProductoVenta")
public class DetalleProductoVenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false,name="id_detalle_venta") 
	private Integer id;
	
	@Column(nullable=false) 
	private Integer cantidad;
	
	@Column(nullable=false) 
	private Double precio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_producto")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Producto producto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
