package br.com.jsbse.arquitetura.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ContextoSpring {
	private static Map<String, ApplicationContext> contextos = new HashMap<String, ApplicationContext>();

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return getContextoDaAplicacao().getBean(requiredType);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String nome) {
		for (ApplicationContext contexto : contextos.values()) {
			if (contexto.containsBean(nome)) {
				return (T) contexto.getBean(nome);
			}
		}
		return null;
	}

	public static void acrescentaContexto(String nome, ApplicationContext contexto) {
		getContextos().put(nome, contexto);
	}

	public static void carregaContexto(String nomeDoArquivo) {
		try {
			ResourcePatternResolver resolver = getResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:/*.xml");
			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];

				// Carrega apenas o contexto escolhido.
				if (nomeDoArquivo != null && !nomeDoArquivo.equals(resource.getFilename()))
					continue;

				String chave = resource.getFilename();
				chave = chave.substring(chave.indexOf("-") + 1, chave.indexOf("."));

				if (contextos.containsKey(chave))
					continue;

				ApplicationContext contexto = new ClassPathXmlApplicationContext(resource.getFilename());
				contextos.put(chave, contexto);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	private static ResourcePatternResolver getResourcePatternResolver() {
		return ContextoSpring.getResourcePatternResolver(null);
	}

	private static ResourcePatternResolver getResourcePatternResolver(ClassLoader classLoader) {
		if (classLoader != null)
			return new PathMatchingResourcePatternResolver(classLoader);

		return new PathMatchingResourcePatternResolver();
	}

	public static Map<String, ApplicationContext> getContextos() {
		return contextos;
	}

	public static ApplicationContext getContextoDaAplicacao(String nomeDaAplicacao) {
		return getContextos().get(nomeDaAplicacao);
	}

	public static ApplicationContext getContextoDaAplicacao() {
		return getContextos().values().iterator().next();
	}
}