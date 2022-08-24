package com.IW.STS.API.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IW.STS.API.app.models.Permiso;
import com.IW.STS.API.app.services.PermisoServices;

@RestController
@RequestMapping("api/permiso")
public class PermisoController {
	
	@Autowired
	PermisoServices Perm;
	
	@GetMapping("")
	public List<Permiso> ListPermisos() {		
		return Perm.findAll();
	}
	

}
