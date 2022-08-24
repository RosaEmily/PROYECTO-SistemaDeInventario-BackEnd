package com.IW.STS.API.app.security.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.IW.STS.API.app.models.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	  
    Page<Role> findByEstadoAndNameStartsWith(Boolean estado,String name,PageRequest pageRequest);	
	
	List<Role> findByNameStartsWith(String name);	

	Role findByIdNotInAndName(Collection<Integer> id,String name);
	
	Role findByName(String name);
	
	@Query(value="SELECT p.permiso as permiso FROM user_roles ur INNER JOIN roles r ON ur.role_id=r.id\n"
			+ "INNER JOIN rol_permiso rp ON rp.rol_id=r.id INNER JOIN permiso p\n"
			+ "ON p.id=rp.permiso_id WHERE ur.user_id=:id GROUP BY p.permiso", nativeQuery=true)
	List<String> Permisos(@Param("id") int id);
	
	List<Role> findByEstado(Boolean estado);
  
}
