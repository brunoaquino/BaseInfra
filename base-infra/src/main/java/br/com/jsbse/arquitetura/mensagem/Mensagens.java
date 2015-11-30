package br.com.jsbse.arquitetura.mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.jsbse.arquitetura.tipo.TipoDeMensagem;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public class Mensagens implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1058962098387721481L;
	private List<Mensagem> mensagens;

	public void acrescentaMensagem(String chave, String parametro, String... argumentos) {
		acrescentaMensagem(Aplicacao.get().getFabricaDeMensagens().getMensagem(chave, argumentos));
	}

	public void acrescentaMensagem(String chave) {
		acrescentaMensagem(Aplicacao.get().getFabricaDeMensagens().getMensagem(chave, new String[] {}));
	}

	public void acrescentaMensagem(Mensagem mensagem) {
		getMensagens().add(mensagem);
	}

	public void acrescentaMensagens(Mensagens mensagens) {
		getMensagens().addAll(mensagens.getMensagens());
	}

	public void acrescentaMensagemDeErro(String mensagem) {
		getMensagens().add(new Mensagem(TipoDeMensagem.ERRO, mensagem));
	}

	public void acrescentaMensagemDeErro(String parametro, String mensagem) {
		getMensagens().add(new Mensagem(null, TipoDeMensagem.ERRO, mensagem, parametro));
	}

	public void acrescentaMensagemDeInfo(String mensagem) {
		getMensagens().add(new Mensagem(TipoDeMensagem.INFO, mensagem));
	}

	public void limpaMensagens() {
		getMensagens().clear();
	}

	public List<Mensagem> getMensagens() {
		if (mensagens == null) {
			mensagens = new ArrayList<Mensagem>();
		}
		return mensagens;
	}

	public boolean isTemErro() {
		for (Mensagem mensagem : getMensagens()) {
			if (mensagem.getTipo() == TipoDeMensagem.ERRO)
				return true;
		}
		return false;
	}

	public boolean isTemInfo() {
		for (Mensagem mensagem : getMensagens()) {
			if (mensagem.getTipo() == TipoDeMensagem.INFO)
				return true;
		}
		return false;
	}
}
