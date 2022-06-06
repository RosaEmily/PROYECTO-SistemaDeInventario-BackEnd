package com.IW.STS.API.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.IW.STS.API.app.models.Cliente;

public interface ClienteServices extends JpaRepository<Cliente,Integer> {
	
	@Query(value="SELECT*FROM Cliente WHERE doi=:doi", nativeQuery=true)
	Cliente Verificar1(@Param("doi") String doi);
	
	@Query(value="SELECT*FROM Cliente WHERE id NOT IN(:id) AND doi=:doi", nativeQuery=true)
	Cliente Verificar2(@Param("id") Integer id,@Param("doi") String doi);
	
	@Query(value="SELECT*FROM Cliente WHERE id=:id", nativeQuery=true)
	Cliente GetId(@Param("id") Integer id);
	
	@Query(value="SELECT doi as doi, CASE\r\n"
			+ " WHEN tipo_doi='02' THEN CONCAT(apellido,', ',nombre)   \r\n"
			+ " ELSE nombre\r\n"
			+ " END as nombre,	\r\n"
			+ "	id as id,create_at as create_at,deleted_at as deleted_at,direccion as direccion,\r\n"
			+ "	email as email,estado as estado,tipo_doi as tipo_doi,updated_at as updated_at ,apellido as apellido\r\n"
			+ "	FROM Cliente WHERE estado=true;", nativeQuery=true)
	List<Cliente> Listar();
	
	@Modifying
	@Transactional
	@Query("UPDATE Cliente SET nombre=:nombre,doi=:doi,email=:email,"
			+ "tipoDoi=:tipoDoi,direccion=:direccion,estado=:estado,updated_at=NOW(),apellido=:apellido"
			+ " WHERE id=:id")
	void Editar(@Param("nombre") String nombre,@Param("doi") String doi,@Param("email") String email
			,@Param("tipoDoi") String tipoDoi,@Param("direccion") String direccion
			,@Param("id") Integer id,@Param("estado") Boolean estado,@Param("apellido") String apellido);
	
	@Modifying
	@Transactional
	@Query("UPDATE Cliente SET estado=false,deleted_at=NOW() WHERE id=:id")
	void Eliminar(@Param("id") Integer id);

}
