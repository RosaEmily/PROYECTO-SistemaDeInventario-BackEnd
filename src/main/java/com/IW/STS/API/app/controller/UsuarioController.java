package com.IW.STS.API.app.controller;
import java.sql.Array;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.IW.STS.API.app.models.Mensaje;
import com.IW.STS.API.app.models.Usuario;
import com.IW.STS.API.app.services.UsuarioServices;

@RestController
@RequestMapping("api/auth")
public class UsuarioController {
	
	@Autowired
	private UsuarioServices UsuSer;
	
	private Mensaje men =new Mensaje();
	
	@PostMapping("/save")
	public void Guardar(@RequestBody Usuario U) {
		UsuSer.save(U);
	}
	
	
	@GetMapping("/listar")
	public List<Usuario> listar(){
		return UsuSer.ListarUsuario(false);		
	}
	
	
	@PostMapping("/login")
	public Mensaje login(@RequestBody Usuario U){
		if(UsuSer.Login(U.getUsu_email(), U.getUsu_password())!=null) {
			if(UsuSer.Login(U.getUsu_email(), U.getUsu_password()).getUsu_estado()==false) {
				men.setStatus(401);
				men.setMessage("Usuario inactivo");
			}else {
				men.setStatus(200);
				men.setMessage("Credenciales Correctas");
			}
		}else {
			men.setStatus(401);
			men.setMessage("Credenciales Incorrectas");
		}
		return men;		
	}	

	

}
