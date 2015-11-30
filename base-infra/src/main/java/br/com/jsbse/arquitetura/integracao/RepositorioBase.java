package br.com.jsbse.arquitetura.integracao;

import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public abstract class RepositorioBase {

	public DAO getDAO() {
		return Aplicacao.get().getDAO();
	}
}
