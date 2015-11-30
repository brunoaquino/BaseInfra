package br.com.jsbse.arquitetura.mensagem;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.com.jsbse.arquitetura.excecao.ExcecaoRuntime;
import br.com.jsbse.arquitetura.tipo.TipoDeMensagem;
import br.com.jsbse.arquitetura.util.UtilString;

/**
 * Classe utilitária para centralização de mensagens. Cada mensagem deverá possuir uma chave que a identifica e seu respectivo texto.
 * 
 */
public abstract class FabricaDeMensagens {

	// Definido nas classes que extendem 'FabricaDeMensagens'. Exemplo: 'FabricaDeMensagensUnifar'
	private Properties properties;

	private List<String> chaves;

	public FabricaDeMensagens() {
		properties = doGetPropriedades();

		chaves = new ArrayList<String>();

		// Carrega todas as chaves das mensagens do sistema.
		for (Object o : properties.keySet()) {
			chaves.add((String) o);
		}
	}

	public Mensagem getMensagem(String chave) {
		return getMensagem(chave, null, TipoDeMensagem.ERRO);
	}

	public Mensagem getMensagem(String chave, String parametro) {
		return getMensagem(chave, parametro, TipoDeMensagem.ERRO);
	}

	public Mensagem getMensagem(String chave, String parametro, TipoDeMensagem tipoDaMensagem, String... argumentos) {
		return getMensagemFormatada(getMensagem(chave, parametro, tipoDaMensagem), argumentos);
	}

	public Mensagem getMensagem(String chave, String parametro, String... argumentos) {
		return getMensagemFormatada(getMensagem(chave, parametro), argumentos);
	}

	public Mensagem getMensagem(String chave, String... argumentos) {
		return getMensagemFormatada(getMensagem(chave), argumentos);
	}

	private Mensagem getMensagemFormatada(Mensagem mensagem, String... argumentos) {
		argumentos = UtilString.substituiNuloPorVazio(argumentos);

		String texto = MessageFormat.format(mensagem.getTexto(), (Object[]) argumentos);

		mensagem.setTexto(texto);

		return mensagem;
	}

	public Mensagem getMensagem(String chave, String parametro, TipoDeMensagem tipoDaMensagem) {
		if (UtilString.isVazio(chave))
			throw new ExcecaoRuntime("Falha ao tentar localizar uma mensagem com chave nula ou vazia.");

		String texto = properties.getProperty(chave);
		if (UtilString.isVazio(texto))
			throw new ExcecaoRuntime("Nenhuma mensagem foi localizada com a chave " + chave + ".");

		Mensagem mensagem = new Mensagem();
		mensagem.setId(chave);
		mensagem.setParametro(parametro);
		mensagem.setTipo(tipoDaMensagem);
		mensagem.setTexto(texto);
		return mensagem;
	}

	public List<String> getChaves() {
		return chaves;
	}

	public boolean isTemMensagemComAChave(String chave) {
		return !UtilString.isVazio(doGetPropriedades().getProperty(chave));
	}

	protected abstract Properties doGetPropriedades();
}
