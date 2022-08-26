package com.IW.STS.API.app.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="venta")
@OnDelete(action=OnDeleteAction.CASCADE)
public class Venta {
	
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false,name="id_venta") 
	private Integer id;
		
	@Column(nullable=false,name="entidad_direccion")  
	private String entidaddireccion;
	
	@Column(nullable=true) 
	private String  moneda;
	
	@Column(nullable=false)  
	private String  serie;
	
	@Column(nullable=false)  
	private String  correlativo;
	
	@Column(nullable=false)  
	private String  descripcion;
	
	@Column(nullable=false)  private String  tipodoc;
	@Column(nullable=true)  private String  desctipo;
	@Column(nullable=false,name="tipo_cambio")  private Double  tipocambio;
	@Column(nullable=false)  private Double  total;
	@Column(nullable=false)  private Boolean  estado=true;
	@Column(nullable=true)   private LocalDate updated_at;
	@Column(nullable=true)   private LocalDate deleted_at;
	@Column(nullable=true)   private LocalDate created_at=LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name="id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_venta")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private List<DetalleProductoVenta> DetalleProductoVenta;

	
	public Venta() {
		this.DetalleProductoVenta = new ArrayList<DetalleProductoVenta>();
	}	
	
	public String getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(String tipodoc) {
		this.tipodoc = tipodoc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEntidad_direccion() {
		return entidaddireccion;
	}

	public void setEntidad_direccion(String entidad_direccion) {
		this.entidaddireccion = entidad_direccion;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(String correlativo) {
		this.correlativo = correlativo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	

	public Double getTipo_cambio() {
		return tipocambio;
	}

	public void setTipo_cambio(Double tipo_cambio) {
		this.tipocambio = tipo_cambio;
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

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<DetalleProductoVenta> getDetalle_producto() {
		return DetalleProductoVenta;
	}

	public void setDetalle_producto(List<DetalleProductoVenta> DetalleProductoVenta) {
		this.DetalleProductoVenta = DetalleProductoVenta;
	}
	
	public void addDetalleProducto(DetalleProductoVenta item) {
		this.DetalleProductoVenta.add(item);
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDesctipo() {
		return desctipo;
	}

	public void setDesctipo(String desctipo) {
		this.desctipo = desctipo;
	}


}
