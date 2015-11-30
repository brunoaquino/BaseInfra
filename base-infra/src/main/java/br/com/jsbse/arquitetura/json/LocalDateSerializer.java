package br.com.jsbse.arquitetura.json;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate>{

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	@Override
	public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		String dateString = formatter.format(date);
		gen.writeString(dateString);
	}

}
