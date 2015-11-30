package br.com.jsbse.arquitetura.servico;

import java.io.Serializable;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.excecao.ExcecaoRuntime;
import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.integracao.Repositorio;
import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.mensagem.Mensagens;
import br.com.jsbse.arquitetura.validacao.UtilValidador;
import br.com.jsbse.arquitetura.validacao.ValidadorDeDados;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public class ServicoBase {

	private ThreadLocal<Mensagens> mensagens;

	public Mensagens getMensagens() {
		if (mensagens == null) {
			mensagens = new ThreadLocal<Mensagens>() {

				@Override
				protected Mensagens initialValue() {
					return new Mensagens();
				}
			};
		}
		return mensagens.get();
	}

	protected void acrescentaMensagem(Mensagem mensagem) {
		getMensagens().acrescentaMensagem(mensagem);
	}

	/**
	 * 
	 * @param chave - chave no arquivo mensagens.properties.
	 * @param parametro - tag de identificação na tela.
	 * @param argumentos - argumentos do mensagens.properties.
	 */
	protected void acrescentaMensagem(String chave, String parametro, String... argumentos) {
		getMensagens().acrescentaMensagem(chave, parametro, argumentos);
	}

//	protected void acrescentaMensagem(String chave) {
//		getMensagens().acrescentaMensagem(chave, null, new String[] {});
//	}

	protected void acrescentaMensagem(String parametro, String mensagem) {
		getMensagens().acrescentaMensagemDeErro(parametro, mensagem);
	}

	protected void acrescentaMensagemDeErro(String mensagem) {
		getMensagens().acrescentaMensagemDeErro(mensagem);
	}

	protected void acrescentaMensagemDeInfo(String mensagem) {
		getMensagens().acrescentaMensagemDeInfo(mensagem);
	}

	public void levantaExcecaoSeTemErro() throws ExcecaoRuntime {
		Mensagens mensagensAux = getMensagens();
		limpaMensagens();
		if (mensagensAux.isTemErro()) {
			throw new ExcecaoRuntime(mensagensAux);
		}
	}

	public void limpaMensagens() {
		if (mensagens != null) {
			mensagens.remove();
		}
	}

	protected DAO getDAO() {
		return Aplicacao.get().getDAO();
	}

	protected <T extends Repositorio> T getRepositorio(Class<T> interfaceDoRepositorio) {
		return Aplicacao.get().getRepositorio(interfaceDoRepositorio);
	}

	public <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, ID id,
			String parametro) {
		return ValidadorDeDados.erroSeIndefinidoOuInexistente(tipoDaClasse, id, parametro, getMensagens());
	}

	public <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, ID id,
			String parametro, boolean acrescentaMensagem) {
		return ValidadorDeDados.erroSeIndefinidoOuInexistente(tipoDaClasse, id, parametro, getMensagens(), acrescentaMensagem);
	}

	public <E extends Entidade<ID>, ID extends Serializable> E erroSeDefinidoEInexistente(Class<E> tipoDaClasse, ID id,
			String parametro) {
		return ValidadorDeDados.erroSeDefinidoEInexistente(tipoDaClasse, id, parametro, getMensagens());
	}

	public boolean erroSeIndefinido(Object objeto, String parametro) {
		return UtilValidador.erroSeIndefinido(objeto, parametro, getMensagens());
	}

	public boolean erroSeZeroOuVazio(Object objeto, String parametro) {
		return UtilValidador.erroSeZeroOuVazio(objeto, parametro, getMensagens());
	}
}