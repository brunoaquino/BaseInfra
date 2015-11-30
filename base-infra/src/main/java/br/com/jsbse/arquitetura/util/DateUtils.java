package br.com.jsbse.arquitetura.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

	public static int daysBetween(LocalDate menorData, LocalDate maiorData) {
		return (int) ChronoUnit.DAYS.between(menorData, maiorData);
	}

	public static LocalDateTime getLocalDateTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	public static LocalDate getLocalDate(Date date) {
		return getLocalDateTime(date).toLocalDate();
	}

	public static Date getDate(LocalDate date) {
		String str = String.format("%s/%s/%s 00:00:00", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static LocalDate today() {
		return LocalDate.now();
	}
}
