package br.com.jsbse.integracao.hibernate.filter;

import org.hibernate.SessionFactory;

import br.com.jsbse.arquitetura.spring.ContextoSpring;

public class OpenSessionInViewFilterHibernate3 extends org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {

	@Override
	protected SessionFactory lookupSessionFactory() {
		return ContextoSpring.getBean("sessionFactory");
	}
}
