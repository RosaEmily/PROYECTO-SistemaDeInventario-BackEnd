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

import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Proveedor;
import com.IW.STS.API.app.services.ProveedorServices;

@RestController
@RequestMapping("api/proveedor")
public class ProveedorController {
	
	@Autowired
	private ProveedorServices ProSer;
	private Filtro fil =new  Filtro();
	private ListarFiltro lis = new ListarFiltro();
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page) {		
		lis.setRows(ProSer.Listar());
		fil.setLimit(limit);
		fil.setPage(page);
		lis.setResponseFilter(fil);
		return lis;
	}
	
	@PostMapping("/")
	public ResponseEntity<String> Guardar(@RequestBody Proveedor P) {
		if(ProSer.Verificar1(P.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			ProSer.save(P);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/supplier/{id}")
	public Proveedor IdInfo(@PathVariable  Integer id) {		
		return ProSer.GetId(id);
	}
	
	@PutMapping("/editar")
	public ResponseEntity<String> Editar(@RequestBody Proveedor P) {
		if(ProSer.Verificar2(P.getId(),P.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			ProSer.Editar(P.getNombre(),P.getDoi() ,P.getEmail(), P.getTipoDoi(), P.getDireccion(), P.getId(),P.getEstado());
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		ProSer.Eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	

}
