package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.IW.STS.API.app.models.Rol;

public interface RolServices extends JpaRepository<Rol,Integer>{
	
	
	Page<Rol> findByEstadoAndRolStartsWith(Boolean estado,String rol,PageRequest pageRequest);	
	
	Rol findByIdNotInAndRol(Collection<Integer> id,String rol);
	
	Rol findByRol(String rol);
	
	List<Rol> findByEstado(Boolean estado);


}
