package com.IW.STS.API.app.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.IW.STS.API.app.models.Permiso;
import com.IW.STS.API.app.models.Role;
import com.IW.STS.API.app.models.User;
import com.IW.STS.API.app.security.jwt.AuthEntryPointJwt;
import com.IW.STS.API.app.security.jwt.AuthTokenFilter;
import com.IW.STS.API.app.security.repository.RoleRepository;
import com.IW.STS.API.app.security.repository.UserRepository;
import com.IW.STS.API.app.security.services.UserDetailsServiceImpl;
import com.IW.STS.API.app.services.PermisoServices;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/*@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
		
	  @Autowired
	  UserDetailsServiceImpl userDetailsService;
	  
	  @Value("${apispring.app.IP.FOTO}")
	  private String IP;

	  @Autowired
	  private AuthEntryPointJwt unauthorizedHandler;
	  
	  @Autowired
	  UserRepository userRepository;

	  @Autowired
	  RoleRepository roleRepository;
	  
	  @Autowired
	  PermisoServices permisoServ;
	  
	  @Lazy
	  @Autowired
	  private PasswordEncoder encoder;

	  @Bean
	  public AuthTokenFilter authenticationJwtTokenFilter() {
	    return new AuthTokenFilter();
	  }

	  @Override
	  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		  if(!permisoServ.existsByPermiso("Usuarios")) {
				Permiso perm=new Permiso();
				perm.setPermiso("Usuarios");
				permisoServ.save(perm);
				
				Permiso perm1=new Permiso();
				perm1.setPermiso("Roles");
				permisoServ.save(perm1);
				
				Permiso perm2=new Permiso();
				perm2.setPermiso("Listar Compras");
				permisoServ.save(perm2);
				
				Permiso perm3=new Permiso();
				perm3.setPermiso("Notas de Crédito");
				permisoServ.save(perm3);
				
				Permiso perm6=new Permiso();
				perm6.setPermiso("Proveedores");
				permisoServ.save(perm6);
				
				Permiso perm4=new Permiso();
				perm4.setPermiso("Listar Ventas");
				permisoServ.save(perm4);
				
				Permiso perm5=new Permiso();
				perm5.setPermiso("Clientes");
				permisoServ.save(perm5);
				
				Permiso perm7=new Permiso();
				perm7.setPermiso("Listar Productos");
				permisoServ.save(perm7);
				
				Permiso perm8=new Permiso();
				perm8.setPermiso("Agregar Producto");
				permisoServ.save(perm8);
				
				Permiso perm9=new Permiso();
				perm9.setPermiso("Categorias");
				permisoServ.save(perm9);
				
				Permiso perm10=new Permiso();
				perm10.setPermiso("Gráficos");
				permisoServ.save(perm10);
				
				Permiso perm11=new Permiso();
				perm11.setPermiso("Dashboard");
				permisoServ.save(perm11);
				
				Permiso perm12=new Permiso();
				perm12.setPermiso("P. Reposición");
				permisoServ.save(perm12);
				
				Permiso perm13=new Permiso();
				perm13.setPermiso("Análisis ABC");
				permisoServ.save(perm13);
				
			}
		  if(roleRepository.findByName("ADMINISTRADOR")==null) {
				Role rol=new Role();
				rol.setName("ADMINISTRADOR");
				roleRepository.save(rol);	
				Role rol2=new Role();
				rol2.setName("VENDEDOR");
				roleRepository.save(rol2);
				Role rol3=new Role();
				rol3.setName("GERENTE");
				roleRepository.save(rol3);
			}
		  if(roleRepository.findByName("ADMINISTRADOR")!=null) {
				if(roleRepository.findByName("ADMINISTRADOR").getPermisos().size()==0) {
					Role rol = roleRepository.findByName("ADMINISTRADOR");
					Permiso perm1 = permisoServ.findById(1).get();
					Permiso perm2 = permisoServ.findById(2).get();
					Permiso perm3 = permisoServ.findById(8).get();
					Permiso perm4 = permisoServ.findById(9).get();
					Permiso perm5 = permisoServ.findById(10).get();
					List<Permiso> permisos = new ArrayList<Permiso>();
					permisos.add(perm1);
					permisos.add(perm2);
					permisos.add(perm3);
					permisos.add(perm4);
					permisos.add(perm5);
					rol.setPermisos(permisos);;
					rol.setId(roleRepository.findByName("ADMINISTRADOR").getId());
					roleRepository.save(rol);
				}
			}
		  
		  if(roleRepository.findByName("ADMINISTRADOR")==null) {
				Role rol=new Role();
				rol.setName("ADMINISTRADOR");
				roleRepository.save(rol);	
				Role rol2=new Role();
				rol2.setName("VENDEDOR");
				roleRepository.save(rol2);
				Role rol3=new Role();
				rol3.setName("GERENTE");
				roleRepository.save(rol3);
			}		
			
			if(!userRepository.existsByUsername("ADMIN")) {
				User usu=new User();
				usu.setFoto("http://"+IP+"/foto_usuario/perfil_anonimo.png");
				usu.setPassword(encoder.encode("password"));
				usu.setEmail("admin@gmail.com");
				usu.setApellido("ADMIN");
				usu.setUsername("ADMIN");
				usu.setNombre("ADMIN");
				usu.setRoles(null);
				userRepository.save(usu);				
			}
			
			if(userRepository.existsByUsername("ADMIN")) {
				if(userRepository.findByUsername("ADMIN").get().getRoles().size()==0) {
					User userAdmin = userRepository.findByUsername("ADMIN").get();
					Role rolAdmin = roleRepository.findByName("ADMINISTRADOR");
					List<Role> roles = new ArrayList<Role>();
					roles.add(rolAdmin);
					userAdmin.setRoles(roles);
					userAdmin.setId(userAdmin.getId());
					userRepository.save(userAdmin);
				}
			}
			
	    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	  }

	  @Bean
	  @Override
	  public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	      .authorizeRequests().antMatchers("/api/**").permitAll().and()
	      .authorizeRequests().antMatchers("/foto_usuario/**").permitAll()
	      .antMatchers("/api/test/**").permitAll()
	      .anyRequest().authenticated();

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	  }
	
}
