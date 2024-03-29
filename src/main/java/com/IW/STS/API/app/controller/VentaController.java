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

import com.IW.STS.API.app.models.Venta;
import com.IW.STS.API.app.models.DetalleProductoVenta;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Producto;
import com.IW.STS.API.app.services.VentaServices;
import com.IW.STS.API.app.services.ProductoServices;
import com.IW.STS.API.app.services.ClienteServices;
import com.IW.STS.API.app.services.NotaCreditoVentaServices;


@RestController
@RequestMapping("api/venta")
public class VentaController {
	
	@Autowired
	private VentaServices VentServ;
	
	@Autowired
	private ProductoServices ProSer;
	

	@Autowired
	private NotaCreditoVentaServices nccvs;
	
	@Autowired
	private ClienteServices ClieSer;
	
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
	
		lis.setRows(VentServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn(true,serie,correlativo
				,ClieSer.findByNombreStartsWith(proveedor),PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(VentServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn(true,serie,correlativo
				,ClieSer.findByNombreStartsWith(proveedor),PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) VentServ.findByEstadoAndSerieStartsWithAndCorrelativoStartsWithAndClienteIn(true,serie,correlativo
				,ClieSer.findByNombreStartsWith(proveedor),PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);
		
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody() Venta C) {
		if(VentServ.findByCorrelativoAndSerieAndTipodoc(C.getCorrelativo(),C.getSerie(),C.getTipodoc())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			Venta com = new Venta();
			com.setCorrelativo(C.getCorrelativo());
			com.setDescripcion(C.getDescripcion());
			com.setMoneda(C.getMoneda());
			com.setTipo_cambio(C.getTipo_cambio());
			com.setEntidad_direccion(C.getEntidad_direccion());
			com.setSerie(C.getSerie());
			com.setCreated_at(C.getCreated_at());
			com.setCliente(C.getCliente());
			com.setTotal(C.getTotal());
			com.setTipodoc(C.getTipodoc());
			com.setDesctipo(C.getDesctipo());			
			for(int i=0; i<C.getDetalle_producto().size();i++) {
				DetalleProductoVenta dp = new DetalleProductoVenta();
				dp.setCantidad(C.getDetalle_producto().get(i).getCantidad());
				dp.setPrecio(C.getDetalle_producto().get(i).getPrecio());
				dp.setProducto(C.getDetalle_producto().get(i).getProducto());
				
				Producto P = ProSer.getById(C.getDetalle_producto().get(i).getProducto().getId());
				Integer cantidad=P.getStock() - C.getDetalle_producto().get(i).getCantidad();
				P.setStock(cantidad);
				ProSer.save(P);		
				com.addDetalleProducto(dp);
			}
			VentServ.save(com);		
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		if(nccvs.existsByEstadoAndVenta(true, VentServ.findById(id).get())) {
			return ResponseEntity.status(HttpStatus.OK).body("201");
		}else {
			VentServ.findById(id).get().setEstado(false);
			VentServ.findById(id).get().setDeleted_at(LocalDate.now());
			VentServ.save(VentServ.findById(id).get());				
			return ResponseEntity.status(HttpStatus.OK).body("200");
		}
	
	}
	
	@GetMapping("/{id}")
	public Venta Editar(@PathVariable  Integer id) {						
		return VentServ.findById(id).get();
	}
	
	@GetMapping("/list/{doc}")
	public String ListarCompras(@PathVariable  String doc) {						
		return VentServ.SerieCorrelativo(doc);
	}
	
	@GetMapping("/all")
	public List<Venta> All() {						
		return VentServ.findByEstado(true);
	}
	
	@GetMapping("/ListCompra")
	public List<Venta> ListCompras() {						
		return VentServ.ListCompra();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Update(@RequestBody() Venta C,@PathVariable  Integer id) {
		if(nccvs.existsByEstadoAndVenta(true, VentServ.findById(id).get())) {
			return ResponseEntity.status(HttpStatus.OK).body("401");
		}
		Collection<Integer> idCol = Arrays.asList(id);
		if(VentServ.findByIdNotInAndCorrelativoAndSerieAndTipodoc(idCol,C.getCorrelativo(),C.getSerie(),C.getTipodoc())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {			
			Venta com = VentServ.getById(id);
			com.setCorrelativo(C.getCorrelativo());
			com.setDescripcion(C.getDescripcion());
			com.setMoneda(C.getMoneda());
			com.setTipo_cambio(C.getTipo_cambio());
			com.setEntidad_direccion(C.getEntidad_direccion());
			com.setSerie(C.getSerie());
			com.setCreated_at(C.getCreated_at());
			com.setCliente(C.getCliente());
			com.setTotal(C.getTotal());
			com.setTipodoc(C.getTipodoc());
			for(int j=0; j<VentServ.getById(id).getDetalle_producto().size();j++) {							
				Producto P = ProSer.getById(VentServ.getById(id).getDetalle_producto().get(j).getProducto().getId());
				P.setId(VentServ.getById(id).getDetalle_producto().get(j).getProducto().getId());
				Integer cantidad= P.getStock() + VentServ.getById(id).getDetalle_producto().get(j).getCantidad();
				P.setStock(cantidad);
				ProSer.save(P);
			}
			VentServ.EliminarDetalle(id);
			for(int i=0; i<C.getDetalle_producto().size();i++) {
				DetalleProductoVenta dp = new DetalleProductoVenta();
				dp.setCantidad(C.getDetalle_producto().get(i).getCantidad());
				dp.setPrecio(C.getDetalle_producto().get(i).getPrecio());
				dp.setProducto(C.getDetalle_producto().get(i).getProducto());
				
				Producto P = ProSer.getById(C.getDetalle_producto().get(i).getProducto().getId());
				Integer cantidad=P.getStock() - C.getDetalle_producto().get(i).getCantidad();
				P.setStock(cantidad);
				ProSer.save(P);				
				com.addDetalleProducto(dp);
			}
			VentServ.save(com);		
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	

	@GetMapping("/detalle")
	public List<Venta> DetalleProducto() {		
		return VentServ.findByEstado(true);
	}
	
	@GetMapping("/cantidadTOP10")
	public List<Integer> Top10Cantidad() {		
		return VentServ.CantidadTop10();
	}
	
	@GetMapping("/nombresTOP10")
	public List<String> NombresTop10() {		
		return VentServ.NombresTop10();
	}
	
	@GetMapping("/CantidadMes")
	public List<Integer> CantidadMes() {		
		return VentServ.CantidadMes();
	}
	
	@GetMapping("/NombreMes")
	public List<String> NombreMes() {		
		return VentServ.NombreMes();
	}
	
	@GetMapping("/Anio")
	public List<String> Anio() {		
		return VentServ.Anio();
	}
	
	@GetMapping("/MesTotal")
	public List<String> MesTotal() {		
		return VentServ.MesTotal();
	}
	
	@GetMapping("/MesAnio/{anio}")
	public List<String> MesAnio(@PathVariable String anio) {		
		return VentServ.MesAnio(anio);
	}
	
	@GetMapping("/CantidadMesParam/{anio}")
	public List<Integer> CantidadMesParam(@PathVariable String anio) {		
		return VentServ.CantidadMesParam(anio);
	}
	
	@GetMapping("/NombreMesParam/{anio}")
	public List<String> NombreMesParam(@PathVariable String anio) {		
		return VentServ.NombreMesParam(anio);
	}
	
	@GetMapping("/CantidadSemana/{anio}/{mes}")
	public List<Integer> CantidadSemana(@PathVariable String anio,@PathVariable Integer mes) {		
		return VentServ.CantidadSemana(anio,mes);
	}
	
	@GetMapping("/NombreSemana/{anio}/{mes}")
	public List<String> NombreSemana(@PathVariable String anio,@PathVariable Integer mes) {		
		return VentServ.NombreSemana(anio,mes);
	}
	
	@GetMapping("/CantidadSemanaOtro/{mes}")
	public List<Integer> CantidadSemanaOtro(@PathVariable Integer mes) {		
		return VentServ.CantidadSemanaOtro(mes);
	}
	
	@GetMapping("/NombreSemanaOtro/{mes}")
	public List<String> NombreSemanaOtro(@PathVariable Integer mes) {		
		return VentServ.NombreSemanaOtro(mes);
	}
	
	@GetMapping("/CantidadTop10Anio/{anio}")
	public List<Integer> CantidadTop10Anio(@PathVariable String anio) {		
		return VentServ.CantidadTop10Anio(anio);
	}
	
	@GetMapping("/NombresTop10Anio/{anio}")
	public List<String> NombresTop10Anio(@PathVariable String anio) {		
		return VentServ.NombresTop10Anio(anio);
	}
	
	@GetMapping("/CantidadTop10Anio1/{mes}")
	public List<Integer> CantidadTop10Anio1(@PathVariable Integer mes) {		
		return VentServ.CantidadTop10Anio1(mes);
	}
	
	@GetMapping("/NombresTop10Anio1/{mes}")
	public List<String> NombresTop10Anio1(@PathVariable Integer mes) {		
		return VentServ.NombresTop10Anio1(mes);
	}
	
	@GetMapping("/CantidadTop10Anio2/{anio}/{mes}")
	public List<Integer> CantidadTop10Anio2(@PathVariable String anio,@PathVariable Integer mes) {		
		return VentServ.CantidadTop10Anio2(anio,mes);
	}
	
	@GetMapping("/NombresTop10Anio2/{anio}/{mes}")
	public List<String> NombresTop10Anio2(@PathVariable String anio,@PathVariable Integer mes) {		
		return VentServ.NombresTop10Anio2(anio,mes);
	}
	
	@GetMapping("/Top10Proveedor")
	public List<String> Top10Proveedor() {		
		return VentServ.Top10Proveedor();
	}
	
	@GetMapping("/Top10Cliente")
	public List<String> Top10Cliente() {		
		return VentServ.Top10Cliente();
	}
	
	@GetMapping("/Top10ProveedorAnio/{anio}")
	public List<String> Top10ProveedorAnio(@PathVariable String anio) {		
		return VentServ.Top10ProveedorAnio(anio);
	}
	
	@GetMapping("/Top10ClienteAnio/{anio}")
	public List<String> Top10ClienteAnio(@PathVariable String anio) {		
		return VentServ.Top10ClienteAnio(anio);
	}
	
	@GetMapping("/Top10ProveedorMes/{mes}")
	public List<String> Top10ProveedorMes(@PathVariable Integer mes) {		
		return VentServ.Top10ProveedorMes(mes);
	}
	
	@GetMapping("/Top10ClienteMes/{mes}")
	public List<String> Top10ClienteMes(@PathVariable Integer mes) {		
		return VentServ.Top10ClienteMes(mes);
	}
	
	@GetMapping("/Top10ProveedorMesAnio/{anio}/{mes}")
	public List<String> Top10ProveedorMesAnio(@PathVariable String anio,@PathVariable Integer mes) {		
		return VentServ.Top10ProveedorMesAnio(anio,mes);
	}
	
	@GetMapping("/Top10ClienteMesAnio/{anio}/{mes}")
	public List<String> Top10ClienteMesAnio(@PathVariable String anio,@PathVariable Integer mes) {		
		return VentServ.Top10ClienteMesAnio(anio,mes);
	}
	

}
