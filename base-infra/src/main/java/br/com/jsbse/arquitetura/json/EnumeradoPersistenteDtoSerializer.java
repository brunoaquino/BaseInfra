package br.com.jsbse.arquitetura.json;

import java.io.IOException;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistenteDto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EnumeradoPersistenteDtoSerializer extends JsonSerializer<EnumeradoPersistenteDto> {

	@Override
	public void serialize(EnumeradoPersistenteDto value, JsonGenerator generator, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("id");
		generator.writeString(String.valueOf(value.getId()));
		generator.writeFieldName("descricao");
		generator.writeString(value.getDescricao());
		generator.writeEndObject();
	}
}