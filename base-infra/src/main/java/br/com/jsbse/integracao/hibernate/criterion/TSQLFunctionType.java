package br.com.jsbse.integracao.hibernate.criterion;

/**
 * Tipo de fun��o SQL. <br>
 * <b>Projeto:</b> spry-integracao-hibernate <br>
 * <b>Classe:</b>
 * br.com.gt1.spry.integracao.hibernate.criterion.mssql.TSQLFunctionType .java <br>
 * <b>Copyright:</b> Copyright (c) 2008 Evoluti <br>
 * <b>Companhia:</b> Evoluti Tecnologia e Servi�os LTDA
 * 
 * @author rbaraujo
 * @since 07/04/2009
 */
public enum TSQLFunctionType {

	/**
	 * YEAR.
	 */
	YEAR(1, "YEAR"),
	/**
	 * MONTH.
	 */
	MONTH(2, "MONTH"),
	/**
	 * DAY.
	 */
	DAY(3, "DAY");

	/**
	 *
	 */
	private String function;

	/**
	 *
	 */
	private int id;

	/**
	 * @param i
	 *            identificador
	 * @param func
	 *            função
	 */
	private TSQLFunctionType(final Integer i, final String func) {
		this.id = i;
		this.function = func;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param i
	 *            the id to set
	 */
	public void setId(Integer i) {
		this.id = i;
	}

	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @param func
	 *            the function to set
	 */
	public void setFunction(String func) {
		this.function = func;
	}

	/**
	 * Retorna o nome da função.
	 * 
	 * @return nome da função
	 */
	@Override
	public final String toString() {
		return this.getFunction();
	}

}
