package br.com.jsbse.arquitetura.entidade;

import java.util.ArrayList;
import java.util.List;

public class RegraDeUnicidade {

	private List<ParametroDeConsulta> parametros;

	public RegraDeUnicidade com(ParametroDeConsulta parametro) {
		getParametros().add(parametro);
		return this;
	}

	public RegraDeUnicidade com(String campo, String valor) {
		getParametros().add(new ParametroDeConsulta(campo, valor));
		return this;
	}

	public List<ParametroDeConsulta> getParametros() {
		if (parametros == null) {
			parametros = new ArrayList<ParametroDeConsulta>();
		}
		return parametros;
	}
}
