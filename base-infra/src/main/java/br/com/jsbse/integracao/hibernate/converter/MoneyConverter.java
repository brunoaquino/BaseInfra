package br.com.jsbse.integracao.hibernate.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.jsbse.arquitetura.tipo.Money;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {

	private static final int SCALE = 2;

	@Override
	public BigDecimal convertToDatabaseColumn(Money money) {
		return null == money ? null : money.toBigDecimal(SCALE, RoundingMode.HALF_UP);
	}

	@Override
	public Money convertToEntityAttribute(BigDecimal valor) {
		return null == valor ? null : new Money(valor);
	}

}
