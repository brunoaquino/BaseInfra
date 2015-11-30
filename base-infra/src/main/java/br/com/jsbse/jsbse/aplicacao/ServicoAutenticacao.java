package br.com.jsbse.jsbse.aplicacao;

import br.com.jsbse.arquitetura.excecao.ExcecaoInfraestrutura;

public interface ServicoAutenticacao {

	public UsuarioBase getUsuarioBasePeloLogin(String login) throws ExcecaoInfraestrutura;
}
