package br.com.jsbse.arquitetura.validacao;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class ValidadorDeBigDecimal implements ValidadorTipo {

	public boolean isZeroOuVazio(Object valor) {
		if (valor == null)
			return false;

		return (((BigDecimal) valor).equals(BigDecimal.ZERO));
	}

	@Override
	public String getObjetoFormatado(Object valor) {
		if (valor == null)
			return null;

		NumberFormat formato = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
		return formato.format(valor);
	}

}
