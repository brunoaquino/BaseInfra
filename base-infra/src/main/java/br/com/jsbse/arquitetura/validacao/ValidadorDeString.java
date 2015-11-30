package br.com.jsbse.arquitetura.validacao;

import br.com.jsbse.arquitetura.util.UtilString;

public class ValidadorDeString implements ValidadorTipo {

	public boolean isZeroOuVazio(Object valor) {
		if (UtilString.isVazio((String) valor)) {
			return true;
		}
		return false;
	}

	public String getObjetoFormatado(Object valor) {
		return valor != null ? valor.toString() : "";
	}
}
