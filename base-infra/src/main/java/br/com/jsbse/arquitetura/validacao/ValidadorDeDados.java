package br.com.jsbse.arquitetura.validacao;

import java.io.Serializable;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.entidade.ParametroDeConsulta;
import br.com.jsbse.arquitetura.entidade.RegraDeUnicidade;
import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.mensagem.FabricaDeMensagens;
import br.com.jsbse.arquitetura.mensagem.Mensagens;
import br.com.jsbse.arquitetura.servico.dto.DTO;
import br.com.jsbse.arquitetura.tipo.AcaoCrud;
import br.com.jsbse.arquitetura.tipo.MensagemDeErro;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public class ValidadorDeDados {
	/**
	 * 
	 * @param <E>
	 * @param <ID>
	 * @param tipoDaClasse
	 * @param id
	 * @param parametro
	 * @param mensagens
	 * @return O objeto cujo id se refere ao parâmetro passado. Retorna null se o objeto for inexistente. Não acrescenta mensagem de validação.
	 * 
	 * @author jsilva
	 */
	public static <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, ID id, String parametro,
			Mensagens mensagens) {
		return erroSeIndefinidoOuInexistente(tipoDaClasse, id, parametro, mensagens, true);
	}

	/**
	 * 
	 * @param <E>
	 * @param <ID>
	 * @param dao
	 * @param tipoDaClasse
	 * @param id
	 * @param parametro
	 * @param mensagens
	 * @return O objeto cujo id se refere ao parâmetro passado. Retorna null se o objeto for inexistente. Não acrescenta mensagem de validação.
	 * 
	 * @author jsilva
	 */
	public static <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, DAO dao, ID id,
			String parametro, Mensagens mensagens) {
		if (id != null) {
			E entidade = dao.getObjeto(tipoDaClasse, id);
			if (entidade != null)
				return entidade;
		}

		mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_INDEFINIDO.getChave(), parametro));
		return null;
	}

	/**
	 * 
	 * @param <E>
	 * @param <ID>
	 * @param tipoDaClasse
	 * @param id
	 * @param parametro
	 * @param mensagens
	 * @param acrescentaMensagem
	 *            true se é para acrescentar mensagem de validaçãoo referente a objeto indefinido.
	 * @return O objeto cujo id se refere ao parâmetro passado. Retorna null se o objeto for inexistente.
	 * 
	 * @author jsilva
	 */
	public static <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, ID id, String parametro,
			Mensagens mensagens, boolean acrescentaMensagem) {
		DAO dao = Aplicacao.get().getDAO();
		if (id != null) {
			E entidade = dao.getObjeto(tipoDaClasse, id);
			if (entidade != null)
				return entidade;
		}

		if (acrescentaMensagem)
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_INDEFINIDO.getChave(), parametro));
		return null;
	}

	public static <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, DAO dao, ID id,
			String parametro, Mensagens mensagens, boolean acrescentaMensagem) {
		if (id != null) {
			E entidade = dao.getObjeto(tipoDaClasse, id);
			if (entidade != null)
				return entidade;
		}

		if (acrescentaMensagem)
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_INDEFINIDO.getChave(), parametro));
		return null;
	}

	public static <E extends Entidade<ID>, ID extends Serializable> E erroSeDefinidoEInexistente(Class<E> tipoDaClasse, ID id, String parametro,
			Mensagens mensagens) {
		if (id == null)
			return null;

		DAO dao = Aplicacao.get().getDAO();
		E entidade = dao.getObjeto(tipoDaClasse, id);
		if (entidade == null) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_INDEFINIDO.getChave(), parametro));
		} else {
			return entidade;
		}
		return null;
	}

	public static <E extends Entidade<ID>, ID extends Serializable> E erroSeDefinidoEInexistente(Class<E> tipoDaClasse, DAO dao, ID id,
			String parametro, Mensagens mensagens) {
		if (id == null)
			return null;

		E entidade = dao.getObjeto(tipoDaClasse, id);
		if (entidade == null) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_INDEFINIDO.getChave(), parametro));
		} else {
			return entidade;
		}
		return null;
	}

	public static FabricaDeMensagens getFabricaDeMensagens() {
		return Aplicacao.get().getFabricaDeMensagens();
	}

	@SuppressWarnings("rawtypes")
	public static <E extends Entidade<ID>, D extends DTO<ID>, ID extends Serializable> boolean erroSeRegistroNaoEUnico(Class<E> tipoDaClasse, D dto,
			RegraDeUnicidade regra, AcaoCrud modo, String parametro, Mensagens mensagens) {
		DAO dao = Aplicacao.get().getDAO();
		Entidade entidade = dao.getObjetoPorParametros(tipoDaClasse, regra.getParametros().toArray(new ParametroDeConsulta[0]));

		if (modo == AcaoCrud.INCLUSAO) {
			if (entidade != null) {
				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.UNICIDADE.getChave(), parametro,
						new String[] { parametro }));
				return true;
			}
		} else {
			if (entidade != null && !entidade.getId().equals(dto.getId())) {
				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.UNICIDADE.getChave(), parametro,
						new String[] { parametro }));
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E extends Entidade<ID>, ID extends Serializable> boolean erroSeRegistroNaoEUnico(E entidade, RegraDeUnicidade regra,
			AcaoCrud modo, String parametro, Mensagens mensagens) {
		DAO dao = Aplicacao.get().getDAO();
		Entidade entidade2 = dao.getObjetoPorParametros(entidade.getClass(), regra.getParametros().toArray(new ParametroDeConsulta[0]));

		if (modo == AcaoCrud.INCLUSAO) {
			if (entidade2 != null) {
				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.UNICIDADE.getChave(), parametro,
						new String[] { parametro }));
				return true;
			}
		} else {
			if (entidade2 != null && !entidade2.getId().equals(entidade.getId())) {
				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.UNICIDADE.getChave(), parametro,
						new String[] { parametro }));
				return true;
			}
		}
		return false;
	}
}
