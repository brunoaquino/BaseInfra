package br.com.jsbse.arquitetura.integracao;

import java.util.ArrayList;
import java.util.List;

import br.com.jsbse.arquitetura.entidade.Entidade;


public class ClassesPersistentes {
	private List<Class<? extends Entidade<?>>> classes;

	public ClassesPersistentes() {
	}

	public ClassesPersistentes(Class<? extends Entidade<?>>... classes) {
		for (Class<? extends Entidade<?>> classe : classes) {
			acrescentaClassePersistente(classe);
		}
	}

	public ClassesPersistentes(List<Class<? extends Entidade<?>>> classes) {
		super();
		this.classes = classes;
	}

	public void acrescentaClassePersistente(Class<? extends Entidade<?>> classe) {
		getClasses().add(classe);
	}

	public List<Class<? extends Entidade<?>>> getClasses() {
		if (classes == null) {
			classes = new ArrayList<Class<? extends Entidade<?>>>();
		}
		return classes;
	}

	public void setClasses(List<Class<? extends Entidade<?>>> classes) {
		this.classes = classes;
	}

	public boolean isContemAClasse(Class<? extends Entidade<?>> classe) {
		return getClasses().contains(classe);
	}
}
