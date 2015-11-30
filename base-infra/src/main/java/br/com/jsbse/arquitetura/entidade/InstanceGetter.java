package br.com.jsbse.arquitetura.entidade;



/**
 * Recupera a instância do objeto quando este for um proxy.
  
 */
public class InstanceGetter {

	@SuppressWarnings("rawtypes")
	public static Object getInstance(Entidade entidade) {
		InstanceVisitorImpl instanceVisitor = new InstanceVisitorImpl();
		entidade.getInstance(instanceVisitor);

		return instanceVisitor.getEntidade();
	}
}
