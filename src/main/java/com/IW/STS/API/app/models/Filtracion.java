package com.IW.STS.API.app.models;

public class Filtracion {
	
	public  Integer  limit;
	public  Integer order;
	public  Integer page;
	public  Integer sort;
	public  Integer pages;
	public  Integer _count;
	
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer get_count() {
		return _count;
	}
	public void set_count(Integer _count) {
		this._count = _count;
	}
	
	

}
