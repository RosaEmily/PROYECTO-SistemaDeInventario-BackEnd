package com.IW.STS.API.app.services;


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.IW.STS.API.app.models.Compra;
import com.IW.STS.API.app.models.Proveedor;

public interface CompraServices  extends JpaRepository<Compra,Integer> {
	
	Page<Compra> findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn
	(Boolean estado,String serie,String correlativo,Collection<Proveedor> proveedor,PageRequest pageRequest);
	
	List<Compra> findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn
	(Boolean estado,String serie,String correlativo,Collection<Proveedor> proveedor);
	
	Compra findByCorrelativoAndSerie(String correlativo,String serie);
	
	Compra findByIdNotInAndCorrelativoAndSerie(Collection<Integer> id,String correlativo,String serie);
	
	List<Compra> findByEstado(Boolean estado);
	
	@Query(value="SELECT C FROM Compra C ")
	List<Compra> ListCompra();
	
	
	@Modifying
	@Transactional
	@Query(value="Delete from detalle_producto_compra WHERE id_compra=:id", nativeQuery=true)
	void EliminarDetalle(@Param("id") int id);

}
