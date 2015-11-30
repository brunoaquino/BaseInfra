package br.com.jsbse.arquitetura.excecao;

import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.mensagem.Mensagens;

public class ExcecaoUsuarioNaoAutenticado extends ExcecaoRuntime {
	 
    private static final long serialVersionUID = -618210585197817999L;
 
    public ExcecaoUsuarioNaoAutenticado(String mensagem) {
        super(mensagem);
    }
 
    public ExcecaoUsuarioNaoAutenticado(String mensagem, Throwable e) {
        super(e);
    }
 
    public ExcecaoUsuarioNaoAutenticado(Mensagem mensagem) {
        super(mensagem);
    }
 
    public ExcecaoUsuarioNaoAutenticado(Mensagens mensagens) {
        super(mensagens);
    }
 
    public ExcecaoUsuarioNaoAutenticado(Throwable e) {
        super(e);
    }
 
    public ExcecaoUsuarioNaoAutenticado(Throwable e, Mensagens mensagens) {
        super(e, mensagens);
    }
 
}
