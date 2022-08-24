package com.IW.STS.API.app.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  
  @Column(nullable=true)  private LocalDate created_at=LocalDate.now();
  @Column(nullable=true)   private LocalDate updated_at;
  @Column(nullable=true)   private LocalDate deleted_at;
  @Column(nullable=false)  private Boolean estado=true;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable( name = "rol_permiso", 
        joinColumns = @JoinColumn(name = "rol_id"), 
        inverseJoinColumns = @JoinColumn(name = "permiso_id"))
  private List<Permiso> permisos = new ArrayList<Permiso>();

  public Role() {
  }

  public Role(String name) {
    this.name = name;
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

public List<Permiso> getPermisos() {
	return permisos;
}

public void setPermisos(List<Permiso> permisos) {
	this.permisos = permisos;
}


  
}