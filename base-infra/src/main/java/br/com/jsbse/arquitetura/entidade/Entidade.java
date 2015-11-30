package br.com.jsbse.arquitetura.entidade;

import java.io.Serializable;

import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.integracao.Repositorio;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public abstract class Entidade<ID extends Serializable> {
	private ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public boolean isTransient() {
		return getId() == null;
	}

	public String getNid() {
		return "";
	}

	public void getInstance(InstanceVisitor instanceVisitor) {
		instanceVisitor.getInstance(this);
	}

	protected DAO getDAO() {
		return Aplicacao.get().getDAO();
	}

	protected <T extends Repositorio> T getRepositorio(Class<? extends Repositorio> interfaceDoRepositorio) {
		return Aplicacao.get().getRepositorio(interfaceDoRepositorio);
	}

	public <T extends Entidade<ID>> T get(Class<T> classe,  ID id) {
		return (T) getDAO().getObjeto(classe, id);
		}

	public void persiste() {
		getDAO().persisteObjeto(this);
	}
	
	public void merge() {
		getDAO().merge(this);
	}

	public void exclui() {
		getDAO().excluiObjeto(this);
	}

	@SuppressWarnings("hiding")
	protected <T extends Entidade<ID>, ID extends Serializable> void persiste(T objeto) {
		getDAO().persisteObjeto(objeto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidade other = (Entidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
