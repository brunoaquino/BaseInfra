package br.com.jsbse.arquitetura.json;

import java.io.StringWriter;

import br.com.jsbse.arquitetura.excecao.ExcecaoRuntime;
import br.com.jsbse.arquitetura.spring.ContextoSpring;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	public static final String OBJECT_MAPPER = "baseObjectMapper";

	public static String toJson(Object obj) {
		ObjectMapper mapper = ContextoSpring.getBean(OBJECT_MAPPER);
		try {
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, obj);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcecaoRuntime("Erro ao converter objeto.", e);
		}
	}

	public static <T> T fromJson(Class<T> classe, String json) {
		ObjectMapper mapper = ContextoSpring.getBean(OBJECT_MAPPER);
		try {
			return mapper.readValue(json, classe);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExcecaoRuntime("Erro ao converter objeto.", e);
		}
	}
}
