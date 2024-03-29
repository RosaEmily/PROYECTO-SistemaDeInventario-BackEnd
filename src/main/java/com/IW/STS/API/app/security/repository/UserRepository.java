package com.IW.STS.API.app.security.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IW.STS.API.app.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  
  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);  
  
  Page<User> findByEstadoAndNombreStartsWithAndApellidoStartsWithAndUsernameStartsWith
	(Boolean estado,String nombre,String apellido,String username,PageRequest pageRequest);
  
	User findByIdNotInAndEmail(Collection<Long> id,String email);

}
