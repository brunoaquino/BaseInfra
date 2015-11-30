package br.com.jsbse.integracao.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.StandardBasicTypes;

public class TSQLFunction implements Criterion {

	/**
	 * Atributo serialVersionUID do tipo long
	 */
	private static final long serialVersionUID = -2425616877320094379L;

	private String property;

	private String value;

	private TSQLFunctionType tSQLFunctionType;

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property
	 *          the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *          the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the tSQLFunctionType
	 */
	public TSQLFunctionType getTSQLFunctionType() {
		return tSQLFunctionType;
	}

	/**
	 * @param functionType
	 *          the tSQLFunctionType to set
	 */
	public void setTSQLFunctionType(TSQLFunctionType functionType) {
		tSQLFunctionType = functionType;
	}

	protected TSQLFunction(String property, TSQLFunctionType extractType, String value) {
		this.property = property;
		this.tSQLFunctionType = extractType;
		this.value = value;
	}

	protected TSQLFunction(String property, TSQLFunctionType extractType, Integer value) {
		this.property = property;
		this.tSQLFunctionType = extractType;
		this.value = value.toString();
	}

	public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		return new TypedValue[] { new TypedValue(StandardBasicTypes.STRING, value) };
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
		String column = criteriaQuery.getColumnsUsingProjection(criteria, property)[0];
		return "( " + getTSQLFunctionType().toString() + "(" + column + ") = ? )";
	}

}
