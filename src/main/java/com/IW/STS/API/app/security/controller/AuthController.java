package com.IW.STS.API.app.security.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.User;
import com.IW.STS.API.app.security.jwt.JwtUtils;
import com.IW.STS.API.app.security.repository.RoleRepository;
import com.IW.STS.API.app.security.repository.UserRepository;
import com.IW.STS.API.app.security.request.LoginRequest;
import com.IW.STS.API.app.security.request.SignupRequest;
import com.IW.STS.API.app.security.response.JwtResponse;
import com.IW.STS.API.app.security.response.MessageResponse;
import com.IW.STS.API.app.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;
  
  @Value("${apispring.app.IP.FOTO}")
  private String IP;
  
  	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	

  public static String uploadDirectoryVideo= System.getProperty("user.dir")+"/src/main/webapp/foto_usuario";

  
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, 
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(),
                         userDetails.getApellido(),
                         userDetails.getNombre(),
                         userDetails.getFoto(),
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body("400");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body("401");
    }

    // Create new user's account
    User user = new User();
    user.setUsername(signUpRequest.getUsername());
    user.setEmail(signUpRequest.getEmail());
    user.setApellido(signUpRequest.getApellido());
    user.setFoto(signUpRequest.getFoto());
    user.setNombre(signUpRequest.getNombre());
    user.setPassword(encoder.encode("password"));
    user.setFoto("http://"+IP+"/foto_usuario/perfil_anonimo.png");
    user.setRoles(signUpRequest.getRole());
    userRepository.save(user);

    return ResponseEntity.ok("201");
  }
  
  @GetMapping("/perfil/{id}")
	public User IdInfo(@PathVariable  Long id) {		
		return userRepository.findById(id).get();
	}	
	
	
	@PutMapping("/perfil1/{id}")
	public ResponseEntity<String> Editarperfil1(@RequestParam(name="file") MultipartFile foto,
			@RequestParam("usuarioNombre") String nombres,@RequestParam("usuarioApellido") String apellido,
			@RequestParam("usuarioPassword") String password, @PathVariable  Long id) {
		
		String uniqueFilename="perfil_anonimo.png";
		User u = userRepository.findById(id).get();
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
		u.setFoto("http://"+IP+"/foto_usuario/"+uniqueFilename);
		u.setId(id);
		u.setNombre(nombres);
		u.setApellido(apellido);
		if(!password.equals("")) {
			u.setPassword(encoder.encode(password));
		}
		u.setUpdated_at(LocalDate.now());		
		userRepository.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body("201");		
	}
	
	@PutMapping("/perfil2/{id}")
	public ResponseEntity<String> Editarperfil2(@RequestParam("usuarioNombre") String nombres,
			@RequestParam("usuarioApellido") String apellido,
			@RequestParam("usuarioPassword") String password, @PathVariable  Long id) {		
		User u = userRepository.findById(id).get();		
		u.setId(id);
		u.setNombre(nombres);
		u.setApellido(apellido);
		if(!password.equals("")) {
			u.setPassword(encoder.encode(password));
		}
		u.setUpdated_at(LocalDate.now());		
		userRepository.save(u);
		return ResponseEntity.status(HttpStatus.CREATED).body("201");		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Long id) {
		userRepository.findById(id).get().setEstado(false);
		userRepository.findById(id).get().setDeleted_at(LocalDate.now());
		userRepository.save(userRepository.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	
	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {		
		String nombre="",apellido="",rol="",username="";
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
				 }else if(vect[i*2].equals("username")){
					 username=vect[i*2+1];
				 }else {
					 rol=vect[i*2+1];
				 }
			 }	 		 		 
		 }		
		lis.setRows(userRepository.findByEstadoAndNombreStartsWithAndApellidoStartsWithAndUsernameStartsWith(true,nombre,apellido,username,
				PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(userRepository.findByEstadoAndNombreStartsWithAndApellidoStartsWithAndUsernameStartsWith(true,nombre,apellido,username,
				PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) userRepository.findByEstadoAndNombreStartsWithAndApellidoStartsWithAndUsernameStartsWith(true,nombre,apellido,username,
				PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);		 
		return lis;
	}
	
	@GetMapping("/{id}")
	public User InforId(@PathVariable  Long id) {		
		return userRepository.findById(id).get();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody User u,@PathVariable  Long id) {
		Collection<Long> idCol = Arrays.asList(id);
		if(userRepository.findByIdNotInAndEmail(idCol,u.getEmail())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			u.setId(id);			
			u.setUpdated_at(LocalDate.now());
			if(u.getRestablecer()) {
				u.setPassword(encoder.encode("password"));
			}
			u.setRestablecer(false);
			userRepository.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
}
