package com.IW.STS.API.app.services;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.IW.STS.API.app.models.Producto;

public interface ProductoServices extends JpaRepository<Producto,Integer> {	
	
	@Query(value="SELECT*FROM Producto WHERE id NOT IN(:id) AND codigo=:codigo", nativeQuery=true)
	Producto Verificar2(@Param("id") Integer id,@Param("codigo") String doi);
	
	List<Producto> findByEstado(Boolean estado);	
	
	Producto findByCodigo(String codigo);

}
