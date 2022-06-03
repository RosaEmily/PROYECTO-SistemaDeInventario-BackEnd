package com.IW.STS.API.app.models;

import java.util.List;

public class ListarFiltro {
	
	public List rows;	
	public Filtracion responseFilter;	
	
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Filtracion getResponseFilter() {
		return responseFilter;
	}
	public void setResponseFilter(Filtracion responseFilter) {
		this.responseFilter = responseFilter;
	}	

}
