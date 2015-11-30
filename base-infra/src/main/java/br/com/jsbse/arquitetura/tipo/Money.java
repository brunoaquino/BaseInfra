package br.com.jsbse.arquitetura.tipo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {

	public static final Money ZERO = new Money(0);

	private BigDecimal valor;
	private int casasDecimais = 2;

	public Money() {
	}

	public Money(Integer valor) {
		this.valor = new BigDecimal(valor);
	}

	public Money(Double valor) {
		this.valor = new BigDecimal(valor);
	}

	public Money(BigDecimal valor) {
		this.valor = valor;
	}

	public Money(String valor) {
		this.valor = new BigDecimal(valor);
	}

	public BigDecimal toBigDecimal(int scale) {
		return toBigDecimal(scale, RoundingMode.CEILING);
	}

	public BigDecimal toBigDecimal(int scale, RoundingMode mode) {
		return valor.setScale(scale, mode);
	}

	public BigDecimal toBigDecimal() {
		return toBigDecimal(casasDecimais, RoundingMode.CEILING);
	}

	public static Money valueOf(Double val) {
		return new Money(val);
	}

	public Double doubleValue() {
		return valor.doubleValue();
	}

	public Money divide(Money valor) {
		return divide(valor, casasDecimais);
	}
	public Money divide(BigDecimal valor) {
		return divide(new Money(valor));
	}

	public Money divide(Money fator, int scale) {
		BigDecimal resultado = this.valor.divide(fator.valor, scale, RoundingMode.CEILING);
		return new Money(resultado);
	}

	@Override
	public String toString() {
		return toBigDecimal(2).toString();
	}

	public boolean isZero() {
		return this.valor.equals(ZERO);
	}
}