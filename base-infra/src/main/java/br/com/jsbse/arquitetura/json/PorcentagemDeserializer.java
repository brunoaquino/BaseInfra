package br.com.jsbse.arquitetura.json;

import java.io.IOException;

import br.com.jsbse.arquitetura.tipo.Porcentagem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class PorcentagemDeserializer extends JsonDeserializer<Porcentagem> {

	@Override
	public Porcentagem deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String value = p.getText();
		value = value.replace("\"", "");
		return new Porcentagem(value);
	}

}
