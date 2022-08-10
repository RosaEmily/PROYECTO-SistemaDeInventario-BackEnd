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
	

	@Query(value="Select COUNT(id_producto) as cantidad \r\n"
			+ "from detalle_producto_venta GROUP BY id_producto\r\n"
			+ "ORDER BY COUNT(id_producto) DESC LIMIT 10", nativeQuery=true)
	List<Integer> CantidadTop10();
	
	@Query(value="Select p.nombre as nombres\r\n"
			+ "from detalle_producto_venta dpv INNER JOIN producto p  \r\n"
			+ "ON dpv.id_producto=p.id_producto GROUP BY p.id_producto\r\n"
			+ "ORDER BY COUNT(p.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<String> NombresTop10();
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD\r\n"
			+ "from detalle_producto_venta dpv INNER JOIN venta v \r\n"
			+ "ON v.id_venta=dpv.id_venta GROUP BY MONTH(v.created_at)\r\n"
			+ "ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<Integer> CantidadMes();
	
	@Query(value="Select  MONTHNAME(v.created_at) AS Mes\r\n"
			+ "from detalle_producto_venta dpv INNER JOIN venta v \r\n"
			+ "ON v.id_venta=dpv.id_venta GROUP BY MONTH(v.created_at)\r\n"
			+ "ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<String> NombreMes();

}
