package br.com.jsbse.integracao.hibernate.criterion;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class TSQLFunctions {

	public TSQLFunctions() {
	}

	/**
	 * Gera o SQL da função (Transact-SQL).<br>
	 * O ano será extraído da data informada.
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          data de onde será extraído o ano
	 * @return Criterion
	 */
	public static Criterion year(String property, Date value) {
		if (value == null) {
			throw new IllegalArgumentException("A data não pode ser nula.");
		}
		if (property == null || property.length() == 0) {
			throw new IllegalArgumentException("A propriedade não pode ser nula.");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(value);
		return new TSQLFunction(property, TSQLFunctionType.YEAR, c.get(Calendar.YEAR));
	}

	/**
	 * Gera o SQL da função YEAR (Transact-SQL).
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          string representando o ano no formato YYYY.<br>
	 *          Ex.: "2008".
	 * @return Criterion
	 */
	public static Criterion year(String property, String value) {
		return new TSQLFunction(property, TSQLFunctionType.YEAR, value);
	}

	/**
	 * Gera o SQL da função YEAR (Transact-SQL).
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          inteiro representando o ano
	 * @return Criterion
	 */
	public static Criterion year(String property, Integer value) {
		return new TSQLFunction(property, TSQLFunctionType.YEAR, value);
	}

	/**
	 * Gera o SQL da função MONTH (Transact-SQL).<br>
	 * O mês será extraído da data informada.
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          data de onde será extraído o mês
	 * @return Criterion
	 */
	public static Criterion month(String property, Date value) {
		if (value == null) {
			throw new IllegalArgumentException("A data não pode ser nula.");
		}
		if (property == null || property.length() == 0) {
			throw new IllegalArgumentException("A propriedade não pode ser nula.");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(value);
		return new TSQLFunction(property, TSQLFunctionType.MONTH, c.get(Calendar.MONTH) + 1);
	}

	/**
	 * Gera o SQL da função MONTH (Transact-SQL).<br>
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          string com um número representado o mês.<br>
	 *          Ex.: "1" ou "01" para janeiro, "2" ou "02" para fevereiro.
	 * @return Criterion
	 */
	public static Criterion month(String property, String value) {
		return new TSQLFunction(property, TSQLFunctionType.MONTH, new Integer(value));
	}

	/**
	 * Gera o SQL da função MONTH (Transact-SQL).<br>
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          inteiro representando o mês.<br>
	 *          Ex.: janeiro = 1, fevereiro = 2.
	 * @return Criterion
	 */
	public static Criterion month(String property, Integer value) {
		return new TSQLFunction(property, TSQLFunctionType.MONTH, value);
	}

	/**
	 * Gera o SQL da função DAY (Transact-SQL).<br>
	 * O dia será extraído da data informada.
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          data de onde será extraído o dia
	 * @return Criterion
	 */
	public static Criterion day(String property, Date value) {
		if (value == null) {
			throw new IllegalArgumentException("A data não pode ser nula.");
		}
		if (property == null || property.length() == 0) {
			throw new IllegalArgumentException("A propriedade não pode ser nula.");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(value);
		return new TSQLFunction(property, TSQLFunctionType.DAY, c.get(Calendar.DATE));
	}

	/**
	 * Gera o SQL da função DAY (Transact-SQL).<br>
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          string com um número representado o dia.<br>
	 *          Ex.: "1", "01", "2", "02", etc..
	 * @return Criterion
	 */
	public static Criterion day(String property, String value) {
		return new TSQLFunction(property, TSQLFunctionType.DAY, new Integer(value));
	}

	/**
	 * Gera o SQL da função DAY (Transact-SQL).<br>
	 * 
	 * @param property
	 *          propriedade a ser comparada
	 * @param value
	 *          inteiro representando o mês.
	 * @return Criterion
	 */
	public static Criterion day(String property, Integer value) {
		return new TSQLFunction(property, TSQLFunctionType.DAY, value);
	}

	/**
	 * Gera o SQL para comparação somente da data de um campo do tipo datetime. <br>
	 * Ex.:<br>
	 * (DAY('2008-01-01 12:30:52') = DAY('2008-01-01 13:30:50') AND <br>
	 * MONTH('2008-01-01 12:30:52') = MONTH('2008-01-01 13:30:50') AND <br>
	 * YEAR('2008-01-01 12:30:52') = YEAR('2008-01-01 13:30:50'))
	 * 
	 * @param property
	 * @param data
	 * @return
	 */
	public static Criterion onlyDate(String property, Date data) {
		return Restrictions.and(TSQLFunctions.day(property, data),
				Restrictions.and(TSQLFunctions.month(property, data), TSQLFunctions.year(property, data)));
	}
}
