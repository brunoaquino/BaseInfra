package br.com.jsbse.arquitetura.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@Override
	public void serialize(Date aDate, JsonGenerator aJsonGenerator, SerializerProvider aSerializerProvider) throws IOException, JsonProcessingException {

		String dateString = format.format(aDate);
		aJsonGenerator.writeString(dateString);
	}
}
