package br.com.jsbse.arquitetura.servico.dto;

import java.io.Serializable;

public class ObjetoDto<ID extends Serializable> extends DTO<ID> {

	public ObjetoDto() {
	}

	public ObjetoDto(ID id) {
		super();
		super.setId(id);
	}

	public ObjetoDto(ID id, String nid) {
		super();
		super.setId(id);
		this.nid = nid;
	}

	private String nid;

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}
}
