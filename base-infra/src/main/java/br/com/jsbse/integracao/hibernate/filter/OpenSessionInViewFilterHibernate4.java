package br.com.jsbse.integracao.hibernate.filter;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

import br.com.jsbse.arquitetura.spring.ContextoSpring;

public class OpenSessionInViewFilterHibernate4 extends OpenSessionInViewFilter {
	@Override
	protected SessionFactory lookupSessionFactory() {
		return ContextoSpring.getBean("sessionFactory");
	}
}
