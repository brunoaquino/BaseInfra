package br.com.jsbse.arquitetura.validacao;


public class ValidadorDeInteger implements ValidadorTipo {

	public boolean isZeroOuVazio(Object valor) {
		if (valor == null)
			return false;

		if (((Integer) valor).equals(0)) {
			return true;
		}
		return false;
	}

	@Override
	public String getObjetoFormatado(Object valor) {
		if (valor == null)
			return null;

		return valor.toString();
	}
}
