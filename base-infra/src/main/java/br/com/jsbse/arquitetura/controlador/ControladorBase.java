package br.com.jsbse.arquitetura.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import base.util.BaseMessages;
import base.util.Dto;
import br.com.jsbse.arquitetura.excecao.ExcecaoAcessoNaoPermitido;
import br.com.jsbse.arquitetura.excecao.ExcecaoArquitetura;
import br.com.jsbse.arquitetura.excecao.ExcecaoInfraestrutura;
import br.com.jsbse.arquitetura.excecao.ExcecaoRuntime;
import br.com.jsbse.arquitetura.excecao.ExcecaoUsuarioNaoAutenticado;
import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.tipo.TipoDeMensagem;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public abstract class ControladorBase {

	@Autowired
	protected HttpServletRequest request;

	protected DAO getDAO() {
		return Aplicacao.get().getDAO();
	}

	protected MensagemDeResposta getMensagemDeResposta(BaseMessages message) {
		MensagemDeResposta mensagem = new MensagemDeResposta();
		mensagem.setId(message.getId());
		mensagem.setTipo(getTipo(message.getTipo()));
		mensagem.setTexto(message.getDefaultMessage());
		return mensagem;
	}

	private String getTipo(Integer tipo) {
		return tipo == base.util.TipoDeMensagem.ERRO ? TipoDeMensagem.ERRO.toString() : TipoDeMensagem.INFO.toString();
	}

	@ExceptionHandler(value = { IllegalStateException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
		Dto dto = new Dto();
		dto.set("erro", ex.getMessage());
		return dto;
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
		Dto dto = new Dto();
		dto.set("erro", ex.getMessage());
		return dto;
	}

	@ExceptionHandler(value = { ExcecaoUsuarioNaoAutenticado.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaExcecaoUsuarioNaoAutenticado(ExcecaoUsuarioNaoAutenticado ex, HttpServletRequest request) {
		Dto dto = new Dto();
		dto.set("erro", getMensagemDeResposta(BaseMessages.REQUISICAO_NAO_AUTENTICADA));
		return dto;
	}

	@ExceptionHandler(value = { ExcecaoAcessoNaoPermitido.class })
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody Dto capturaExcecaoAcessoNaoPermitido(ExcecaoAcessoNaoPermitido ex, HttpServletRequest request) {
		MensagemDeResposta mensagem = getMensagem(ex);
		mensagem.setId(BaseMessages.ACESSO_NAO_PERMITIDO.getId());
		mensagem.setParametro("acesso");

		Dto dto = new Dto();
		dto.set("erro", mensagem);
		return dto;
	}

	@ExceptionHandler(value = { ExcecaoInfraestrutura.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaExcecaoInfraestrutura(ExcecaoInfraestrutura ex, HttpServletRequest request) {
		Dto dto = new Dto();
		dto.set("erro", getMensagens(ex));
		return dto;
	}

	@ExceptionHandler(value = { ExcecaoRuntime.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaExcecaoRuntime(ExcecaoRuntime ex, HttpServletRequest request) {
		Dto dto = new Dto();
		dto.set("erro", getMensagens(ex));
		return dto;
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaException(Exception ex, HttpServletRequest request) {
		ex.printStackTrace();
		Dto erro = new Dto();

		MensagemDeResposta mensagem = new MensagemDeResposta();
		mensagem.setId("999");
		mensagem.setParametro("");
		mensagem.setTipo("erro");
		mensagem.setTexto("Ocorreu um erro na sua requisição: " + ex.getMessage() + ". Consulte o administrador do sistema.");

		List<MensagemDeResposta> msgs = new ArrayList<MensagemDeResposta>();
		msgs.add(mensagem);

		erro.put("erro", msgs);
		return erro;
	}

	@ExceptionHandler(value = { Throwable.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody Dto capturaThrowable(Throwable ex, HttpServletRequest request) {
		ex.printStackTrace();

		Dto erro = new Dto();
		erro.set("erro", ex.getMessage());
		return erro;
	}

	private MensagemDeResposta getMensagem(ExcecaoArquitetura ex) {
		Mensagem mensagem = ex.getMensagens().getMensagens().get(0);
		return new MensagemDeResposta(mensagem);
	}

	private List<MensagemDeResposta> getMensagens(ExcecaoArquitetura ex) {
		List<MensagemDeResposta> msgs = new ArrayList<MensagemDeResposta>();

		for (Mensagem m : ex.getMensagens().getMensagens()) {
			msgs.add(new MensagemDeResposta(m));
		}
		return msgs;
	}

	protected Dto sucesso(String chave, Object valor) {
		Dto dto = new Dto();
		if (valor != null) {
			dto.set(chave, valor);
		}
		return dto;
	}

	protected Dto sucesso() {
		return new Dto();
	}

	protected String getBaseUri() {
		return String.format("%s://%s:%d%s", request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
	}
}
