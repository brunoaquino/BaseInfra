package br.com.jsbse.integracao.hibernate.tipo;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistente;

public class IdentificadorEnumeradoString implements IdentificadorEnumerado {

	@Override
	public int getTipoDoId() {
		return Types.VARCHAR;
	}

	@Override
	public Serializable getValorDoResultSet(ResultSet resultSet, String[] nomes)
			throws HibernateException, SQLException {
		return resultSet.getString(nomes[0]);
	}

	@Override
	public void setValorDoPreparedStatement(
			PreparedStatement preparedStatement, Object objeto, int indice)
			throws HibernateException, SQLException {
		preparedStatement.setString(indice, (String) ((EnumeradoPersistente) objeto)
				.getId());
	}

}
