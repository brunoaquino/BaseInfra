package base.util;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

		//XXX - Revisar se esta é a melhor maneira de implementar o UUID.
		return UUIDGenerator.generate();

	}
	
	public static String generate() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
