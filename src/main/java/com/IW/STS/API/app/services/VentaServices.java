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

import com.IW.STS.API.app.models.Venta;
import com.IW.STS.API.app.models.Cliente;

public interface VentaServices extends JpaRepository<Venta,Integer> {
	
	Page<Venta> findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn
	(Boolean estado,String serie,String correlativo,Collection<Cliente> cliente,PageRequest pageRequest);
	
	List<Venta> findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn
	(Boolean estado,String serie,String correlativo,Collection<Cliente> cliente);
	
	Venta findByCorrelativoAndSerie(String correlativo,String serie);
	
	Venta findByIdNotInAndCorrelativoAndSerie(Collection<Integer> id,String correlativo,String serie);
	
	List<Venta> findByEstado(Boolean estado);
	
	@Query(value="SELECT C FROM Compra C ")
	List<Venta> ListCompra();
	
	
	@Modifying
	@Transactional
	@Query(value="Delete from detalle_producto_venta WHERE id_venta=:id", nativeQuery=true)
	void EliminarDetalle(@Param("id") int id);

}
