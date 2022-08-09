package com.IW.STS.API.app.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Usuario;
import com.IW.STS.API.app.services.RolServices;
import com.IW.STS.API.app.services.UsuarioServices;

@RestController
@RequestMapping("api/auth")
public class UsuarioController {
	
	@Autowired
	private UsuarioServices UsuSer;
	
	@Autowired
	private RolServices RolSer;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	
	public static String uploadDirectoryVideo= System.getProperty("user.dir")+"/src/main/webapp/foto_usuario";

	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Usuario U){
		
		//System.out.print(passwordEncoder.matches("password1","$2a$10$Y4GezIIfQ.wEYTvsmP4FTOJdVyneAExPxLpI5nClqKPfzlCrBXDTm"));
		if(UsuSer.findByEmail(U.getEmail())!=null) {
			if(UsuSer.findByEmail(U.getEmail()).getEstado()) {
				if(passwordEncoder.matches(U.getPassword(),UsuSer.findByEmail(U.getEmail()).getPassword())) {
					return ResponseEntity.status(HttpStatus.OK).body("200");
				}else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401");
				}
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("403");
			}			
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("402");
		}	
		
	}	
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String nombre="",apellido="",rol="";
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
				 }else {
					 rol=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
		lis.setRows(UsuSer.findByEstadoAndNombreStartsWithAndApellidoStartsWithAndRolIn(true,nombre,apellido,RolSer.findByRolStartsWith(rol),
				PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(UsuSer.findByEstadoAndNombreStartsWithAndApellidoStartsWithAndRolIn(true,nombre,apellido,RolSer.findByRolStartsWith(rol),
				PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) UsuSer.findByEstadoAndNombreStartsWithAndApellidoStartsWithAndRolIn(true,nombre,apellido,RolSer.findByRolStartsWith(rol),
				PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);		 
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Usuario u) {		
		if(UsuSer.findByEmail(u.getEmail())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			u.setFoto("http://localhost:4001/foto_usuario/perfil_anonimo.png");
			u.setPassword(passwordEncoder.encode("password"));
			UsuSer.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Usuario> IdInfo(@PathVariable  Integer id) {		
		return UsuSer.findById(id);
	}
	
	@GetMapping("/perfil/{email}")
	public Usuario IdInfo(@PathVariable  String email) {		
		return UsuSer.findByEmail(email);
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
				u.setPassword(passwordEncoder.encode("password"));
			}
			u.setRestablecer(false);
			UsuSer.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@PutMapping("/perfil1/{id}")
	public ResponseEntity<String> Editarperfil1(@RequestParam(name="file") MultipartFile foto,
			@RequestParam("usuarioNombre") String nombres,@RequestParam("usuarioApellido") String apellido,
			@RequestParam("usuarioPassword") String password, @PathVariable  Integer id) {
		
		String uniqueFilename="perfil_anonimo.png";
		Usuario u = UsuSer.findById(id).get();
		if (!foto.isEmpty()) {		
			Path directorio =Paths.get(uploadDirectoryVideo);
			uniqueFilename ="FOTO-USUARIO_" +nombres+"_"+apellido+"_"+id+"_"+ foto.getOriginalFilename();
			String rutaAbsoluta=directorio.toFile().getAbsolutePath();
			try {
				byte[] bytesImg=foto.getBytes();
				Path rutaCompleta=Paths.get(rutaAbsoluta+"//"+uniqueFilename);
				Files.write(rutaCompleta, bytesImg);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		u.setFoto("http://localhost:4001/foto_usuario/"+uniqueFilename);
		u.setId(id);
		u.setNombre(nombres);
		u.setApellido(apellido);
		if(!password.equals("")) {
			u.setPassword(passwordEncoder.encode(password));
		}
		u.setUpdated_at(LocalDate.now());		
		UsuSer.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body("201");		
	}
	
	@PutMapping("/perfil2/{id}")
	public ResponseEntity<String> Editarperfil2(@RequestParam("usuarioNombre") String nombres,
			@RequestParam("usuarioApellido") String apellido,
			@RequestParam("usuarioPassword") String password, @PathVariable  Integer id) {		
		Usuario u = UsuSer.findById(id).get();		
		u.setId(id);
		u.setNombre(nombres);
		u.setApellido(apellido);
		if(!password.equals("")) {
			u.setPassword(passwordEncoder.encode(password));
		}
		u.setUpdated_at(LocalDate.now());		
		UsuSer.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body("201");		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		UsuSer.findById(id).get().setEstado(false);
		UsuSer.findById(id).get().setDeleted_at(LocalDate.now());
		UsuSer.save(UsuSer.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	

}
