package br.com.jsbse.integracao.hibernate.tipo;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;

public interface IdentificadorEnumerado {

	public Serializable getValorDoResultSet(ResultSet resultSet, String[] nomes)
			throws HibernateException, SQLException;

	public void setValorDoPreparedStatement(
			PreparedStatement preparedStatement, Object objeto, int indice)
			throws HibernateException, SQLException;

	public int getTipoDoId();
}
