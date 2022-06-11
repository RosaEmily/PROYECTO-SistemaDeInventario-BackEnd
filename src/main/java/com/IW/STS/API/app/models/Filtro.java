package com.IW.STS.API.app.models;

import org.springframework.stereotype.Component;

@Component("filtro")
public class Filtro {
	
	public  Integer  limit;
	public  Integer page;
	public  Integer total_pages;
	public  Integer total_rows;
	
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}	
	public Integer getTotal_pages() {
		return total_pages;
	}
	public void setTotal_pages(Integer total_pages) {
		this.total_pages = total_pages;
	}
	public Integer getTotal_rows() {
		return total_rows;
	}
	public void setTotal_rows(Integer total_rows) {
		this.total_rows = total_rows;
	}
	
	

}
