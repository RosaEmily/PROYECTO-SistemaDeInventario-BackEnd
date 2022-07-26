package com.IW.STS.API.app.controller;



import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.IW.STS.API.app.models.Compra;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Producto;
import com.IW.STS.API.app.models.DetalleProductoCompra;
import com.IW.STS.API.app.services.CompraServices;
import com.IW.STS.API.app.services.ProductoServices;

@RestController
@RequestMapping("api/compra")
public class CompraController {
	
	@Autowired
	private CompraServices ComServ;
	
	@Autowired
	private ProductoServices ProSer;
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String serie="",correlativo="",proveedor="";
		int stock=0;
		 System.out.println(filter);
		 if(!filter.equals("nada")) {
			 String replace0 = filter.replace("\"",""); 
			 String replace1 = replace0.replace("[","");
			 String replace2 = replace1.replace("]","");
			 String replace3 = replace2.replace("{","");
			 String replace4 = replace3.replace("}","");
			 String replace5 = replace4.replace("keyContains:","");
			 String replace6 = replace5.replace("value:",""); 
			 String [] vect = replace6.split(",");			 
			 for(int i=0; i<(vect.length/2);i++) {
				 if(vect[i*2].equals("serie")) {
					 serie=vect[i*2+1];
				 }else if(vect[i*2].equals("correlativo")) {
					 correlativo=vect[i*2+1];
				 }else {
					 proveedor=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
	
		lis.setRows(ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWith(true,serie,correlativo,
				PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWith(true,serie,correlativo,
				PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWith(true,serie,correlativo,
				PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);
		
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody() Compra C) {
		if(ComServ.findByCorrelativoAndSerie(C.getCorrelativo(),C.getSerie())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			Compra com = new Compra();
			com.setCorrelativo(C.getCorrelativo());
			com.setDescripcion(C.getDescripcion());
			com.setMoneda(C.getMoneda());
			com.setTipo_cambio(C.getTipo_cambio());
			com.setEntidad_direccion(C.getEntidad_direccion());
			com.setSerie(C.getSerie());
			com.setCreated_at(C.getCreated_at());
			com.setProveedor(C.getProveedor());
			com.setTotal(C.getTotal());
			
			for(int i=0; i<C.getDetalle_producto().size();i++) {
				DetalleProductoCompra dp = new DetalleProductoCompra();
				dp.setCantidad(C.getDetalle_producto().get(i).getCantidad());
				dp.setPrecio(C.getDetalle_producto().get(i).getPrecio());
				dp.setProducto(C.getDetalle_producto().get(i).getProducto());
				
				Producto P = new Producto();
				P=C.getDetalle_producto().get(i).getProducto();
				Integer cantidad= C.getDetalle_producto().get(i).getProducto().getStock() - C.getDetalle_producto().get(i).getCantidad();
				P.setStock(cantidad);
				ProSer.save(P);
				
				com.addDetalleProducto(dp);
			}
			ComServ.save(com);		
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		ComServ.findById(id).get().setEstado(false);
		ComServ.findById(id).get().setDeleted_at(LocalDate.now());
		ComServ.save(ComServ.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

}
