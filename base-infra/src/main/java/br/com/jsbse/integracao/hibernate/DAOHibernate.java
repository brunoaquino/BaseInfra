package br.com.jsbse.integracao.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.integracao.DAO;

public interface DAOHibernate extends DAO {

	Session getHibernateSession();

	public <T extends Entidade<ID>, ID extends Serializable> Criteria criaCriteria(Class<T> tipoDaClasse);
	
	public <T extends Entidade<ID>, ID extends Serializable> Criteria criaCriteria(Class<T> tipoDaClasse, String alias);

	public SessionFactory getSessionFactory();
}
