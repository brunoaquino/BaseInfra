package br.com.jsbse.arquitetura.validacao;

public class ValidadorGenerico implements ValidadorTipo {

	@Override
	public boolean isZeroOuVazio(Object valor) {
		return valor == null;
	}

	public String getObjetoFormatado(Object valor) {
		return valor != null ? valor.toString() : "";
	}
}
