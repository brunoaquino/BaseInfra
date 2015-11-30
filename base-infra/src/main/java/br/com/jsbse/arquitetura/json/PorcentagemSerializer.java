package br.com.jsbse.arquitetura.json;

import java.io.IOException;

import br.com.jsbse.arquitetura.tipo.Porcentagem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PorcentagemSerializer extends JsonSerializer<Porcentagem>{

	@Override
	public void serialize(Porcentagem porcentagem, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		gen.writeString(porcentagem.toString());
	}

}
