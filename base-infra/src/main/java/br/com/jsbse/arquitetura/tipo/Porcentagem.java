package br.com.jsbse.arquitetura.tipo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Porcentagem {

	public static final Porcentagem ZERO = new Porcentagem(0.);
	private BigDecimal valor;

	public Porcentagem(Double valor) {
		this(new BigDecimal(valor));
	}

	public Porcentagem(BigDecimal valor) {
		this.valor = valor;
	}

	public Porcentagem(String valor) {
		this.valor = new BigDecimal(valor);
	}

	public BigDecimal toBigDecimal(int scale) {
		return toBigDecimal(scale, RoundingMode.HALF_UP);
	}

	public BigDecimal toBigDecimal(int scale, RoundingMode mode) {
		return valor.setScale(scale, mode);
	}

	public BigDecimal toBigDecimal() {
		return toBigDecimal(2, RoundingMode.HALF_UP);
	}

	public static Porcentagem valueOf(Double val) {
		return new Porcentagem(val);
	}

	public Double doubleValue() {
		return valor.doubleValue();
	}

	@Override
	public String toString() {
		return toBigDecimal(2).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	public boolean equals(Double valor) {
		return this.equals(Porcentagem.valueOf(valor));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Porcentagem other = (Porcentagem) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!toBigDecimal(2).equals(other.toBigDecimal(2)))
			return false;
		return true;
	}
}