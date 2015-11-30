package br.com.jsbse.integracao.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.integracao.RepositorioBase;

public class RepositorioHibernateBase extends RepositorioBase {

	protected <T extends Entidade<ID>, ID extends Serializable> Criteria criaCriteria(Class<T> classe) {
		DAOHibernate4 dao = (DAOHibernate4) getDAO();
		return dao.criaCriteria(classe);
	}
	
	protected <T extends Entidade<ID>, ID extends Serializable> Criteria criaCriteria(Class<T> classe, String alias) {
		DAOHibernate dao = (DAOHibernate) getDAO();
		return dao.criaCriteria(classe, alias);
	}

	protected Session getSession() {
		DAOHibernate dao = (DAOHibernate) getDAO();
		return dao.getHibernateSession();
	}

	protected Query criaQuery(String query) {
		DAOHibernate dao = (DAOHibernate) getDAO();
		return dao.getHibernateSession().createQuery(query);
	}

	protected SQLQuery criaSQLQuery(String query) {
		DAOHibernate dao = (DAOHibernate) getDAO();
		return dao.getHibernateSession().createSQLQuery(query);
	}

}
