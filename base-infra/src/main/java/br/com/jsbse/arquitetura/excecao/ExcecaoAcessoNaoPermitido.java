package br.com.jsbse.arquitetura.excecao;

import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.mensagem.Mensagens;

public class ExcecaoAcessoNaoPermitido extends ExcecaoRuntime {

	/**
     * 
     */
	private static final long serialVersionUID = -618210585197817999L;

	public ExcecaoAcessoNaoPermitido(String mensagem) {
		super(mensagem);
	}

	public ExcecaoAcessoNaoPermitido(String mensagem, Throwable e) {
		super(e);
	}

	public ExcecaoAcessoNaoPermitido(Mensagem mensagem) {
		super(mensagem);
	}

	public ExcecaoAcessoNaoPermitido(Mensagens mensagens) {
		super(mensagens);
	}

	public ExcecaoAcessoNaoPermitido(Throwable e) {
		super(e);
	}

	public ExcecaoAcessoNaoPermitido(Throwable e, Mensagens mensagens) {
		super(e, mensagens);
	}

}
