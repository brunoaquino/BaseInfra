package br.com.jsbse.arquitetura.servico.dto;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistenteDto;

public class EnumeradoDto implements EnumeradoPersistenteDto {
	private String id;
	private String descricao;

	public EnumeradoDto() {
	}

	public EnumeradoDto(String id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnumeradoDto other = (EnumeradoDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
