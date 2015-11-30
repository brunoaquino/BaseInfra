package br.com.jsbse.arquitetura.integracao;

import java.io.Serializable;
import java.util.Collection;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.entidade.ParametroDeConsulta;


public interface DAO {
	
	<T extends Entidade<ID>, ID extends Serializable> void persisteObjeto(T objeto);

	<T extends Entidade<ID>, ID extends Serializable> void merge(T objeto);

	<T extends Entidade<ID>, ID extends Serializable> void excluiObjeto(T objeto);

	<T extends Entidade<ID>, ID extends Serializable> void excluiObjetos(Collection<T> lista);

	<T extends Entidade<ID>, ID extends Serializable> T getObjeto(Class<T> tipoDaClasse, ID id);

	public <T extends Entidade<ID>, ID extends Serializable> Collection<T> getObjetos(Class<T> tipoDaClasse, Serializable[] ids);

	<T extends Entidade<ID>, ID extends Serializable> Collection<T> getObjetos(Class<T> tipoDaClasse);

	<T extends Entidade<ID>, ID extends Serializable> T getObjetoPorParametros(Class<T> tipoDaClasse, ParametroDeConsulta ...parametros);

}
