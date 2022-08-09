package com.IW.STS.API.app.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.services.ClienteServices;


@RestController
@RequestMapping("api/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteServices CliSer;
	
	@Autowired
	private Filtro fil;
	
	@Autowired
	private ListarFiltro lis;
	

	@GetMapping("")
	public ListarFiltro Listar(@RequestParam Integer limit,@RequestParam Integer page,@RequestParam String filter) {
		 String doi="",nombre="";
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
				 if(vect[i*2].equals("doi")) {
					 doi=vect[i*2+1];
				 }else {
					 nombre=vect[i*2+1];
				 }
			 }	 		 		 
		 }	
		lis.setRows(CliSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true,doi,nombre,PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(CliSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true,doi,nombre,PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) CliSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true,doi,nombre,PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);		 
		return lis;
	}
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Cliente C) {
		if(CliSer.findByDoi(C.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			CliSer.save(C);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Cliente> IdInfo(@PathVariable  Integer id) {		
		return CliSer.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Cliente C,@PathVariable  Integer id) {
		Collection<Integer> idCol = Arrays.asList(id);
		if(CliSer.findByIdNotInAndDoi(idCol,C.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			C.setId(id);			
			C.setUpdated_at(LocalDate.now());
			CliSer.save(C);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		CliSer.findById(id).get().setEstado(false);
		CliSer.findById(id).get().setDeleted_at(LocalDate.now());
		CliSer.save(CliSer.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

}
