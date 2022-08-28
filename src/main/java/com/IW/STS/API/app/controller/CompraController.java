package com.IW.STS.API.app.controller;



import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.IW.STS.API.app.services.NotaCreditoCompraServices;
import com.IW.STS.API.app.services.ProductoServices;
import com.IW.STS.API.app.services.ProveedorServices;


@RestController
@RequestMapping("api/compra")
public class CompraController {
	
	@Autowired
	private CompraServices ComServ;
	
	@Autowired
	private ProductoServices ProSer;
	
	@Autowired
	private ProveedorServices ProvSer;
	
	@Autowired
	private NotaCreditoCompraServices nccs;	
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String serie="",correlativo="",proveedor="";
		int stock=0;
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
	
		lis.setRows(ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn(true,serie,correlativo
				,ProvSer.findByNombreStartsWith(proveedor),PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn(true,serie,correlativo
				,ProvSer.findByNombreStartsWith(proveedor),PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn(true,serie,correlativo
				,ProvSer.findByNombreStartsWith(proveedor),PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);
		
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody() Compra C) {
		if(ComServ.findByCorrelativoAndSerieAndTipodoc(C.getCorrelativo(),C.getSerie(),C.getTipodoc())!=null) {
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
			com.setTipodoc(C.getTipodoc());
			com.setDesctipo(C.getDesctipo());
			for(int i=0; i<C.getDetalle_producto().size();i++) {
				DetalleProductoCompra dp = new DetalleProductoCompra();
				dp.setCantidad(C.getDetalle_producto().get(i).getCantidad());
				dp.setPrecio(C.getDetalle_producto().get(i).getPrecio());
				dp.setProducto(C.getDetalle_producto().get(i).getProducto());				
				Producto P = ProSer.getById(C.getDetalle_producto().get(i).getProducto().getId());
				Integer cantidad=P.getStock() + C.getDetalle_producto().get(i).getCantidad();
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
		if(nccs.existsByEstadoAndCompra(true, ComServ.findById(id).get())) {
			return ResponseEntity.status(HttpStatus.OK).body("201");
		}else {
			ComServ.findById(id).get().setEstado(false);
			ComServ.findById(id).get().setDeleted_at(LocalDate.now());
			ComServ.save(ComServ.findById(id).get());
			return ResponseEntity.status(HttpStatus.OK).body("200");
		}
	}
	
	@GetMapping("/{id}")
	public Compra Editar(@PathVariable  Integer id) {						
		return ComServ.findById(id).get();
	}
	
	@GetMapping("/all")
	public List<Compra> All() {						
		return ComServ.findByEstado(true);
	}
	
	@GetMapping("/ListCompra")
	public List<Compra> ListCompras() {						
		return ComServ.ListCompra();
	}
	
	@GetMapping("/list/{doc}")
	public String ListarCompras(@PathVariable  String doc) {						
		return ComServ.SerieCorrelativo(doc);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Update(@RequestBody() Compra C,@PathVariable  Integer id) {
		Collection<Integer> idCol = Arrays.asList(id);
		if(nccs.existsByEstadoAndCompra(true, ComServ.findById(id).get())) {
			return ResponseEntity.status(HttpStatus.OK).body("401");
		}
		if(ComServ.findByIdNotInAndCorrelativoAndSerieAndTipodoc(idCol,C.getCorrelativo(),C.getSerie(),C.getTipodoc())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {			
			Compra com = ComServ.getById(id);
			com.setCorrelativo(C.getCorrelativo());
			com.setDescripcion(C.getDescripcion());
			com.setMoneda(C.getMoneda());
			com.setTipo_cambio(C.getTipo_cambio());
			com.setEntidad_direccion(C.getEntidad_direccion());
			com.setSerie(C.getSerie());
			com.setCreated_at(C.getCreated_at());
			com.setProveedor(C.getProveedor());
			com.setTotal(C.getTotal());
			com.setTipodoc(C.getTipodoc());
			for(int j=0; j<ComServ.getById(id).getDetalle_producto().size();j++) {							
				Producto P = ProSer.getById(ComServ.getById(id).getDetalle_producto().get(j).getProducto().getId());
				P.setId(ComServ.getById(id).getDetalle_producto().get(j).getProducto().getId());
				Integer cantidad= P.getStock() - ComServ.getById(id).getDetalle_producto().get(j).getCantidad();
				P.setStock(cantidad);
				ProSer.save(P);
			}
			ComServ.EliminarDetalle(id);
			for(int i=0; i<C.getDetalle_producto().size();i++) {
				DetalleProductoCompra dp = new DetalleProductoCompra();
				dp.setCantidad(C.getDetalle_producto().get(i).getCantidad());
				dp.setPrecio(C.getDetalle_producto().get(i).getPrecio());
				dp.setProducto(C.getDetalle_producto().get(i).getProducto());				
				Producto P = ProSer.getById(C.getDetalle_producto().get(i).getProducto().getId());
				Integer cantidad=P.getStock() + C.getDetalle_producto().get(i).getCantidad();
				P.setStock(cantidad);
				ProSer.save(P);				
				com.addDetalleProducto(dp);
			}
			ComServ.save(com);		
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@GetMapping("/detalle")
	public List<Compra> DetalleProducto() {		
		return ComServ.findByEstado(true);
	}
	
	@GetMapping("/Egresos")
	public Double Egresos() {
		if(ComServ.Egresos()==null) {
			return 0.00;
		}else {
			return ComServ.Egresos();
		}
	}
	
	@GetMapping("/Ingresos")
	public Double Ingresos() {		
		if(ComServ.Ingresos()==null) {
			return 0.00;
		}else {
			return ComServ.Ingresos();
		}
	}
	
	@GetMapping("/EgresosAnio")
	public List<String> EgresosAnio() {		
		return ComServ.EgresosAnio();
	}
	
	@GetMapping("/IngresosAnio")
	public List<String> IngresosAnio() {		
		return ComServ.IngresosAnio();
	}
	
	@GetMapping("/EgresosProveedor")
	public List<String> EgresosProveedor() {		
		return ComServ.EgresosProveedor();
	}
	
	@GetMapping("/IngresosCliente")
	public List<String> IngresosCliente() {		
		return ComServ.IngresosCliente();
	}

}
