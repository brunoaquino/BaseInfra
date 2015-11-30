package br.com.jsbse.arquitetura.excecao;

import java.sql.SQLException;

import java.util.Iterator;

import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.mensagem.Mensagens;
import br.com.jsbse.arquitetura.tipo.TipoDeMensagem;

public class ExcecaoInfraestrutura extends RuntimeException implements ExcecaoArquitetura {
	
	private static final long serialVersionUID = -1587613550523664070L;

	private Mensagens mensagens = new Mensagens();

	public ExcecaoInfraestrutura(String mensagem) {
		super(mensagem);
		mensagens.acrescentaMensagemDeErro(mensagem);
	}

	public ExcecaoInfraestrutura(String mensagem, Throwable e) {
		super(mensagem, e);
		mensagens.acrescentaMensagemDeErro(mensagem);
	}

	public ExcecaoInfraestrutura(Mensagem mensagem) {
		super(mensagem.getTexto());
		this.mensagens.acrescentaMensagem(mensagem);
	}

	public ExcecaoInfraestrutura(Mensagens mensagens) {
		super(getMensagemDeErro(mensagens));
		this.mensagens = mensagens;
	}

	public ExcecaoInfraestrutura(Throwable e) {
		super(getMensagemDeErro(e), e);
		Mensagens mensagens = getMensagens(e);
		if (mensagens != null) {
			this.mensagens = mensagens;
		} else {
			this.mensagens.acrescentaMensagem(new Mensagem(TipoDeMensagem.ERRO, e.getMessage()));
		}
	}

	public ExcecaoInfraestrutura(Throwable e, Mensagens mensagens) {
		super(getMensagemDeErro(mensagens).toString(), e);
		this.mensagens = mensagens;
	}

	public Mensagens getMensagens() {
		return mensagens;
	}

	private static String getMensagemDeErro(Throwable e) {
		if (e instanceof ExcecaoArquitetura) {
			ExcecaoArquitetura excecao = (ExcecaoArquitetura) e;
			return getMensagemDeErro(excecao.getMensagens());
		} else if (e instanceof SQLException) {
			return e.getMessage();
		}
		return "";
	}

	private static Mensagens getMensagens(Throwable e) {
		if (e instanceof ExcecaoArquitetura) {
			ExcecaoArquitetura excecao = (ExcecaoArquitetura) e;
			return excecao.getMensagens();
		}
		return null;
	}

	public static String getMensagemDeErro(Mensagens mensagens) {
		StringBuilder msg = new StringBuilder();
		for (Iterator<Mensagem> iterator = mensagens.getMensagens().iterator(); iterator.hasNext();) {
			Mensagem mensagem = iterator.next();
			if (mensagem.getParametro() != null && !mensagem.getParametro().trim().equals("")) {
				msg.append(mensagem.getParametro());
				msg.append(": ");
			}
			msg.append(mensagem.getTexto());
			if (iterator.hasNext())
				msg.append("\n");
		}
		return msg.toString();
	}
}