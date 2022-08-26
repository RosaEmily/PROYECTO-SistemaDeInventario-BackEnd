package com.IW.STS.API.app.services;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.IW.STS.API.app.models.Compra;
import com.IW.STS.API.app.models.NotaCreditoCompra;

public interface NotaCreditoCompraServices extends JpaRepository<NotaCreditoCompra,Integer>{
	
	Page<NotaCreditoCompra> findByEstadoAndCompraIn(Boolean estado,Collection<Compra> compra,PageRequest pageRequest);
	
	
	Boolean existsByEstadoAndCompra(Boolean estado,Compra compra);
	
	@Query("SELECT C FROM Compra C WHERE NOT EXISTS (SELECT NCC.compra FROM NotaCreditoCompra NCC WHERE NCC.estado=true AND C=NCC.compra) AND C.estado=True")
	List<Compra> metodoCompra();
}
