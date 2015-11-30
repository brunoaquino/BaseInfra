package br.com.jsbse.arquitetura.validacao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ValidadorDeDate extends ValidadorGenerico {

	@Override
	public String getObjetoFormatado(Object valor) {
		if (valor != null) {
			DateFormat formatter = new SimpleDateFormat("dd MMMMM yyyy", new Locale("pt", "BR"));
			return formatter.format(valor);
		}
		return null;
	}

}
