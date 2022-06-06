package com.IW.STS.API.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.IW.STS.API.app.models.Categoria;

public interface CategoriaServices extends JpaRepository<Categoria,Integer> {
	
	@Query(value="SELECT*FROM Categoria WHERE codigo=:codigo", nativeQuery=true)
	Categoria Verificar1(@Param("codigo") String codigo);
	
	@Query(value="SELECT*FROM Categoria WHERE id NOT IN(:id) AND codigo=:codigo", nativeQuery=true)
	Categoria Verificar2(@Param("id") Integer id,@Param("codigo") String codigo);
	
	@Query(value="SELECT*FROM Categoria WHERE id=:id", nativeQuery=true)
	Categoria GetId(@Param("id") Integer id);
	
	@Query(value="SELECT * FROM Categoria WHERE estado=true;", nativeQuery=true)
	List<Categoria> Listar();
	
	@Modifying
	@Transactional
	@Query("UPDATE Categoria SET nombre=:nombre,codigo=:codigo"
			+ ",descripcion=:descripcion,updated_at=NOW()"
			+ " WHERE id=:id")
	void Editar(@Param("codigo") String codigo,@Param("nombre") String nombre
			,@Param("descripcion") String descripcion);
	
	@Modifying
	@Transactional
	@Query("UPDATE Categoria SET estado=false,deleted_at=NOW() WHERE id=:id")
	void Eliminar(@Param("id") Integer id);

}
