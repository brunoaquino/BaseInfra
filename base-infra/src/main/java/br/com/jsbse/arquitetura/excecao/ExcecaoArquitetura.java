package br.com.jsbse.arquitetura.excecao;

import br.com.jsbse.arquitetura.mensagem.Mensagens;

public interface ExcecaoArquitetura {

	public Throwable getCause();

	public Mensagens getMensagens();
}
