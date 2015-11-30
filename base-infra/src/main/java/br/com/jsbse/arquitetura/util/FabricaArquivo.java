package br.com.jsbse.arquitetura.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import br.com.jsbse.arquitetura.excecao.ExcecaoRuntime;

public final class FabricaArquivo {
	private static FabricaArquivo instancia;

	/**
	 * Construtor
	 */
	private FabricaArquivo() {
		// Construtor
	}

	public static FabricaArquivo getInstance() {
		if (instancia == null) {
			instancia = new FabricaArquivo();
		}
		return instancia;
	}

	/**
	 * 
	 * @param caminhoDoArquivo
	 * @return Properties carregado com os dados do arquivo passado por par�metro.
	 */
	public Properties criaProperties(String caminhoDoArquivo) {
		Properties properties = criaProperties();

		if (!UtilString.isVazio(caminhoDoArquivo)) {
			InputStream is = criaInputStream(caminhoDoArquivo);
			if (is != null) {
				try {
					properties.load(is);
				} catch (IOException e) {
					throw new ExcecaoRuntime("Erro ao carregar arquivo " + caminhoDoArquivo + ".", e);
				}
			}
		}
		return properties;
	}

	/**
	 * 
	 * @param InputStream
	 *          do arquivo.
	 * @return Properties carregado com os dados do arquivo passado por par�metro.
	 */
	public Properties criaProperties(InputStream is) {
		Properties properties = criaProperties();

		if (is != null) {
			try {
				properties.load(is);
			} catch (IOException e) {
				throw new ExcecaoRuntime("Erro ao carregar arquivo properties pelo InputStream.", e);
			}
		}
		return properties;
	}

	/**
	 * 
	 * @param caminhoDoArquivo
	 * @return O InputStream do caminho do arquivo informado.
	 */
	public InputStream criaInputStream(String caminhoDoArquivo) {
		InputStream is = null;

		if (!UtilString.isVazio(caminhoDoArquivo)) {
			is = criaFileInputStream(caminhoDoArquivo);
			is = criaInputStreamDoClassLoader(is, caminhoDoArquivo);
			is = criaInputStreamDaClasse(is, caminhoDoArquivo);
		}

		return is;
	}

	/**
	 * 
	 * @param caminhoDoArquivo
	 * @return O FileInputStream do caminho do arquivo.
	 */
	public InputStream criaFileInputStream(String caminhoDoArquivo) {
		FileInputStream fis = null;

		if (!UtilString.isVazio(caminhoDoArquivo)) {
			try {
				fis = new FileInputStream(caminhoDoArquivo);
			} catch (FileNotFoundException e) {
			}
		}
		return fis;
	}

	/**
	 * 
	 * @param is
	 * @param caminhoDoArquivo
	 * @return O input stream recuperado do class loader.
	 */
	private InputStream criaInputStreamDoClassLoader(InputStream is, String caminhoDoArquivo) {
		if (is == null) {
			is = getClassLoader().getResourceAsStream(caminhoDoArquivo);
		}
		return is;
	}

	/**
	 * 
	 * @param is
	 * @param caminhoDoArquivo
	 * @return O InputStream recuperado da classe FabricaDeArquivos
	 */
	private InputStream criaInputStreamDaClasse(InputStream is, String caminhoDoArquivo) {
		if (is == null) {
			is = FabricaArquivo.class.getResourceAsStream(caminhoDoArquivo);
		}
		return is;
	}

	public File carregaArquivo(String nome) {
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources("classpath*:" + nome);
			if (resources.length == 0)
				throw new ExcecaoRuntime("O arquivo " + nome + " não foi encontrado.");

			return resources[0].getFile();
		} catch (IOException e) {
			throw new ExcecaoRuntime(e);
		}

	}

	public Properties criaProperties() {
		return new Properties();
	}

	private ClassLoader getClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			cl = getClass().getClassLoader();
		}
		return cl;
	}
}
