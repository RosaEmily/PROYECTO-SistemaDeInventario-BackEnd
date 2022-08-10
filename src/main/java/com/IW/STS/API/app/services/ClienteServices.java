package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IW.STS.API.app.models.Cliente;

public interface ClienteServices extends JpaRepository<Cliente,Integer> {
	
	Cliente findByDoi(String doi);	
	
	Optional<Cliente> findById(Integer id);
	
	Page<Cliente> findByEstadoAndDoiStartsWithAndNombreStartsWith(Boolean estado,String doi,String nombre,PageRequest pageRequest);	
	
	List<Cliente> findByEstadoAndDoiStartsWithAndNombreStartsWith(Boolean estado,String doi,String nombre);	

	
	Cliente findByIdNotInAndDoi(Collection<Integer> id,String doi);
	
	List<Cliente> findByNombreStartsWith(String nombre);
	
	@Query(value="SELECT doi as doi, CASE\r\n"
			+ " WHEN tipo_doi='02' THEN CONCAT(apellido,', ',nombre)   \r\n"
			+ " ELSE nombre\r\n"
			+ " END as nombre,	\r\n"
			+ "	id as id,create_at as create_at,deleted_at as deleted_at,direccion as direccion,\r\n"
			+ "	email as email,estado as estado,tipo_doi as tipo_doi,updated_at as updated_at ,apellido as apellido\r\n"
			+ "	FROM Cliente WHERE estado=true ;", nativeQuery=true)
	Page<Cliente> Listar(PageRequest page2Request);
	

	

}
