package br.com.jsbse.arquitetura.json;

import java.io.IOException;

import br.com.jsbse.arquitetura.tipo.Money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MoneySerializer extends JsonSerializer<Money> {

	@Override
	public void serialize(Money money, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		gen.writeString(money.toString());
	}
}