package br.com.jsbse.integracao.hibernate.tipo;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistente;

public class IdentificadorEnumeradoInteger implements IdentificadorEnumerado {

	@Override
	public int getTipoDoId() {
		return Types.INTEGER;
	}

	@Override
	public Serializable getValorDoResultSet(ResultSet resultSet, String[] nomes) throws HibernateException, SQLException {
		return resultSet.getInt(nomes[0]);
	}

	@Override
	public void setValorDoPreparedStatement(PreparedStatement preparedStatement, Object objeto, int indice) throws HibernateException, SQLException {
		preparedStatement.setInt(indice, Integer.parseInt(((EnumeradoPersistente) objeto).getId()));
	}
}
