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

import com.IW.STS.API.app.models.Cliente;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.services.ClienteServices;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {
	@Autowired
	private ClienteServices CliSer;
	private Filtro fil =new  Filtro();
	private ListarFiltro lis = new ListarFiltro(); 
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page) {		
		lis.setRows(CliSer.Listar());
		fil.setLimit(limit);
		fil.setPage(page);
		lis.setResponseFilter(fil);
		return lis;
	}
	
	@PostMapping("/")
	public ResponseEntity<String> Guardar(@RequestBody Cliente C) {
		if(CliSer.Verificar(C.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			CliSer.save(C);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/client/{id}")
	public Cliente IdInfo(@PathVariable  Integer id) {		
		return CliSer.GetId(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Cliente C,@PathVariable  Integer id) {
		CliSer.Editar(C.getNombre(),C.getDoi() ,C.getEmail(), C.getTipoDoi(), C.getDireccion(), id,C.getEstado(),C.getApellido());
		return ResponseEntity.status(HttpStatus.CREATED).body("201");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		CliSer.Eliminar(id);
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

}
