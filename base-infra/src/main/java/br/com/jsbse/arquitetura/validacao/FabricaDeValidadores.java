package br.com.jsbse.arquitetura.validacao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FabricaDeValidadores {
	@SuppressWarnings("rawtypes")
	private static Map<Class, ValidadorTipo> validadores = new HashMap<Class, ValidadorTipo>();

	static {
		validadores.put(String.class, new ValidadorDeString());
		validadores.put(BigDecimal.class, new ValidadorDeBigDecimal());
		validadores.put(Double.class, new ValidadorDeDouble());
		validadores.put(Integer.class, new ValidadorDeInteger());
		validadores.put(Long.class, new ValidadorDeLong());
		validadores.put(Date.class, new ValidadorDeDate());
		validadores.put(Object.class, new ValidadorGenerico());
	}

	public static ValidadorTipo getValidador(Object objeto) {
		if (objeto == null)
			return validadores.get(Object.class);

		ValidadorTipo validador = validadores.get(objeto.getClass());

		if (validador == null) {
			validador = validadores.get(Object.class);
		}

		return validador;
	}
}
