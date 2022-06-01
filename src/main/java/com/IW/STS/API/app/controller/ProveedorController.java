package com.IW.STS.API.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.IW.STS.API.app.models.Filtracion;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Mensaje;
import com.IW.STS.API.app.models.Proveedor;
import com.IW.STS.API.app.services.ProveedorServices;

@RestController
@RequestMapping("api/proveedor")
public class ProveedorController {
	
	@Autowired
	private ProveedorServices ProSer;
	private Filtracion fil =new  Filtracion();
	private ListarFiltro lis = new ListarFiltro(); 
	private Mensaje men =new Mensaje();
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page) {		
		lis.setRows(ProSer.Listar());
		fil.setLimit(limit);
		fil.setPage(page);
		lis.setResponseFilter(fil);
		return lis;
	}
	
	@PostMapping("/")
	public Mensaje Guardar(@RequestBody Proveedor P) {
		if(ProSer.Verificar(P.getDoi())!=null) {
			men.setStatus(401);
			men.setMessage("El proveedor ingresado ya existe");
		}else {
			ProSer.save(P);
			men.setStatus(200);
			men.setMessage("Proveedor Creado");
		}
		return men;	
	}
	
	@PostMapping("/supplier")
	public Proveedor IdInfo(@RequestParam Integer id) {		
		return ProSer.GetId(id);	
	}
	
	@PutMapping("")
	public Mensaje Editar(@RequestBody Proveedor P) {
		ProSer.Editar(P.getNombre(),P.getDoi() ,P.getEmail(), P.getTipoDoi(), P.getDireccion(), P.getId());
		men.setStatus(200);
		men.setMessage("Proveedor Editado");
		return men;	
	}
	
	@PostMapping("")
	public Mensaje Eliminar(@RequestParam Integer id) {
		ProSer.Eliminar(id);
		men.setStatus(200);
		men.setMessage("Proveedor Eliminado");
		return men;	
	}
	

}
