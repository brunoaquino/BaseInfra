package br.com.jsbse.arquitetura.validacao;


public class ValidadorDeLong implements ValidadorTipo {

	public boolean isZeroOuVazio(Object valor) {
		if (valor == null)
			return false;

		if (((Long) valor).equals(0L)) {
			return true;
		}
		return false;
	}

	public String getObjetoFormatado(Object valor) {
		if (valor == null)
			return null;

		return valor.toString();
	}
}
