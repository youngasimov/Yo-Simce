package com.dreamer8.yosimce.shared.dto;

import java.io.Serializable;

public class DocumentoDTO implements Serializable {

	
	private Integer id;
	private String url;
	private String name;
	
	public DocumentoDTO(){}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
