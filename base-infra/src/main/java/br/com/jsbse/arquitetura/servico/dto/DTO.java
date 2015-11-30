package br.com.jsbse.arquitetura.servico.dto;

import java.io.Serializable;

import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.integracao.Repositorio;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public abstract class DTO<ID extends Serializable> {
	public static final String CONVERTER = "Converter";
	public static final String RETRIEVER = "Retriever";
	public static final String ENUMERADO = "Enumerado";
	
	private ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	protected DAO getDAO() {
		return Aplicacao.get().getDAO();
	}
	
	protected <T extends Repositorio> T getRepositorio(Class<? extends Repositorio> interfaceDoRepositorio) {
		return Aplicacao.get().getRepositorio(interfaceDoRepositorio);
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
		@SuppressWarnings("rawtypes")
		DTO other = (DTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
