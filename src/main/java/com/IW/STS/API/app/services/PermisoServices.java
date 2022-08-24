package com.IW.STS.API.app.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IW.STS.API.app.models.Permiso;

public interface PermisoServices extends JpaRepository<Permiso,Integer>{
	
	Boolean existsByPermiso(String permiso);

}
