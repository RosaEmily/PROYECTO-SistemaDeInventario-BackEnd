package com.IW.STS.API.app.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page) {		
		lis.setRows(ProSer.findByEstado(true));
		fil.setLimit(limit);
		fil.setPage(page);
		lis.setResponseFilter(fil);
		return lis;
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
