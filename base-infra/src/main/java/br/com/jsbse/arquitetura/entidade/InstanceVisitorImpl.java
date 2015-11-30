package br.com.jsbse.arquitetura.entidade;

@SuppressWarnings("rawtypes")
public class InstanceVisitorImpl implements InstanceVisitor {


	private Entidade entidade;

	public void getInstance(Entidade entity) {
		this.entidade = entity;
	}

	public Entidade getEntidade() {
		return entidade;
	}

/*	public void setEntity(Entity entity) {
		this.entity = entity;
	}
*/
}
