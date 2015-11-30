package br.com.jsbse.arquitetura.json;

import java.io.IOException;

import br.com.jsbse.arquitetura.servico.dto.EnumeradoDto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class EnumeradoDtoDeserializer extends JsonDeserializer<EnumeradoDto> {

	@Override
	public EnumeradoDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		JsonNode idNode = node.get("id");
		if (idNode == null)
			return null;

		String id = idNode.asText();
		JsonNode descricaoNode = node.get("descricao");
		String descricao = descricaoNode != null ? descricaoNode.asText() : null;
		return new EnumeradoDto(id, descricao);
	}

}
