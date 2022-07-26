package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.IW.STS.API.app.models.Usuario;

public interface UsuarioServices extends JpaRepository<Usuario,Integer>{
	
	@Query(value="SELECT*FROM Usuario WHERE estado=:estado", nativeQuery=true)
	List<Usuario> ListarUsuario(@Param("estado") boolean estado);
	
	@Query(value="SELECT*FROM Usuario WHERE email=:email AND password=:password", nativeQuery=true)
	Usuario Login(@Param("email") String email,@Param("password") String password);
	
	Page<Usuario> findByEstadoAndNombreStartsWithAndApellidoStartsWith(Boolean estado,String nombre,String apellido,PageRequest pageRequest);	
	
	Usuario findByIdNotInAndEmail(Collection<Integer> id,String email);
	
	Usuario findByEmail(String email);
}
