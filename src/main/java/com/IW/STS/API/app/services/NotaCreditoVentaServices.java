package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IW.STS.API.app.models.Venta;
import com.IW.STS.API.app.models.NotaCreditoVenta;

public interface NotaCreditoVentaServices extends JpaRepository<NotaCreditoVenta,Integer>{
	
	Page<NotaCreditoVenta> findByEstadoAndVentaIn(Boolean estado,Collection<Venta> compra,PageRequest pageRequest);
	
	
	List<NotaCreditoVenta> findByEstadoAndVentaIn(Boolean estado,Collection<Venta> compra);
	
	@Query("SELECT C FROM Venta C WHERE NOT EXISTS (SELECT NCC.venta FROM NotaCreditoVenta NCC WHERE NCC.estado=true AND C=NCC.venta) AND C.estado=True")
	List<Venta> metodoVenta();

}
