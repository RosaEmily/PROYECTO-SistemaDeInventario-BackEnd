package com.IW.STS.API.app.models;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("Listafiltro")
public class ListarFiltro {
	
	public List rows;	
	public Filtro responseFilter;	
	
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Filtro getResponseFilter() {
		return responseFilter;
	}
	public void setResponseFilter(Filtro responseFilter) {
		this.responseFilter = responseFilter;
	}	

}
