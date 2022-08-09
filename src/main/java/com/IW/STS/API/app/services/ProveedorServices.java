package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.IW.STS.API.app.models.Proveedor;

public interface ProveedorServices extends JpaRepository<Proveedor,Integer>{
	
	Proveedor findByDoi(String doi);	
	
	Optional<Proveedor> findById(Integer id);
	
	Page<Proveedor> findByEstadoAndDoiStartsWithAndNombreStartsWith(Boolean estado,String doi,String nombre,PageRequest pageRequest);	
	
	List<Proveedor> findByEstadoAndDoiStartsWithAndNombreStartsWith(Boolean estado,String doi,String nombre);
	
	List<Proveedor> findByNombreStartsWith(String nombre);
	
	Proveedor findByIdNotInAndDoi(Collection<Integer> id,String doi);	

	
}
