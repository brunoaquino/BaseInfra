package br.com.jsbse.arquitetura.servico;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.entidade.InstanceGetter;
import br.com.jsbse.arquitetura.entidade.RegraDeUnicidade;
import br.com.jsbse.arquitetura.excecao.ExcecaoInfraestrutura;
import br.com.jsbse.arquitetura.excecao.ExcecaoRuntime;
import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.mensagem.FabricaDeMensagens;
import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.mensagem.Mensagens;
import br.com.jsbse.arquitetura.servico.dto.DTO;
import br.com.jsbse.arquitetura.servico.dto.ObjetoDto;
import br.com.jsbse.arquitetura.tipo.AcaoCrud;
import br.com.jsbse.arquitetura.util.UtilObjeto;
import br.com.jsbse.arquitetura.validacao.ValidadorDeDados;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public abstract class AuxiliarServicoBase<E extends Entidade<ID>, ID extends Serializable, D extends DTO<ID>> extends
		AuxiliarServicoLeitura<E, ID, D> {

	protected ServicoBase servico;

	private boolean levantamentoDeExcecaoHabilitado = true;

	public AuxiliarServicoBase(ServicoBase servico) {
		super();
		this.servico = servico;
	}

	public D criaDTO(E entidade) throws ExcecaoRuntime {
		if (entidade == null)
			return null;

		D dto = criaDTO();
		populaDTO(entidade, dto);
		return dto;
	}

	public E criaEntidade(D dto) {
		if (dto == null)
			return null;

		E entidade = getInstanciaDaEntidade();
		populaEntidade(entidade, dto, AcaoCrud.INCLUSAO);
		return entidade;
	}

	public E getEntidade(ID id) throws ExcecaoInfraestrutura {
		DAO dao = getDAO();
		return dao.getObjeto(getClasseEntidade(), id);
	}

	/**
	 * 
	 * @param dtos
	 * @param entidades
	 * @param composicao
	 *          se composição, exclui os objetos excluídos
	 * @throws ExcecaoInfraestrutura
	 */
	public void validaDTOsESincronizaEntidades(Collection<D> dtos, Collection<E> entidades, boolean composicao) throws ExcecaoInfraestrutura {
		desabilitaLevantamentoDeExcecao();

		try {
			for (D dto : dtos) {

				if (dto.getId() == null) {
					// o populaEntidade deve setar o dono da coleção (pai)
					try {
						E entidade = incluiObjeto(dto);
						dto.setId(entidade.getId());
						entidades.add(entidade);
					} catch (ExcecaoInfraestrutura e) {
						// trata o próximo
					}
				} else {
					try {
						alteraObjeto(dto);
					} catch (ExcecaoInfraestrutura e) {
						// trata o próximo
					}
				}
			}
			Iterator<E> itrEntidades = entidades.iterator();
			while (itrEntidades.hasNext()) {
				E entidade = itrEntidades.next();

				// procurando o dto referente à entidade
				D dto = null;
				for (D dtoAux : dtos) {
					if (dtoAux.getId().equals(entidade.getId())) {
						dto = dtoAux;
						break;
					}
				}
				if (dto == null) {
					try {
						itrEntidades.remove();
						if (composicao) {
							excluiObjeto(entidade.getId());
						}
					} catch (ExcecaoInfraestrutura e) {
						// trata o próximo
					}
				}
			}
		} finally {
			habilitaLevantamentoDeExcecao();
		}
		levantaExcecaoSeTemErro();
	}

	protected abstract void doPopulaEntidade(E entidade, D dto, AcaoCrud acao);

	private void populaEntidade(E entidade, D dto, AcaoCrud acao) {
		entidade.setId(dto.getId());
		doPopulaEntidade(entidade, dto, acao);
	}

	protected abstract void doValidaDados(D dto, AcaoCrud acao, Mensagens mensagens);

	protected void validaDadosDoDTO(D dto, AcaoCrud acao) {
		doValidaDados(dto, acao, servico.getMensagens());
	}

	private void valida(E entidade, D dto, AcaoCrud acao) throws ExcecaoInfraestrutura {
		int qtdeDeMensagens = servico.getMensagens().getMensagens().size();

		validaDadosDoDTO(dto, acao);
		doValidaRelacionamentos(entidade, dto, acao, servico.getMensagens());

		if (qtdeDeMensagens < servico.getMensagens().getMensagens().size())
			levantaExcecaoSeTemErro();
	}

	protected abstract void doValidaRelacionamentos(E entidade, D dto, AcaoCrud acao, Mensagens mensagens);

	/**
	 * Método utilizado apenas para inclusão.
	 * 
	 * @param dto
	 * @param acao
	 * @return a entidade validada sem id definido.
	 * @throws ExcecaoInfraestrutura
	 */
	public E validaDTOECriaEntidade(D dto) throws ExcecaoInfraestrutura {
		try {
			E entidade = getInstanciaDaEntidade();
			valida(entidade, dto, AcaoCrud.INCLUSAO);
			populaEntidade(entidade, dto, AcaoCrud.INCLUSAO);
			return entidade;
		} catch (ExcecaoRuntime e) {
			throw e;
		}
	}

	public E incluiObjeto(D dto) throws ExcecaoInfraestrutura {
		try {
			E entidade = validaDTOECriaEntidade(dto);

			DAO dao = getDAO();

			if (dao == null)
				throw new ExcecaoInfraestrutura("Não foi encontrado o DAO para a classe " + getClasseEntidade().getSimpleName());

			entidade.persiste();
			return entidade;
		} catch (ExcecaoRuntime e) {
			throw e;
		}
	}

	public D incluiObjetoDto(D dto) throws ExcecaoInfraestrutura {
		E entidade = incluiObjeto(dto);

		D dtoGravado = criaDTO();
		populaDTO(entidade, dtoGravado);

		return dtoGravado;
	}

	@SuppressWarnings("unchecked")
	public E alteraObjeto(D dto) throws ExcecaoInfraestrutura {
		try {
			DAO dao = getDAO();
			E entidade = dao.getObjeto(getClasseEntidade(), dto.getId());

			if (entidade == null) {
				FabricaDeMensagens fabricaDeMensagens = Aplicacao.get().getFabricaDeMensagens();
				Mensagem mensagem = fabricaDeMensagens.getMensagem("objeto.alterado.por.outro.usuario");
				servico.getMensagens().acrescentaMensagem(mensagem);
				servico.levantaExcecaoSeTemErro();
			} else {
				entidade = (E) InstanceGetter.getInstance(entidade);
				valida(entidade, dto, AcaoCrud.ALTERACAO);
				populaEntidade(entidade, dto, AcaoCrud.ALTERACAO);
				entidade.persiste();
			}
			return entidade;
		} catch (ExcecaoRuntime e) {
			throw e;
		}
	}

	public D alteraObjetoDTO(D dto) throws ExcecaoInfraestrutura {
		E entidade = alteraObjeto(dto);

		D dtoAlterado = criaDTO();
		populaDTO(entidade, dtoAlterado);

		return dtoAlterado;
	}

	protected abstract void doValidaExclusao(E entidade, Mensagens mensagens);

	protected abstract void doExcluiRelacionados(E entidade) throws ExcecaoInfraestrutura;

	public void excluiObjeto(ID id) throws ExcecaoInfraestrutura {
		try {
			DAO dao = getDAO();
			E entidade = ValidadorDeDados.erroSeIndefinidoOuInexistente(getClasseEntidade(), dao, id, getClasseEntidade().getSimpleName(),
					servico.getMensagens(), false);
			if (entidade == null) {
				FabricaDeMensagens fabricaDeMensagens = Aplicacao.get().getFabricaDeMensagens();
				Mensagem mensagem = fabricaDeMensagens.getMensagem("objeto.alterado.por.outro.usuario");
				servico.getMensagens().acrescentaMensagem(mensagem);
				servico.levantaExcecaoSeTemErro();
			} else {
				doValidaExclusao(entidade, servico.getMensagens());
				doExcluiRelacionados(entidade);
				servico.levantaExcecaoSeTemErro();

				dao.excluiObjeto(entidade);
			}
		} catch (ExcecaoRuntime e) {
			throw e;
		}
	}

	/**
	 * Exclui uma coleção de DTOs
	 * 
	 * @param listaParaExcluir
	 *          Collection<DTO>
	 * @throws ExcecaoInfraestrutura
	 */
	public void excluiObjetosDTO(Collection<D> listaParaExcluir) throws ExcecaoInfraestrutura {
		if (listaParaExcluir != null) {
			for (D dto : listaParaExcluir) {
				excluiObjeto(dto.getId());
			}
		}
	}

	/**
	 * Exclui uma coleção de Entidade
	 * 
	 * @param listaParaExcluir
	 * @throws ExcecaoInfraestrutura
	 */
	public void excluiObjetosEntidade(Collection<E> listaParaExcluir) throws ExcecaoInfraestrutura {
		if (listaParaExcluir != null) {
			for (E entidade : listaParaExcluir) {
				excluiObjeto(entidade.getId());
			}
		}
	}

	private E getInstanciaDaEntidade() throws ExcecaoInfraestrutura {
		try {
			return getClasseEntidade().newInstance();
		} catch (Exception e) {
			throw new ExcecaoInfraestrutura("Erro ao instanciar uma entidade.", e);
		}
	}

	protected void levantaExcecaoSeTemErro() throws ExcecaoInfraestrutura {
		if (levantamentoDeExcecaoHabilitado)
			servico.levantaExcecaoSeTemErro();
	}

	public void habilitaLevantamentoDeExcecao() {
		levantamentoDeExcecaoHabilitado = true;
	}

	public void desabilitaLevantamentoDeExcecao() {
		levantamentoDeExcecaoHabilitado = false;
	}

	protected boolean isDiferentes(Object bo, Object dto) {
		return UtilObjeto.isDiferentes(bo, dto);
	}

	@SuppressWarnings("hiding")
	protected <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, ID id, String parametro) {
		return ValidadorDeDados.erroSeIndefinidoOuInexistente(tipoDaClasse, id, parametro, servico.getMensagens());
	}

	@SuppressWarnings("hiding")
	protected <E extends Entidade<ID>, ID extends Serializable> E erroSeIndefinidoOuInexistente(Class<E> tipoDaClasse, ObjetoDto<ID> objeto,
			String parametro) {
		return ValidadorDeDados.erroSeIndefinidoOuInexistente(tipoDaClasse, objeto != null ? objeto.getId() : null, parametro, servico.getMensagens());
	}

	@SuppressWarnings("hiding")
	protected <E extends Entidade<ID>, ID extends Serializable> E erroSeDefinidoEInexistente(Class<E> tipoDaClasse, ID id, String parametro) {
		return ValidadorDeDados.erroSeDefinidoEInexistente(tipoDaClasse, id, parametro, servico.getMensagens());
	}

	@SuppressWarnings("hiding")
	protected <E extends Entidade<ID>, ID extends Serializable> E erroSeDefinidoEInexistente(Class<E> tipoDaClasse, ObjetoDto<ID> objeto,
			String parametro) {
		return ValidadorDeDados.erroSeDefinidoEInexistente(tipoDaClasse, objeto != null ? objeto.getId() : null, parametro, servico.getMensagens());
	}

	protected boolean erroSeRegistroNaoEUnico(D dto, RegraDeUnicidade regra, AcaoCrud acao, String parametro) {
		return ValidadorDeDados.erroSeRegistroNaoEUnico(getClasseEntidade(), dto, regra, acao, parametro, servico.getMensagens());
	}

	protected void acrescentaMensagem(String chave, String parametro, String... argumentos) {
		servico.getMensagens().acrescentaMensagem(getFabricaDeMensagens().getMensagem(chave, argumentos));
	}
}
