package com.IW.STS.API.app.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.IW.STS.API.app.models.Usuario;
import com.IW.STS.API.app.services.UsuarioServices;

@RestController
@RequestMapping("api/auth")
public class UsuarioController {
	
	@Autowired
	private UsuarioServices UsuSer;
	
	@PostMapping("/save")
	public void Guardar(@RequestBody Usuario U) {
		UsuSer.save(U);
	}
	
	
	@GetMapping("/listar")
	public List<Usuario> listar(){
		return UsuSer.ListarUsuario(false);		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Usuario U){
		if(UsuSer.Login(U.getUsu_email(), U.getUsu_password())!=null) {
			if(UsuSer.Login(U.getUsu_email(), U.getUsu_password()).getUsu_estado()==false) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body("200");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401");
		}	
	}	

	

}
