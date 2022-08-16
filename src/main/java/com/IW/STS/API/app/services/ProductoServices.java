package com.IW.STS.API.app.services;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IW.STS.API.app.models.Categoria;
import com.IW.STS.API.app.models.Producto;

public interface ProductoServices extends JpaRepository<Producto,Integer> {	
	
	Producto findByIdNotInAndCodigo(Collection<Integer> id,String Codigo);
	
	List<Producto> findByEstado(Boolean estado);
	
	Page<Producto> findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual
	(Boolean estado,String codigo,String nombre,Collection<Categoria> categoria,Integer stock,PageRequest pageRequest);
	
	Page<Producto> findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockLessThanEqual
	(Boolean estado,String codigo,String nombre,Collection<Categoria> categoria,Integer stock,PageRequest pageRequest);	
	
	Producto findByCodigo(String codigo);
	
	@Query(value="SELECT  p.nombre as nombre ,SUM(dpv.cantidad) as cantidades, AVG(dpv.precio*v.tipo_cambio) as precios,\r\n"
			+ "SUM(dpv.cantidad)*AVG(dpv.precio*v.tipo_cambio) as totales\r\n"
			+ "FROM venta v INNER JOIN detalle_producto_venta dpv ON v.id_venta=dpv.id_venta\r\n"
			+ "INNER JOIN	producto p ON p.id_producto=dpv.id_producto\r\n"
			+ "GROUP BY dpv.id_producto\r\n"
			+ "ORDER BY totales DESC", nativeQuery=true)
	List<String> productoABC();

}
