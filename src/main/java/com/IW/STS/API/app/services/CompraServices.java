package com.IW.STS.API.app.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.IW.STS.API.app.models.Compra;
import com.IW.STS.API.app.models.Proveedor;

public interface CompraServices  extends JpaRepository<Compra,Integer> {
	
	Page<Compra> findByEstadoAndSerieStartsWithAndCorrelativoStartsWith
	(Boolean estado,String serie,String correlativo,PageRequest pageRequest);	
	
	Compra findByCorrelativoAndSerie(String correlativo,String serie);

}
