package br.com.jsbse.integracao.hibernate;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import br.com.jsbse.arquitetura.entidade.Entidade;

@MappedSuperclass
public class EntidadeMapeada<ID extends Serializable> extends Entidade<ID> {

}
