package com.IW.STS.API.app.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

import com.IW.STS.API.app.models.Producto;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.services.CategoriaServices;
import com.IW.STS.API.app.services.ProductoServices;

@RestController
@RequestMapping("api/producto")
public class ProductoController {
	
	
	@Autowired
	private ProductoServices ProSer;
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	@Autowired
	private CategoriaServices CatSer;
	
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String codigo="",nombre="",categoria="";
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
				 if(vect[i*2].equals("codigo")) {
					 codigo=vect[i*2+1];
				 }else if(vect[i*2].equals("nombre")) {
					 nombre=vect[i*2+1];
				 }else if(vect[i*2].equals("categoria.nombre")) {
					 categoria=vect[i*2+1];
				 }else {
					 stock=Integer.parseInt(vect[i*2+1]);
				 }
			 }	 		 		 
		 }		
		
		/*lis.setRows(ProSer.findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual(true,codigo,nombre,
				CatSer.findByNombreStartsWith(categoria),stock,PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(ProSer.findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual(true,codigo,nombre,
				CatSer.findByNombreStartsWith(categoria),stock,PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) ProSer.findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual(true,codigo,nombre,
				CatSer.findByNombreStartsWith(categoria),stock,PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);*/
		lis.setRows(ProSer.findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual(true,codigo,nombre,
				CatSer.findByCodigoStartsWith(categoria),stock,PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(ProSer.findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual(true,codigo,nombre,
				CatSer.findByCodigoStartsWith(categoria),stock,PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) ProSer.findByEstadoAndCodigoStartsWithAndNombreStartsWithAndCategoriaInAndStockGreaterThanEqual(true,codigo,nombre,
				CatSer.findByCodigoStartsWith(categoria),stock,PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);
		
		return lis;
	}
	
	@GetMapping("/all")
	public List<Producto> ListarAll() {		
		return ProSer.findAll();
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Producto P) {
		if(ProSer.findByCodigo(P.getCodigo())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			ProSer.save(P);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}				
	}
	
	
	@GetMapping("/{id}")
	public Optional<Producto> IdInfo(@PathVariable  Integer id) {		
		return ProSer.findById(id);		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Producto P,@PathVariable  Integer id) {
		
		Collection<Integer> idCol = Arrays.asList(id);

		if(ProSer.findByIdNotInAndCodigo(idCol,P.getCodigo())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			P.setId(id);			
			P.setUpdated_at(LocalDate.now());
			ProSer.save(P);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		ProSer.findById(id).get().setEstado(false);
		ProSer.findById(id).get().setDeleted_at(LocalDate.now());
		ProSer.save(ProSer.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
}
