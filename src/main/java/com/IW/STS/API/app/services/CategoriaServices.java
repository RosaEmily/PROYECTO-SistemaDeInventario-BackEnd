package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


import com.IW.STS.API.app.models.Categoria;

public interface CategoriaServices extends JpaRepository<Categoria,Integer> {
	
	Categoria findByCodigo(String doi);	
	
	List<Categoria> findByEstado(Boolean estado);	
	
	List<Categoria> findByCodigoStartsWith(String nombre);	

	
	Optional<Categoria> findById(Integer id);
	
	Page<Categoria> findByEstadoAndCodigoStartsWithAndNombreStartsWith(Boolean estado,String codigo,String nombre,PageRequest pageRequest);	
	
	Categoria findByIdNotInAndCodigo(Collection<Integer> id,String doi);
	
	
}
