package com.IW.STS.API.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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

import com.IW.STS.API.app.classs.Datos;
import com.IW.STS.API.app.models.Filtro;
import com.IW.STS.API.app.models.ListarFiltro;
import com.IW.STS.API.app.models.Proveedor;
import com.IW.STS.API.app.services.ProveedorServices;


@RestController
@RequestMapping("api/proveedor")
public class ProveedorController {
	
	@Autowired
	private ProveedorServices ProSer;
	
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
		lis.setRows(ProSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true,doi,nombre,PageRequest.of(page-1,limit)).getContent());		
		fil.setLimit(limit);
		fil.setPage(page);
		fil.setTotal_pages(ProSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true,doi,nombre,PageRequest.of(page-1,limit)).getTotalPages());
		fil.setTotal_rows((int) ProSer.findByEstadoAndDoiStartsWithAndNombreStartsWith(true,doi,nombre,PageRequest.of(page-1,limit)).getTotalElements());
		lis.setResponseFilter(fil);		 
		return lis;
	}
	
	
	@GetMapping("/all")
	public List<Proveedor> ListarAll() {		
		return ProSer.findAll();
	}
	
	
	@GetMapping("/prueba")
	public  List<Datos> ListarPrueba() {	
		List<Datos> listDatos = new ArrayList<Datos>();
		for(Proveedor pro: ProSer.findAll()) {
			Datos  d= new Datos();
			d.setDireccion(pro.getDireccion());
			d.setDoi(pro.getDoi());
			listDatos.add(d);
		}		
		return listDatos;
	}
	
	
	@PostMapping("")
	public ResponseEntity<String> Guardar(@RequestBody Proveedor P) {
		if(ProSer.findByDoi(P.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			ProSer.save(P);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@GetMapping("/{id}")
	public Optional<Proveedor> IdInfo(@PathVariable  Integer id) {		
		return ProSer.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> Editar(@RequestBody Proveedor P,@PathVariable  Integer id) {
		Collection<Integer> idCol = Arrays.asList(id);

		if(ProSer.findByIdNotInAndDoi(idCol,P.getDoi())!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("400");
		}else {
			P.setId(id);			
			P.setUpdated_at(LocalDate.now());
			ProSer.save(P);
			return ResponseEntity.status(HttpStatus.CREATED).body("201");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> Eliminar(@PathVariable  Integer id) {
		ProSer.findById(id).get().setEstado(false);
		ProSer.findById(id).get().setDeleted_at(LocalDate.now());
		ProSer.save(ProSer.findById(id).get());				
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}
	

}
