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
	
	Compra findByCorrelativoAndSerieAndTipodoc(String correlativo,String serie,String tipodoc);
	
	Compra findByIdNotInAndCorrelativoAndSerieAndTipodoc(Collection<Integer> id,String correlativo,String serie,String tipodoc);
	
	List<Compra> findByEstado(Boolean estado);
	
	@Query(value="SELECT C FROM Compra C ")
	List<Compra> ListCompra();
	
	@Query(value="SELECT serie,correlativo FROM compra WHERE tipodoc=:doc\r\n"
			+ "ORDER BY id_compra DESC\r\n"
			+ "LIMIT 1", nativeQuery=true)
	String SerieCorrelativo(String doc);
	
	@Modifying
	@Transactional
	@Query(value="Delete from detalle_producto_compra WHERE id_compra=:id", nativeQuery=true)
	void EliminarDetalle(@Param("id") int id);
	
	@Query(value="SELECT SUM(dpc.cantidad*dpc.precio*c.tipo_cambio) as total FROM compra c inner JOIN detalle_producto_compra dpc ON c.id_compra=dpc.id_compra WHERE c.estado=true", nativeQuery=true)
	Double Egresos();
	
	@Query(value="SELECT SUM(dpv.cantidad*dpv.precio*v.tipo_cambio) as total FROM venta v inner JOIN detalle_producto_venta dpv ON v.id_venta=dpv.id_venta WHERE v.estado=true", nativeQuery=true)
	Double Ingresos();
	
	@Query(value="SELECT YEAR(v.created_at) as anio,SUM(dpv.cantidad*dpv.precio*v.tipo_cambio) as total FROM venta v inner JOIN detalle_producto_venta dpv ON v.id_venta=dpv.id_venta \r\n"
			+ "WHERE v.estado=true\r\n"
			+ "GROUP BY YEAR(v.created_at)\r\n"
			+ "ORDER BY YEAR(v.created_at)", nativeQuery=true)
	List<String> IngresosAnio();
	
	@Query(value="SELECT YEAR(c.created_at) as anio,SUM(dpc.cantidad*dpc.precio*c.tipo_cambio) as total FROM compra c inner JOIN detalle_producto_compra dpc ON c.id_compra=dpc.id_compra \r\n"
			+ "WHERE c.estado=true\r\n"
			+ "GROUP BY YEAR(c.created_at)\r\n"
			+ "ORDER BY YEAR(c.created_at)", nativeQuery=true)
	List<String> EgresosAnio();
	
	@Query(value="SELECT c.doi as doi,SUM(dpv.cantidad*dpv.precio*v.tipo_cambio) as total FROM venta v inner JOIN detalle_producto_venta dpv ON v.id_venta=dpv.id_venta \r\n"
			+ "INNER JOIN cliente c ON c.id_cliente=v.id_cliente\r\n"
			+ "WHERE v.estado=true\r\n"
			+ "GROUP BY c.doi\r\n"
			+ "ORDER BY YEAR(c.created_at),total DESC\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> IngresosCliente();
	
	@Query(value="SELECT p.doi as doi,SUM(dpc.cantidad*dpc.precio*c.tipo_cambio) as total FROM compra c inner JOIN detalle_producto_compra dpc ON c.id_compra=dpc.id_compra INNER JOIN proveedor p ON p.id_proveedor=c.id_proveedor\r\n"
			+ "WHERE c.estado=true\r\n"
			+ "GROUP BY p.doi\r\n"
			+ "ORDER BY YEAR(c.created_at),total DESC\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> EgresosProveedor();



}
