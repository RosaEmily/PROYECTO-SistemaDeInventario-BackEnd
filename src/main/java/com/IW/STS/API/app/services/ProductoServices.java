package com.IW.STS.API.app.services;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.IW.STS.API.app.models.Producto;

public interface ProductoServices extends JpaRepository<Producto,Integer> {	
	
	Producto findByIdNotInAndCodigo(Collection<Integer> id,String Codigo);
	
	List<Producto> findByEstado(Boolean estado);	
	
	Producto findByCodigo(String codigo);

}
