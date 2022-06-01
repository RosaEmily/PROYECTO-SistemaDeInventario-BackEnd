package com.IW.STS.API.app.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.IW.STS.API.app.models.Proveedor;

public interface ProveedorServices extends JpaRepository<Proveedor,Integer>{
	@Query(value="SELECT*FROM Proveedor WHERE doi=:doi", nativeQuery=true)
	Proveedor Verificar(@Param("doi") String doi);
	
	@Query(value="SELECT*FROM Proveedor WHERE id=:id", nativeQuery=true)
	Proveedor GetId(@Param("id") Integer id);
	
	@Query(value="SELECT*FROM Proveedor WHERE estado=true", nativeQuery=true)
	List<Proveedor> Listar();
	
	@Query(value="UPDATE Proveedor SET nombre=:nombre,doi=:doi,email=:email"
			+ "tipoDoi=:tipoDoi,direccion=:direccion WHERE id=:id", nativeQuery=true)
	Proveedor Editar(@Param("nombre") String nombre,@Param("doi") String doi,@Param("email") String email
			,@Param("tipoDoi") String tipoDoi,@Param("direccion") String direccion
			,@Param("id") Integer id);
	
	@Query(value="UPDATE Proveedor SET estado=false WHERE id=:id", nativeQuery=true)
	Proveedor Eliminar(@Param("id") Integer id);
}
