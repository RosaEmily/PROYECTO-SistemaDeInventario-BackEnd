package com.IW.STS.API.app.controller;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Usuario;
import com.IW.STS.API.app.services.UsuarioServices;

@RestController
@RequestMapping("api/auth")
public class UsuarioController {
	
	@Autowired
	private UsuarioServices UsuSer;
	
	
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Usuario U){
		if(UsuSer.Login(U.getEmail(), U.getPassword())!=null) {
			if(UsuSer.Login(U.getEmail(), U.getPassword()).getEstado()==false) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body("200");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401");
		}	
	}	
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String nombre="",apellido="";
		 System.out.println(filter);
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
				 if(vect[i*2].equals("apellido")) {
					 apellido=vect[i*2+1];
				 }else if(vect[i*2].equals("nombre")){
					 nombre=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
		lis.setRows(UsuSer.findByEstadoAndNombreStartsWithAndApellidoStartsWith(true,nombre,apellido,PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(UsuSer.findByEstadoAndNombreStartsWithAndApellidoStartsWith(true,nombre,apellido,PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) UsuSer.findByEstadoAndNombreStartsWithAndApellidoStartsWith(true,nombre,apellido,PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);		 
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Usuario u) {		
		if(UsuSer.findByEmail(u.getEmail())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			UsuSer.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Usuario> IdInfo(@PathVariable  Integer id) {		
		return UsuSer.findById(id);
	}
	
	@PostMapping("/correo")
	public Usuario Correo(@RequestBody Usuario u) {		
		return UsuSer.findByEmail(u.getEmail());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Usuario u,@PathVariable  Integer id) {
		Collection<Integer> idCol = Arrays.asList(id);
		if(UsuSer.findByIdNotInAndEmail(idCol,u.getEmail())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			u.setId(id);			
			u.setUpdated_at(LocalDate.now());
			if(u.getRestablecer()) {
				u.setPassword("password");
			}
			u.setRestablecer(false);
			UsuSer.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		UsuSer.findById(id).get().setEstado(false);
		UsuSer.findById(id).get().setDeleted_at(LocalDate.now());
		UsuSer.save(UsuSer.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	

}
