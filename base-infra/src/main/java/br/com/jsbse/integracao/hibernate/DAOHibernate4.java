package br.com.jsbse.integracao.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.entidade.EntidadeEL;
import br.com.jsbse.arquitetura.entidade.ParametroDeConsulta;
import br.com.jsbse.arquitetura.integracao.ClassesPersistentes;

public class DAOHibernate4 implements DAOHibernate {

	@Autowired
	private SessionFactory sessionFactory;

	public <T extends Entidade<ID>, ID extends Serializable> void persisteObjeto(T objeto) {
		getHibernateSession().saveOrUpdate(objeto);
	}

	public <T extends Entidade<ID>, ID extends Serializable> void excluiObjeto(T objeto) {
		if (EntidadeEL.class.isAssignableFrom(objeto.getClass())) {
			((EntidadeEL<ID>) objeto).setExcluido(true);
			persisteObjeto(objeto);
		} else {
			getHibernateSession().delete(objeto);
		}
	}

	public <T extends Entidade<ID>, ID extends Serializable> void excluiObjetos(Collection<T> lista) {
		if (lista != null) {
			for (T entidade : lista) {
				excluiObjeto(entidade);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Entidade<ID>, ID extends Serializable> T getObjeto(Class<T> tipoDaClasse, ID id) {
		return (T) getHibernateSession().get(tipoDaClasse, id);
	}

	@SuppressWarnings("unchecked")
	public <T extends Entidade<ID>, ID extends Serializable> Collection<T> getObjetos(Class<T> tipoDaClasse, Serializable[] ids) {
		Criteria criteria = criaCriteria(tipoDaClasse);
		criteria.add(Restrictions.in("id", ids));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T extends Entidade<ID>, ID extends Serializable> Collection<T> getObjetos(Class<T> tipoDaClasse) {
		return criaCriteria(tipoDaClasse).list();
	}

	@SuppressWarnings("unchecked")
	public <T extends Entidade<ID>, ID extends Serializable> T getObjetoPelaDescricao(Class<T> tipoDaClasse, String descricao) {
		Criteria criteria = criaCriteria(tipoDaClasse);
		criteria.add(Restrictions.eq("descricao", descricao).ignoreCase());
		return (T) criteria.uniqueResult();
	}

	public Session getHibernateSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public ClassesPersistentes getClassesPersistentes() {
		ClassesPersistentes classes = new ClassesPersistentes();
		try {
			for (Iterator<String> iterator = sessionFactory.getAllClassMetadata().keySet().iterator(); iterator.hasNext();) {
				classes.acrescentaClassePersistente((Class<? extends Entidade<?>>) Class.forName(iterator.next()));
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao tentar ler as classes mapeadas do hibernate.");
		}
		return classes;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entidade<ID>, ID extends Serializable> T getObjetoPorParametros(Class<T> tipoDaClasse, ParametroDeConsulta... parametros) {
		Criteria criteria = getHibernateSession().createCriteria(tipoDaClasse);
		for (ParametroDeConsulta parametro : parametros) {
			criteria.add(Restrictions.eq(parametro.getCampo(), parametro.getValor()).ignoreCase());
		}
		criteria.setMaxResults(1);
		return (T) criteria.uniqueResult();
	}

	public <T extends Entidade<ID>, ID extends Serializable> Criteria criaCriteria(Class<T> tipoDaClasse) {
		return getHibernateSession().createCriteria(tipoDaClasse);
	}

	@Override
	public <T extends Entidade<ID>, ID extends Serializable> Criteria criaCriteria(Class<T> tipoDaClasse, String alias) {
		return getHibernateSession().createCriteria(tipoDaClasse, alias);
	}

	@Override
	public <T extends Entidade<ID>, ID extends Serializable> void merge(T objeto) {
		getHibernateSession().merge(objeto);
	}
}
