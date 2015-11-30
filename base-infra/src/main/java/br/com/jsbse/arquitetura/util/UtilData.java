package br.com.jsbse.arquitetura.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.jsbse.arquitetura.tipo.PrecisaoDoTempo;

public class UtilData {

	public static Date getDataAdicionadaEmSegundos(Date data, int segundos) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.SECOND, segundos);
		return calendario.getTime();
	}

	public static Date getDataAdicionadaEmMinutos(Date data, int minutos) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.MINUTE, minutos);
		return calendario.getTime();
	}

	public static Date getDataAdicionadaEmHoras(Date data, int horas) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.HOUR_OF_DAY, horas);
		return calendario.getTime();
	}

	public static Date getDataAdicionadaEmDias(Date data, int dias) {
		LocalDate date = DateUtils.getLocalDate(data);
		date = date.plusDays(dias);
		return DateUtils.getDate(date);
	}

	public static Date getDataAdicionadaEmSemanas(Date data, int semanas) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.WEEK_OF_MONTH, semanas);
		return calendario.getTime();
	}

	public static Date getDataAdicionadaEmSemanasDoAno(Date data, int semanas) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.WEEK_OF_YEAR, semanas);
		return calendario.getTime();
	}

	public static Date getDataAdicionadaEmMeses(Date data, int meses) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.MONTH, meses);
		return calendario.getTime();
	}

	public static Date getDataAdicionadaEmAnos(Date data, int anos) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(Calendar.YEAR, anos);
		return calendario.getTime();
	}

	public static int getDiferencaEntreDatasEmDias(Date dataAnterior, Date dataPosterior) {
		LocalDate anterior = DateUtils.getLocalDate(dataAnterior);
		LocalDate posterior = DateUtils.getLocalDate(dataPosterior);
		return DateUtils.daysBetween(anterior, posterior);
	}

	public static int getDiferencaEntreDatasEmSemanas(Date dataAnterior, Date dataPosterior) {
		long diferencaEmMili = dataPosterior.getTime() - dataAnterior.getTime();
		// 7dias * 24h * 60m * 60s * 1000ms
		return (int) Math.floor(diferencaEmMili / 604800000.);
	}

	public static int getDiferencaEntreDatasEmHoras(Date dataAnterior, Date dataPosterior) {
		long diferencaEmMili = dataPosterior.getTime() - dataAnterior.getTime();
		// 60m * 60s * 1000ms
		return (int) Math.floor(diferencaEmMili / 3600000.);
	}

	public static int getDiferencaEntreDatasEmMinutos(Date dataAnterior, Date dataPosterior) {
		long diferencaEmMili = dataPosterior.getTime() - dataAnterior.getTime();
		// 60s * 1000ms
		return (int) Math.floor(diferencaEmMili / 60000.);
	}

	public static int getDiferencaEntreDatasEmSegundos(Date dataAnterior, Date dataPosterior) {
		long diferencaEmMili = dataPosterior.getTime() - dataAnterior.getTime();
		// 1000ms
		return (int) Math.floor(diferencaEmMili / 1000.);
	}

	public static Date getUltimoDiaDoMes(Date data) {
		return getCalendarioDoUltimoDiaDoMes(data).getTime();
	}

	private static Calendar getCalendarioDoUltimoDiaDoMes(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.set(Calendar.DATE, 1);
		calendario.add(Calendar.MONTH, 1);
		calendario.add(Calendar.DATE, -1);
		return calendario;
	}

	public static Date getUltimoDiaUtilDoMes(Date data) {
		Calendar calendario = getCalendarioDoUltimoDiaDoMes(data);
		if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendario.add(Calendar.DATE, -1);
		} else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendario.add(Calendar.DATE, -2);
		}
		return calendario.getTime();
	}

	public static Date getPrimeiroDiaDoMes(Date data) {
		return getCalendarioDoPrimeiroDiaDoMes(data).getTime();
	}

	private static Calendar getCalendarioDoPrimeiroDiaDoMes(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.set(Calendar.DATE, 1);
		return calendario;
	}

	public static Date getUltimoDiaDoAno(Integer ano) {
		if (ano == null)
			return null;

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.YEAR, ano);

		calendar = zerarHora(calendar);

		return calendar.getTime();
	}

	/**
	 * Método que recebe uma data e retorna o primeiro dia de seu ano. <br />
	 * ex: Caso seja passado como parametro a data 07/08/2007 este metodo retornará a data 01/01/2007. <br />
	 * 
	 * @param data
	 * @return
	 */
	public static Date getPrimeiroDiaDoAno(Date data) {
		if (data == null)
			return null;

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.YEAR, getAno(data));

		calendar = zerarHora(calendar);

		return calendar.getTime();
	}

	private static Calendar zerarHora(Calendar calendar) {

		calendar.set(Calendar.AM_PM, Calendar.AM);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar;
	}

	public static Date getPrimeiroDiaUtilDoMes(Date data) {
		Calendar calendario = getCalendarioDoPrimeiroDiaDoMes(data);
		if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendario.add(Calendar.DATE, 2);
		} else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendario.add(Calendar.DATE, 1);
		}
		return calendario.getTime();
	}

	public static Date getHoje() {
		return DateUtils.getDate(LocalDate.now());
	}

	public static Date getAgora() {
		Calendar calendario = getCalendarioCorrente();
		calendario.set(Calendar.YEAR, 0);
		calendario.set(Calendar.MONTH, 0);
		calendario.set(Calendar.DATE, 0);
		return calendario.getTime();
	}

	public static Date getHojeEAgora() {
		return getCalendarioCorrente().getTime();
	}

	private static GregorianCalendar getCalendarioCorrente() {
		// se necessário, este é o ponto para buscar o relógio centralizado.
		return new GregorianCalendar();
	}

	public static String getAnoMes(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return String.format("%4d%02d", calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH) + 1);
	}

	public static int getAno(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.YEAR);
	}

	public static int getSemana(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMes(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.MONTH) + 1;
	}

	public static String getMesString(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int mes = c.get(Calendar.MONTH) + 1;
		return ((mes < 10) ? ("0" + mes) : mes).toString();
	}

	public static int getDia(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHoras(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinutos(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.MINUTE);
	}

	public static int getSegundos(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.SECOND);
	}

	public static boolean isDiaDeSemana(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		int diaDaSemana = calendario.get(Calendar.DAY_OF_WEEK);
		return diaDaSemana != Calendar.SUNDAY && diaDaSemana != Calendar.SATURDAY;
	}

	public static boolean isSabado(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	public static boolean isDomingo(Date data) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	/**
	 * Verifica se a data está dentro do intervalo, incluíndo os limites. Compara dia, mês e ano.
	 * 
	 * @param date
	 *          data
	 * @param begin
	 *          início do intervalo
	 * @param end
	 *          fim do intervalo
	 * @return verdadeiro se a data encontra-se dentro do intervalo
	 * @author rbaraujo
	 * @since 30/09/2009
	 */
	public static boolean isBetween(final Date date, final Date begin, final Date end) {
		int diff1 = compareDayMonthYear(date, begin); // data deve ser maior ou
		// igual
		if (diff1 == 0) { // data igual ao início
			return true;
		}
		int diff2 = compareDayMonthYear(date, end); // data deve ser menor ou
		// igual
		if (diff2 == 0) { // data igual ao fim
			return true;
		}
		// Verdadeiro caso a data seja maior que o início e menor que o fim.
		return diff1 == -1 && diff2 == 1;
	}

	/**
	 * Compara o dia, mês e ano das datas informadas. Retorna 1 quando a primeira data é menor que a segunda data, -1 quando a primeira data é maior que
	 * a segunda data e 0 quando o dia, mês e ano, respectivos das datas, sejam iguais.
	 * 
	 * @param first
	 *          data
	 * @param second
	 *          data
	 * @return 1 => primeira < segunda, -1 => primeira > segunda e 0 => primeira == segunda
	 * @author rbaraujo
	 */
	public static int compareDayMonthYear(final Date first, final Date second) {
		Calendar cFirst = Calendar.getInstance();
		cFirst.setTime(first);
		Calendar cSecond = Calendar.getInstance();
		cSecond.setTime(second);

		if (cFirst.get(Calendar.YEAR) == cSecond.get(Calendar.YEAR)) { // anos
			// iguais
			if (cFirst.get(Calendar.MONTH) == cSecond.get(Calendar.MONTH)) { // meses
				// iguais
				if (cFirst.get(Calendar.DATE) == cSecond.get(Calendar.DATE)) { // dias
					// iguais
					return 0; // datas iguais
				} else if (cFirst.get(Calendar.DATE) > cSecond.get(Calendar.DATE)) {
					return -1; // o dia da primeira data é maior que o dia da
					// segunda
				} else { // o dia da primeira data é menor que o dia da segunda
					return 1;
				}
			} else if (cFirst.get(Calendar.MONTH) > cSecond.get(Calendar.MONTH)) {
				return -1; // o mês da primeira data é maior que o mês da
				// segunda
			} else { // o mês da primeira data é menor que o mês da segunda
				return 1;
			}
		} else if (cFirst.get(Calendar.YEAR) > cSecond.get(Calendar.YEAR)) {
			return -1; // o ano da primeira data é maior que o ano da segunda
		} else { // o ano da primeira data é menor que o ano da segunda
			return 1;
		}
	}

	/**
	 * 
	 * A partir do dia, mês e ano gera uma data
	 * 
	 * @param dia
	 *          da data a montar
	 * @param mes
	 *          da data a montar 1 para Janeiro
	 * @param ano
	 *          da data a montar
	 * @return Date com as horas os minutos e sgundos zerados
	 */
	public static Date getData(Integer dia, Integer mes, Integer ano) {
		dia = (dia != null ? dia : 1);
		mes = (mes != null ? mes : 1);
		ano = (ano != null ? ano : 1);

		if (mes == 2 && dia > 28) {
			dia = 28;
		} else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
			dia = 30;
		}

		--mes;// 0 para janeiro 1 para fevereiro e assim por diante

		Calendar cal = Calendar.getInstance();
		cal.set(ano, mes, dia, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Quantidade de meses que o periodo contemplou EXEMPLO 31/01/2011 Ate 01/02/2011 Retorna 2 pois o periodo esta contemplado em janeiro e fevereiro
	 * EXEMPLO 01/01/2011 até 01/01/2012 retorna 13 pois o periodo esta compreendido entre janeiro a ... dezembro de 2011 + janeiro de 2012 = 13 meses
	 * 
	 * @param inicio
	 * @param fim
	 * @return meses contemplados
	 */
	public static Integer getQuantidadeDeMesesDoPeriodo(Date inicio, Date fim) {
		if (inicio == null) {
			return null;
		}
		if (fim == null) {
			return null;
		}
		Calendar cIni = Calendar.getInstance();
		Calendar cFim = Calendar.getInstance();
		cIni.setTime(inicio);
		cFim.setTime(fim);
		int mes = ((cFim.get(Calendar.YEAR) - cIni.get(Calendar.YEAR)) * 12) + (cFim.get(Calendar.MONTH) - cIni.get(Calendar.MONTH)) + 1;
		return mes;
	}

	public static Date getDataSemHora(Date data) {
		return getDataAjustada(data, PrecisaoDoTempo.DIA);
	}

	/**
	 * Ajusta a data para a precisão informada. Ajustar significa definir todos os valores abaixo da precisão para o menor valor possível. Por exemplo:
	 * na precisão DIA, serão zerados a hora, minutos, segundos e milisegundos.
	 * 
	 * @param dataHora
	 * @param precisaoDoPeriodo
	 * @return A data ajustada para a precisão informada.
	 */
	public static Date getDataAjustada(Date dataHora, PrecisaoDoTempo precisaoDoPeriodo) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(dataHora);
		switch (precisaoDoPeriodo) {
		case ANO:
			calendario.set(Calendar.MONTH, GregorianCalendar.JANUARY);
		case MES:
			calendario.set(Calendar.DAY_OF_MONTH, 1);
		case DIA:
			calendario.set(Calendar.HOUR_OF_DAY, 0);
		case HORA:
			calendario.set(Calendar.MINUTE, 0);
		case MINUTO:
			calendario.set(Calendar.SECOND, 0);
			calendario.set(Calendar.MILLISECOND, 0);
			break;
		default:
			break;
		}
		return calendario.getTime();
	}
}