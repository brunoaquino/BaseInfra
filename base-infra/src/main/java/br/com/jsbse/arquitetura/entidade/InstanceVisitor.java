package br.com.jsbse.arquitetura.entidade;

/**
 * Utilitária para recuperar a instância.
 * 
 */
public interface InstanceVisitor {

	@SuppressWarnings("rawtypes")
	void getInstance(Entidade entity);

}
