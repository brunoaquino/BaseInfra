package br.com.jsbse.arquitetura.excecao;

import java.util.Iterator;

import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.mensagem.Mensagens;

public class ExcecaoRuntime extends RuntimeException implements
		ExcecaoArquitetura {

	private static final long serialVersionUID = -7320646502518537814L;

	private Mensagens mensagens = new Mensagens();

	public ExcecaoRuntime(String mensagem) {
		super(mensagem);
		this.mensagens.acrescentaMensagemDeErro(mensagem);
	}

	public ExcecaoRuntime(String mensagem, Throwable e) {
		super(e);
		this.mensagens.acrescentaMensagemDeErro(mensagem);
	}

	public ExcecaoRuntime(Mensagem mensagem) {
		mensagens.acrescentaMensagem(mensagem);
	}

	public ExcecaoRuntime(Mensagens mensagens) {
		this.mensagens = mensagens;
	}

	public ExcecaoRuntime(Throwable e) {
		super(getMensagemDeErro(e), e);
	}

	public ExcecaoRuntime(Throwable e, Mensagens mensagens) {
		super(getMensagemDeErro(mensagens), e);
	}

	public Mensagens getMensagens() {
		return mensagens;
	}

	private static String getMensagemDeErro(Throwable e) {
		if (e instanceof ExcecaoInfraestrutura) {
			ExcecaoInfraestrutura excecao = (ExcecaoInfraestrutura) e;
			return getMensagemDeErro(excecao.getMensagens());
		}
		return "";
	}

	private static String getMensagemDeErro(Mensagens mensagens) {
		StringBuilder mensagem = new StringBuilder();
		for (Iterator<Mensagem> iterator = mensagens.getMensagens().iterator(); iterator
				.hasNext();) {
			Mensagem msg = iterator.next();
			if (msg.getParametro() != null
					&& !msg.getParametro().trim().equals("")) {
				mensagem.append(msg.getParametro());
				mensagem.append(": ");
			}
			mensagem.append(msg.getTexto());

			if (iterator.hasNext())
				mensagem.append("\n\t");
		}
		return mensagem.toString();
	}

	@Override
	public String getMessage() {
		String mensagem = super.getMessage() != null ? super.getMessage() : "";
		if (mensagem != null) {
			mensagem += getMensagemDeErro(getMensagens());
		} else {
			mensagem = getMensagemDeErro(getMensagens());
		}
		return mensagem;
	}

}
