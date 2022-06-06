package com.IW.STS.API.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.IW.STS.API.app.models.Proveedor;

public interface ProveedorServices extends JpaRepository<Proveedor,Integer>{
	@Query(value="SELECT*FROM Proveedor WHERE doi=:doi", nativeQuery=true)
	Proveedor Verificar1(@Param("doi") String doi);
	
	@Query(value="SELECT*FROM Proveedor WHERE id NOT IN(:id) AND doi=:doi", nativeQuery=true)
	Proveedor Verificar2(@Param("id") Integer id,@Param("doi") String doi);
	
	@Query(value="SELECT*FROM Proveedor WHERE id=:id", nativeQuery=true)
	Proveedor GetId(@Param("id") Integer id);
	
	@Query(value="SELECT*FROM Proveedor WHERE estado=true", nativeQuery=true)
	List<Proveedor> Listar();
	
	@Modifying
	@Transactional
	@Query("UPDATE Proveedor SET nombre=:nombre,doi=:doi,email=:email,"
			+ "tipoDoi=:tipoDoi,direccion=:direccion,estado=:estado,updated_at=NOW() WHERE id=:id")
	void Editar(@Param("nombre") String nombre,@Param("doi") String doi,@Param("email") String email
			,@Param("tipoDoi") String tipoDoi,@Param("direccion") String direccion
			,@Param("id") Integer id,@Param("estado") Boolean estado);
	
	@Modifying
	@Transactional
	@Query("UPDATE Proveedor SET estado=false,deleted_at=NOW() WHERE id=:id")
	void Eliminar(@Param("id") Integer id);
}
