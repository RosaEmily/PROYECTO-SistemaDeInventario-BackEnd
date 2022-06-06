package com.IW.STS.API.app.controller;

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

import com.IW.STS.API.app.models.Categoria;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.services.CategoriaServices;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaServices CatSer;
	private Filtro fil =new  Filtro();
	private ListarFiltro lis = new ListarFiltro();
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page) {		
		lis.setRows(CatSer.Listar());
		fil.setLimit(limit);
		fil.setPage(page);
		lis.setResponseFilter(fil);
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Categoria C) {
		if(CatSer.Verificar1(C.getCodigo())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			CatSer.save(C);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/{id}")
	public Categoria IdInfo(@PathVariable  Integer id) {		
		return CatSer.GetId(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Categoria C,@PathVariable  Integer id) {
		if(CatSer.Verificar2(id,C.getCodigo())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			CatSer.Editar(C.getCodigo(),C.getNombre() ,C.getDescripcion());
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		CatSer.Eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

}
