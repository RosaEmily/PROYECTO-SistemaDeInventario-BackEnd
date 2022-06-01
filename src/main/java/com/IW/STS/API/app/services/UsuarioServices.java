package com.IW.STS.API.app.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.IW.STS.API.app.models.Usuario;

public interface UsuarioServices extends JpaRepository<Usuario,Integer>{
	
	@Query(value="SELECT*FROM Usuario WHERE usu_estado=:estado", nativeQuery=true)
	List<Usuario> ListarUsuario(@Param("estado") boolean estado);
	
	@Query(value="SELECT*FROM Usuario WHERE usu_email=:email AND usu_password=:password", nativeQuery=true)
	Usuario Login(@Param("email") String email,@Param("password") String password);
}
