package com.IW.STS.API.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.IW.STS.API.app.models.Filtracion;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Mensaje;
import com.IW.STS.API.app.services.ClienteServices;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {
	@Autowired
	private ClienteServices CliSer;
	private Filtracion fil =new  Filtracion();
	private ListarFiltro lis = new ListarFiltro(); 
	private Mensaje men =new Mensaje();
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page) {		
		lis.setRows(CliSer.Listar());
		fil.setLimit(limit);
		fil.setPage(page);
		lis.setResponseFilter(fil);
		return lis;
	}
	
	@PostMapping("/")
	public Mensaje Guardar(@RequestBody Cliente C) {
		if(CliSer.Verificar(C.getDoi())!=null) {
			men.setStatus(401);
			men.setMessage("El cliente ingresado ya existe");
		}else {
			CliSer.save(C);
			men.setStatus(200);
			men.setMessage("Cliente Creado");
		}
		return men;	
	}
	
	@GetMapping("/client/{id}")
	public Cliente IdInfo(@PathVariable  Integer id) {		
		return CliSer.GetId(id);
	}
	
	@PutMapping("/{id}")
	public Mensaje Editar(@RequestBody Cliente C,@PathVariable  Integer id) {
		CliSer.Editar(C.getNombre(),C.getDoi() ,C.getEmail(), C.getTipoDoi(), C.getDireccion(), 
				id,C.getEstado(),C.getApellido());
		men.setStatus(200);
		men.setMessage("Cliente Editado");
		return men;	
	}
	
	@DeleteMapping("/{id}")
	public Mensaje Eliminar(@PathVariable  Integer id) {
		CliSer.Eliminar(id);
		men.setStatus(200);
		men.setMessage("Cliente Eliminado");
		return men;	
	}

}
