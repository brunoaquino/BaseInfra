package br.com.jsbse.arquitetura.validacao;

import java.text.NumberFormat;
import java.util.Locale;

public class ValidadorDeDouble implements ValidadorTipo {

	public boolean isZeroOuVazio(Object valor) {
		if (valor == null)
			return false;

		if (((Double) valor).equals(new Double(0.0))) {
			return true;
		}
		return false;
	}

	public String getObjetoFormatado(Object valor) {
		if (valor == null)
			return null;

		NumberFormat formato = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
		return formato.format(valor);
	}
}
