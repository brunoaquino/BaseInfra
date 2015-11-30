package base.infra.ambiente;

import org.springframework.core.env.Environment;

import br.com.jsbse.arquitetura.spring.ContextoSpring;

public class Ambiente {

	public static final String LOCAL = "local";
	public static final String DEV = "dev";
	public static final String PROD = "prod";
	
	public static String getNome() {
		Environment env = ContextoSpring.getContextoDaAplicacao().getEnvironment();
		return env.getActiveProfiles()[0];
	}

	public static boolean isLocal() {
		return LOCAL.equals(getNome());
	}


	public static boolean isDev() {
		return DEV.equals(getNome());
	}

	public static boolean isProd() {
		return PROD.equals(getNome());
	}
}
