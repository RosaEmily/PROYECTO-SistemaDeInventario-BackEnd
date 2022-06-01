package com.IW.STS.API.app.models;
import javax.persistence.*;

@Entity
@Table(name="Proveedor")
public class Proveedor {
	@Id  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(unique=true, nullable=false) 
	private Integer id;
	  
	@Column(nullable=false)  private String nombre;
	@Column(nullable=false)  private String  doi;
	@Column(nullable=true)   private String  email;
	@Column(nullable=false)  private String  tipoDoi;
	@Column(nullable=false)  private String  direccion;
	@Column(nullable=false)  private Boolean  estado=true;
	
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
	
	
}
