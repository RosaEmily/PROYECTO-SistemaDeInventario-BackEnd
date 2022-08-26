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
	
	Venta findByCorrelativoAndSerieAndTipodoc(String correlativo,String serie,String tipodoc);
	
	Venta findByIdNotInAndCorrelativoAndSerieAndTipodoc(Collection<Integer> id,String correlativo,String serie,String tipodoc);
	
	List<Venta> findByEstado(Boolean estado);
	
	@Query(value="SELECT serie,correlativo FROM venta WHERE tipodoc=:doc\r\n"
			+ "ORDER BY id_venta DESC\r\n"
			+ "LIMIT 1", nativeQuery=true)
	String SerieCorrelativo(String doc);
	
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
	
	@Query(value="Select  CASE WHEN MONTH(v.created_at) = 1 THEN \"ENERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 2 THEN \"FEBRERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 3 THEN \"MARZO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 4 THEN \"ABRIL\"\r\n"
			+ "WHEN MONTH(v.created_at) = 5 THEN \"MAYO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 6 THEN \"JUNIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 7 THEN \"JULIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 8 THEN \"AGOSTO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 9 THEN \"SEPTIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 10 THEN \"OCTUBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 11 THEN \"NOVIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 12 THEN \"DICIEMBRE\"\r\n"
			+ "ELSE \"NO ES UN MES\" END AS MES\r\n"
			+ "			from detalle_producto_venta dpv INNER JOIN venta v\r\n"
			+ "			ON v.id_venta=dpv.id_venta GROUP BY MONTH(v.created_at)\r\n"
			+ "			ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<String> NombreMes();
	
	
	@Query(value="SELECT MONTH(v.created_at) as num,CASE WHEN MONTH(v.created_at) = 1 THEN \"ENERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 2 THEN \"FEBRERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 3 THEN \"MARZO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 4 THEN \"ABRIL\"\r\n"
			+ "WHEN MONTH(v.created_at) = 5 THEN \"MAYO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 6 THEN \"JUNIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 7 THEN \"JULIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 8 THEN \"AGOSTO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 9 THEN \"SEPTIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 10 THEN \"OCTUBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 11 THEN \"NOVIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 12 THEN \"DICIEMBRE\"\r\n"
			+ "ELSE \"NO ES UN MES\" END AS mes FROM  detalle_producto_venta dpv INNER JOIN venta v \r\n"
			+ "			ON v.id_venta=dpv.id_venta GROUP BY mes ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<String> MesTotal();
	
	
	@Query(value="SELECT MONTH(v.created_at) as num,CASE WHEN MONTH(v.created_at) = 1 THEN \"ENERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 2 THEN \"FEBRERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 3 THEN \"MARZO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 4 THEN \"ABRIL\"\r\n"
			+ "WHEN MONTH(v.created_at) = 5 THEN \"MAYO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 6 THEN \"JUNIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 7 THEN \"JULIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 8 THEN \"AGOSTO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 9 THEN \"SEPTIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 10 THEN \"OCTUBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 11 THEN \"NOVIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 12 THEN \"DICIEMBRE\"\r\n"
			+ "ELSE \"NO ES UN MES\" END AS mes FROM  detalle_producto_venta dpv INNER JOIN venta v \r\n"
			+ "			ON v.id_venta=dpv.id_venta  WHERE YEAR(v.created_at)=:anio GROUP BY mes ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<String> MesAnio(@Param("anio") String anio);
	
	
	@Query(value="SELECT YEAR(v.created_at) as anio FROM  detalle_producto_venta dpv INNER JOIN venta v \r\n"
			+ "			ON v.id_venta=dpv.id_venta GROUP BY anio ORDER BY anio DESC", nativeQuery=true)
	List<String> Anio();
	
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD\r\n"
			+ "from detalle_producto_venta dpv INNER JOIN venta v \r\n"
			+ "ON v.id_venta=dpv.id_venta WHERE YEAR(v.created_at)=:anio GROUP BY MONTH(v.created_at)\r\n"
			+ "ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<Integer> CantidadMesParam(@Param("anio") String anio);
	
	@Query(value="Select  CASE WHEN MONTH(v.created_at) = 1 THEN \"ENERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 2 THEN \"FEBRERO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 3 THEN \"MARZO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 4 THEN \"ABRIL\"\r\n"
			+ "WHEN MONTH(v.created_at) = 5 THEN \"MAYO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 6 THEN \"JUNIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 7 THEN \"JULIO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 8 THEN \"AGOSTO\"\r\n"
			+ "WHEN MONTH(v.created_at) = 9 THEN \"SEPTIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 10 THEN \"OCTUBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 11 THEN \"NOVIEMBRE\"\r\n"
			+ "WHEN MONTH(v.created_at) = 12 THEN \"DICIEMBRE\"\r\n"
			+ "ELSE \"NO ES UN MES\" END AS MES\r\n"
			+ "			from detalle_producto_venta dpv INNER JOIN venta v\r\n"
			+ "			ON v.id_venta=dpv.id_venta WHERE YEAR(v.created_at)=:anio GROUP BY MONTH(v.created_at)\r\n"
			+ "			ORDER BY MONTH(v.created_at)", nativeQuery=true)
	List<String> NombreMesParam(@Param("anio") String anio);
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD\r\n"
			+ "			from detalle_producto_venta dpv INNER JOIN venta v\r\n"
			+ "			ON v.id_venta=dpv.id_venta WHERE YEAR(v.created_at)=:anio AND MONTH(v.created_at)=:mes GROUP BY (WEEK(v.created_at, 5) - WEEK(DATE_SUB(v.created_at, INTERVAL DAYOFMONTH(v.created_at) - 1 DAY), 5) + 1) ORDER BY (WEEK(v.created_at, 5) - WEEK(DATE_SUB(v.created_at, INTERVAL DAYOFMONTH(v.created_at) - 1 DAY), 5) + 1)", nativeQuery=true)
	List<Integer> CantidadSemana(@Param("anio") String anio,@Param("mes") Integer mes);
	
	@Query(value="Select CONCAT(\"SEMANA \" ,(WEEK(v.created_at, 5) - WEEK(DATE_SUB(v.created_at, INTERVAL DAYOFMONTH(v.created_at) - 1 DAY), 5) + 1)) AS SEMANA\r\n"
			+ "			from detalle_producto_venta dpv INNER JOIN venta v\r\n"
			+ "			ON v.id_venta=dpv.id_venta WHERE YEAR(v.created_at)=:anio AND MONTH(v.created_at)=:mes GROUP BY SEMANA ORDER BY SEMANA", nativeQuery=true)
	List<String> NombreSemana(@Param("anio") String anio,@Param("mes") Integer mes);
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD\r\n"
			+ "			from detalle_producto_venta dpv INNER JOIN venta v\r\n"
			+ "			ON v.id_venta=dpv.id_venta WHERE MONTH(v.created_at)=:mes GROUP BY (WEEK(v.created_at, 5) - WEEK(DATE_SUB(v.created_at, INTERVAL DAYOFMONTH(v.created_at) - 1 DAY), 5) + 1) ORDER BY (WEEK(v.created_at, 5) - WEEK(DATE_SUB(v.created_at, INTERVAL DAYOFMONTH(v.created_at) - 1 DAY), 5) + 1)", nativeQuery=true)
	List<Integer> CantidadSemanaOtro(@Param("mes") Integer mes);
	
	@Query(value="Select CONCAT(\"SEMANA \" ,(WEEK(v.created_at, 5) - WEEK(DATE_SUB(v.created_at, INTERVAL DAYOFMONTH(v.created_at) - 1 DAY), 5) + 1)) AS SEMANA\r\n"
			+ "			from detalle_producto_venta dpv INNER JOIN venta v\r\n"
			+ "			ON v.id_venta=dpv.id_venta WHERE MONTH(v.created_at)=:mes GROUP BY SEMANA ORDER BY SEMANA", nativeQuery=true)
	List<String> NombreSemanaOtro(@Param("mes") Integer mes);
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD from detalle_producto_venta dpv INNER JOIN venta v ON v.id_venta=dpv.id_venta INNER JOIN producto p ON dpv.id_producto=p.id_producto\r\n"
			+ "	WHERE YEAR(v.created_at)=:anio GROUP BY dpv.id_producto\r\n"
			+ "			ORDER BY COUNT(dpv.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<Integer> CantidadTop10Anio(@Param("anio") String anio);
	
	@Query(value="Select p.nombre as nombres from detalle_producto_venta dpv INNER JOIN venta v ON v.id_venta=dpv.id_venta INNER JOIN producto p ON dpv.id_producto=p.id_producto\r\n"
			+ "	WHERE YEAR(v.created_at)=:anio GROUP BY dpv.id_producto\r\n"
			+ "			ORDER BY COUNT(dpv.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<String> NombresTop10Anio(@Param("anio") String anio);
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD from detalle_producto_venta dpv INNER JOIN venta v ON v.id_venta=dpv.id_venta INNER JOIN producto p ON dpv.id_producto=p.id_producto\r\n"
			+ "	WHERE MONTH(v.created_at)=:mes GROUP BY dpv.id_producto\r\n"
			+ "			ORDER BY COUNT(dpv.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<Integer> CantidadTop10Anio1(@Param("mes") Integer mes);
	
	@Query(value="Select p.nombre as nombres from detalle_producto_venta dpv INNER JOIN venta v ON v.id_venta=dpv.id_venta INNER JOIN producto p ON dpv.id_producto=p.id_producto\r\n"
			+ "	WHERE MONTH(v.created_at)=:mes GROUP BY dpv.id_producto\r\n"
			+ "			ORDER BY COUNT(dpv.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<String> NombresTop10Anio1(@Param("mes") Integer mes);
	
	@Query(value="Select COUNT(dpv.id_producto) AS CANTIDAD from detalle_producto_venta dpv INNER JOIN venta v ON v.id_venta=dpv.id_venta INNER JOIN producto p ON dpv.id_producto=p.id_producto\r\n"
			+ "	WHERE YEAR(v.created_at)=:anio AND MONTH(v.created_at)=:mes GROUP BY dpv.id_producto\r\n"
			+ "			ORDER BY COUNT(dpv.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<Integer> CantidadTop10Anio2(@Param("anio") String anio,@Param("mes") Integer mes);
	
	@Query(value="Select p.nombre as nombres from detalle_producto_venta dpv INNER JOIN venta v ON v.id_venta=dpv.id_venta INNER JOIN producto p ON dpv.id_producto=p.id_producto\r\n"
			+ "	WHERE YEAR(v.created_at)=:anio AND MONTH(v.created_at)=:mes GROUP BY dpv.id_producto\r\n"
			+ "			ORDER BY COUNT(dpv.id_producto) DESC LIMIT 10", nativeQuery=true)
	List<String> NombresTop10Anio2(@Param("anio") String anio,@Param("mes") Integer mes);
	
	@Query(value="SELECT p.doi as doi, COUNT(p.id_proveedor) as cantidad FROM compra c \r\n"
			+ "INNER JOIN proveedor p \r\n"
			+ "ON c.id_proveedor=p.id_proveedor\r\n"
			+ "GROUP BY p.id_proveedor\r\n"
			+ "ORDER BY p.id_proveedor\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10Proveedor();
	
	@Query(value="SELECT c.doi as doi, COUNT(c.id_cliente) as cantidad FROM venta v\r\n"
			+ "INNER JOIN cliente c \r\n"
			+ "ON v.id_cliente=c.id_cliente\r\n"
			+ "GROUP BY c.id_cliente\r\n"
			+ "ORDER BY c.id_cliente\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10Cliente();
	
	@Query(value="SELECT p.doi as doi, COUNT(p.id_proveedor) as cantidad FROM compra c \r\n"
			+ "INNER JOIN proveedor p \r\n"
			+ "ON c.id_proveedor=p.id_proveedor\r\n"
			+ "WHERE YEAR(c.created_at)=:anio GROUP BY p.id_proveedor\r\n"
			+ "ORDER BY p.id_proveedor\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10ProveedorAnio(@Param("anio") String anio);
	
	@Query(value="SELECT c.doi as doi, COUNT(c.id_cliente) as cantidad FROM venta v\r\n"
			+ "INNER JOIN cliente c \r\n"
			+ "ON v.id_cliente=c.id_cliente\r\n"
			+ "WHERE YEAR(v.created_at)=:anio GROUP BY c.id_cliente\r\n"
			+ "ORDER BY c.id_cliente\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10ClienteAnio(@Param("anio") String anio);
	
	@Query(value="SELECT p.doi as doi, COUNT(p.id_proveedor) as cantidad FROM compra c \r\n"
			+ "INNER JOIN proveedor p \r\n"
			+ "ON c.id_proveedor=p.id_proveedor\r\n"
			+ "WHERE MONTH(c.created_at)=:mes GROUP BY p.id_proveedor\r\n"
			+ "ORDER BY p.id_proveedor\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10ProveedorMes(@Param("mes") Integer mes);
	
	@Query(value="SELECT c.doi as doi, COUNT(c.id_cliente) as cantidad FROM venta v\r\n"
			+ "INNER JOIN cliente c \r\n"
			+ "ON v.id_cliente=c.id_cliente\r\n"
			+ "WHERE MONTH(v.created_at)=:mes GROUP BY c.id_cliente\r\n"
			+ "ORDER BY c.id_cliente\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10ClienteMes(@Param("mes") Integer mes);
	
	@Query(value="SELECT p.doi as doi, COUNT(p.id_proveedor) as cantidad FROM compra c \r\n"
			+ "INNER JOIN proveedor p \r\n"
			+ "ON c.id_proveedor=p.id_proveedor\r\n"
			+ "WHERE YEAR(v.created_at)=:anio AND MONTH(c.created_at)=:mes GROUP BY p.id_proveedor\r\n"
			+ "ORDER BY p.id_proveedor\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10ProveedorMesAnio(@Param("anio") String anio,@Param("mes") Integer mes);
	
	@Query(value="SELECT c.doi as doi, COUNT(c.id_cliente) as cantidad FROM venta v\r\n"
			+ "INNER JOIN cliente c \r\n"
			+ "ON v.id_cliente=c.id_cliente\r\n"
			+ "WHERE  YEAR(v.created_at)=:anio AND MONTH(v.created_at)=:mes GROUP BY c.id_cliente\r\n"
			+ "ORDER BY c.id_cliente\r\n"
			+ "LIMIT 10", nativeQuery=true)
	List<String> Top10ClienteMesAnio(@Param("anio") String anio,@Param("mes") Integer mes);
	
	
	
	
	

}
