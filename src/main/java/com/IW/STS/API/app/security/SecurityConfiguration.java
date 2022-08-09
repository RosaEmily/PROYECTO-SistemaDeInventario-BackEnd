package com.IW.STS.API.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.IW.STS.API.app.models.Rol;
import com.IW.STS.API.app.models.Usuario;
import com.IW.STS.API.app.services.RolServices;
import com.IW.STS.API.app.services.UsuarioServices;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Lazy
	@Autowired
	private BCryptPasswordEncoder bycrypt;
	
	@Autowired
	private UsuarioServices user;
	
	@Autowired
	private RolServices servRol;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public  void  configure(AuthenticationManagerBuilder auth)  throws Exception{
		
		if(servRol.findByRol("ADMINISTRADOR")==null) {
			Rol rol=new Rol();
			rol.setRol("ADMINISTRADOR");
			servRol.save(rol);	
			Rol rol2=new Rol();
			rol2.setRol("VENDEDOR");
			servRol.save(rol2);
			Rol rol3=new Rol();
			rol3.setRol("GERENTE");
			servRol.save(rol3);
		}
		
		if(user.findByEmail("admin@gmail.com")==null) {
			Usuario usu=new Usuario();
			usu.setFoto("http://localhost:4001/foto_usuario/perfil_anonimo.png");
			usu.setPassword(bycrypt.encode("password"));
			usu.setEmail("admin@gmail.com");
			usu.setApellido("ADMIN");
			usu.setNombre("ADMIN");
			usu.setRol(null);
			user.save(usu);				
		}
		
		if(user.findByEmail("admin@gmail.com")!=null) {
			if(user.findByEmail("admin@gmail.com").getRol()==null) {
				Usuario userAdmin = user.findByEmail("admin@gmail.com");
				Rol rolAdmin = servRol.findByRol("ADMINISTRADOR");
				userAdmin.setRol(rolAdmin);
				userAdmin.setId(userAdmin.getId());
				user.save(userAdmin);
			}
		}	
		
		
		 auth.inMemoryAuthentication()
				.withUser("user")
				.password("1234")
				.roles("ADMINISTRADOR");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(				
				"/**"
				).permitAll()
		.anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
        .disable();			
	}
	
}
