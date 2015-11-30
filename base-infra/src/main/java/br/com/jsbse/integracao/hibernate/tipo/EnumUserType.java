package br.com.jsbse.integracao.hibernate.tipo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistente;

/**
 * 
 * Classe responsável pelo mapeamento de enums para o hibernate
 * 
 * @param <E>
 *            Classe do enum que será mapeado.
 */
public abstract class EnumUserType<E extends EnumeradoPersistente> implements UserType {
	private Class<E> clazz = null;
	private IdentificadorEnumerado identificador;

	public EnumUserType() {
		this(TipoIdentificadorDeEnumerados.STRING);
	}

	@SuppressWarnings("unchecked")
	public EnumUserType(TipoIdentificadorDeEnumerados tipo) {
		this.identificador = FabricaDeIdentificadores.getIdentificador(tipo);
		this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public int[] sqlTypes() {
		return new int[] { identificador.getTipoDoId() };
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return clazz;
	}

	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		Serializable id = identificador.getValorDoResultSet(resultSet, names);
		E result = null;
		if (!resultSet.wasNull()) {
			E valor = null;
			for (E e : clazz.getEnumConstants()) {
				if (e.getId().equals(id)) {
					valor = e;
					break;
				}
			}
			result = valor;
		}
		return result;
	}

	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session) throws HibernateException,
			SQLException {
		if (null == value) {
			preparedStatement.setNull(index, identificador.getTipoDoId());
		} else {
			identificador.setValorDoPreparedStatement(preparedStatement, value, index);
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		if (null == x || null == y)
			return false;
		return x.equals(y);
	}

}
