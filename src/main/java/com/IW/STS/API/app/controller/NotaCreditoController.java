package com.IW.STS.API.app.controller;

import java.time.LocalDate;
import java.util.List;

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
import com.IW.STS.API.app.models.NotaCreditoCompra;
import com.IW.STS.API.app.models.NotaCreditoVenta;
import com.IW.STS.API.app.models.Venta;
import com.IW.STS.API.app.services.ClienteServices;
import com.IW.STS.API.app.services.CompraServices;
import com.IW.STS.API.app.services.NotaCreditoCompraServices;
import com.IW.STS.API.app.services.NotaCreditoVentaServices;
import com.IW.STS.API.app.services.ProveedorServices;
import com.IW.STS.API.app.services.VentaServices;

@RestController
@RequestMapping("api/notaCredito")
public class NotaCreditoController {
	
	@Autowired
	private CompraServices ComServ;
	
	@Autowired
	private NotaCreditoCompraServices nccs;
	
	@Autowired
	private VentaServices VenServ;
	
	@Autowired
	private NotaCreditoVentaServices nccvs;
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	@Autowired
	private ProveedorServices ProSer;
	
	@Autowired
	private ClienteServices ClitSer;
	
	
	@GetMapping("/compra")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String serie="",correlativo="",doi="",nombre="";
		int stock=0;
		System.out.print(filter);
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
				 if(vect[i*2].equals("compra.serie")) {
					 serie=vect[i*2+1];
				 }else if(vect[i*2].equals("compra.correlativo")) {
					 correlativo=vect[i*2+1];
				 }else if(vect[i*2].equals("compra.proveedor.nombre")) {
					 nombre=vect[i*2+1];
				 }else {
					 doi=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
		
		lis.setRows(nccs.findByEstadoAndCompraIn(true,
				ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn(true,serie,correlativo,
						ProSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true, doi, nombre)),
				PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(nccs.findByEstadoAndCompraIn(true,
				ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn(true,serie,correlativo,
						ProSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true, doi, nombre)),
				PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) nccs.findByEstadoAndCompraIn(true,
				ComServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndProveedorIn(true,serie,correlativo,
						ProSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true, doi, nombre)),
				PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);
		
		return lis;
	}
	

	@GetMapping("/venta")
	public ListarFiltro ListarV(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String serie="",correlativo="",doi="",nombre="";
		int stock=0;
		System.out.print(filter);
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
				 if(vect[i*2].equals("venta.serie")) {
					 serie=vect[i*2+1];
				 }else if(vect[i*2].equals("venta.correlativo")) {
					 correlativo=vect[i*2+1];
				 }else if(vect[i*2].equals("venta.cliente.nombre")) {
					 nombre=vect[i*2+1];
				 }else {
					 doi=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
		
		lis.setRows(nccvs.findByEstadoAndVentaIn(true,
				VenServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn(true,serie,correlativo,
						ClitSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true, doi, nombre)),
				PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(nccvs.findByEstadoAndVentaIn(true,
				VenServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn(true,serie,correlativo,
						ClitSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true, doi, nombre)),
				PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) nccvs.findByEstadoAndVentaIn(true,
				VenServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn(true,serie,correlativo,
						ClitSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true, doi, nombre)),
				PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);
		
		return lis;
	}
	@PostMapping("/compra")
	public ResponseEntity<String> GuardarCompra(@RequestBody() NotaCreditoCompra ncc){
		nccs.save(ncc);
		return ResponseEntity.status(HttpStatus.CREATED).body("201");		
	}
	
	@PostMapping("/venta")
	public ResponseEntity<String> GuardarCompra(@RequestBody() NotaCreditoVenta ncc){
		nccvs.save(ncc);
		return ResponseEntity.status(HttpStatus.CREATED).body("201");		
	}
	
	@GetMapping("/Listcompras")
	public List<Compra> Compras() {						
		return nccs.metodoCompra();
	}
	
	@GetMapping("/Listventas")
	public List<Venta> Ventas() {						
		return nccvs.metodoVenta();
	}
	
	@DeleteMapping("/compra/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		nccs.findById(id).get().setEstado(false);
		nccs.findById(id).get().setDeleted_at(LocalDate.now());
		nccs.save(nccs.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	
	@DeleteMapping("/venta/{id}")
	public ResponseEntity<String> EliminarVenta(@PathVariable  Integer id) {
		nccvs.findById(id).get().setEstado(false);
		nccvs.findById(id).get().setDeleted_at(LocalDate.now());
		nccvs.save(nccvs.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}


}
