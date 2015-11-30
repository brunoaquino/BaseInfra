package br.com.jsbse.arquitetura.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import br.com.jsbse.arquitetura.spring.ContextoSpring;

@Aspect
@Component
public class OpenSessionInterceptor {

	protected final Log logger = LogFactory.getLog(getClass());

	@Around("@annotation(base.util.annotation.OpenSession)")
	public Object processar(ProceedingJoinPoint pjp) throws Throwable {

		SessionFactory sessionFactory = ContextoSpring.getBean(SessionFactory.class);

		boolean participate = false;

		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			// Do not modify the Session: just set the participate flag.
			participate = true;
		} else {
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			SessionHolder sessionHolder = new SessionHolder(session);
			TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);
		}

		try {
			return pjp.proceed();
		}

		finally {
			if (!participate) {
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}

	}

	/**
	 * Open a Session for the SessionFactory that this filter uses.
	 * <p>
	 * The default implementation delegates to the {@link SessionFactory#openSession} method and sets the {@link Session}'s flush mode to "MANUAL".
	 * 
	 * @param sessionFactory
	 *          the SessionFactory that this filter uses
	 * @return the Session to use
	 * @throws DataAccessResourceFailureException
	 *           if the Session could not be created
	 * @see org.hibernate.FlushMode#MANUAL
	 */
	protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		try {
			Session session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.MANUAL);
			return session;
		} catch (HibernateException ex) {
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}
}
