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

import com.IW.STS.API.app.models.Categoria;
import com.IW.STS.API.app.models.Cliente;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.services.CategoriaServices;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaServices CatSer;
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	@GetMapping("/lista")
	public List<Categoria> Listar() {		
		return CatSer.findByEstado(true);
	}
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String codigo="",nombre="";
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
				 }else {
					 nombre=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
		lis.setRows(CatSer.findByEstadoAndCodigoStartsWithAndNombreStartsWith(true,codigo,nombre,PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(CatSer.findByEstadoAndCodigoStartsWithAndNombreStartsWith(true,codigo,nombre,PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) CatSer.findByEstadoAndCodigoStartsWithAndNombreStartsWith(true,codigo,nombre,PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);		 
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Categoria C) {

		if(CatSer.findByCodigo(C.getCodigo())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			CatSer.save(C);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Categoria> IdInfo(@PathVariable  Integer id) {		
		return CatSer.findById(id);
	}
	
	@GetMapping("/codigo/{codigo}")
	public Categoria BuscarCodigo(@PathVariable  String codigo) {
		return CatSer.findByCodigo(codigo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Categoria C,@PathVariable  Integer id) {
		Collection<Integer> idCol = Arrays.asList(id);

		if(CatSer.findByIdNotInAndCodigo(idCol,C.getCodigo())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			C.setId(id);			
			C.setUpdated_at(LocalDate.now());
			CatSer.save(C);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		CatSer.findById(id).get().setEstado(false);
		CatSer.findById(id).get().setDeleted_at(LocalDate.now());
		CatSer.save(CatSer.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	
	@GetMapping("/all")
	public List<Categoria> ListarAll() {		
		return CatSer.findAll();
	}

}
