package br.com.jsbse.arquitetura.integracao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class FabricaDeRepositorios {
	private Map<Class<? extends Repositorio>, Repositorio> repositorios;

	public void acrescentaRepositorio(Repositorio repositorio) {
		getRepositorios().put(repositorio.getClass(), repositorio);
	}
	
	public Repositorio getRepositorio(Class<? extends Repositorio> interfaceDoRepositorio) {
		return getRepositorios().get(interfaceDoRepositorio);
	}
	
	protected abstract void doPopulaDefinicoesDeRepositorio(Map<Class<? extends Repositorio>, Repositorio> definicoesDosRepositorios);
	
	private  Map<Class<? extends Repositorio>, Repositorio> getRepositorios() {
		if (repositorios == null) {
			repositorios = Collections.synchronizedMap(new HashMap<Class<? extends Repositorio>, Repositorio>());
			doPopulaDefinicoesDeRepositorio(repositorios);
		}
		return repositorios;
	}
}
