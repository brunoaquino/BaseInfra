package br.com.jsbse.integracao.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import br.com.jsbse.arquitetura.entidade.EntidadeEL;

@MappedSuperclass
public class EntidadeMapeadaEL<ID extends Serializable> extends EntidadeEL<ID> {

	@Column(name = "st_excluido")
	public boolean isExcluido() {
		return super.isExcluido();
	}
}
