package br.com.jsbse.integracao.hibernate.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.AttributeConverter;

import br.com.jsbse.arquitetura.tipo.Porcentagem;

public class PorcentagemConverter implements AttributeConverter<Porcentagem, BigDecimal> {

	private static final int SCALE = 2;

	@Override
	public BigDecimal convertToDatabaseColumn(Porcentagem percentual) {
		return null == percentual ? null : percentual.toBigDecimal(SCALE, RoundingMode.HALF_UP);
	}

	@Override
	public Porcentagem convertToEntityAttribute(BigDecimal valor) {
		return null == valor ? null : new Porcentagem(valor);
	}
}