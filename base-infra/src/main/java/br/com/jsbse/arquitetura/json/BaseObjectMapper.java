package br.com.jsbse.arquitetura.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistenteDto;
import br.com.jsbse.arquitetura.servico.dto.EnumeradoDto;
import br.com.jsbse.arquitetura.tipo.Money;
import br.com.jsbse.arquitetura.tipo.Porcentagem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BaseObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -3535954639221175033L;

	public BaseObjectMapper() {
		super();
		registerModule(new JavaTimeModule());
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
		// setDateFormat(new ISO8601DateFormat());

		SimpleModule enumModule = new SimpleModule("enumerado");
		enumModule.addSerializer(EnumeradoDto.class, new EnumeradoDtoSerializer());
		enumModule.addDeserializer(EnumeradoDto.class, new EnumeradoDtoDeserializer());
		enumModule.addSerializer(EnumeradoPersistenteDto.class, new EnumeradoPersistenteDtoSerializer());
		enumModule.addDeserializer(EnumeradoPersistenteDto.class, new EnumeradoPersistenteDtoDeserializer());
		super.registerModule(enumModule);

		SimpleModule dateModule = new SimpleModule("date");
		dateModule.addSerializer(Date.class, new DateSerializer());
		dateModule.addDeserializer(Date.class, new DateDeserializer());
		super.registerModule(dateModule);

		SimpleModule localDateModule = new SimpleModule("localDate");
		localDateModule.addSerializer(LocalDate.class, new LocalDateSerializer());
		localDateModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		super.registerModule(localDateModule);

		SimpleModule localDateTimeModule = new SimpleModule("localDateTime");
		localDateTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		localDateTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		super.registerModule(localDateTimeModule);

		SimpleModule moneyModule = new SimpleModule("money");
		moneyModule.addSerializer(Money.class, new MoneySerializer());
		moneyModule.addDeserializer(Money.class, new MoneyDeserializer());
		super.registerModule(moneyModule);

		SimpleModule porcentagemModule = new SimpleModule("porcentagem");
		porcentagemModule.addSerializer(Porcentagem.class, new PorcentagemSerializer());
		porcentagemModule.addDeserializer(Porcentagem.class, new PorcentagemDeserializer());
		super.registerModule(porcentagemModule);
	}
}
