package br.com.jsbse.arquitetura.servico;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.excecao.ExcecaoInfraestrutura;
import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.integracao.Repositorio;
import br.com.jsbse.arquitetura.mensagem.FabricaDeMensagens;
import br.com.jsbse.arquitetura.mensagem.Mensagem;
import br.com.jsbse.arquitetura.servico.dto.DTO;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public abstract class AuxiliarServicoLeitura<E extends Entidade<ID>, ID extends Serializable, D extends DTO<ID>> {

	protected abstract void doPopulaDTO(E entidade, D dto);

	protected void populaDTO(E entidade, D dto) {
		dto.setId(entidade.getId());
		doPopulaDTO(entidade, dto);
	}

	public D getObjeto(ID id, boolean carregaColecoesDosRelacionamentos) throws ExcecaoInfraestrutura {
		DAO dao = getDAO();
		E entidade = dao.getObjeto(getClasseEntidade(), id);

		if (entidade != null) {

			D dto = criaDTO();
			populaDTO(entidade, dto);
			return dto;
		}
		return null;
	}

	public D getObjeto(ID id) throws ExcecaoInfraestrutura {
		return getObjeto(id, false);
	}

	@SuppressWarnings("unchecked")
	public Collection<D> getObjetosDTO() throws ExcecaoInfraestrutura {
		DAO dao = getDAO();

		Collection<E> entidades = (Collection<E>) dao.getObjetos(getClasseEntidade());
		Collection<D> dtos = new ArrayList<D>();
		for (E entidade : entidades) {
			D dto = criaDTO();
			populaDTO(entidade, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	public Collection<D> getObjetosDTO(Collection<E> entidades) throws ExcecaoInfraestrutura {
		return getObjetosDTO(entidades, false);
	}

	public Collection<D> getObjetosDTO(Collection<E> entidades, boolean ignoraRelacionamentos) throws ExcecaoInfraestrutura {
		Collection<D> dtos = new ArrayList<D>();
		for (E entidade : entidades) {
			D dto = criaDTO();
			populaDTO(entidade, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	public D getObjetoDTO(E entidade) throws ExcecaoInfraestrutura {
		if (entidade == null)
			return null;

		D dto = criaDTO();
		populaDTO(entidade, dto);
		return dto;
	}

	public Collection<D> getObjetosDTOOrdenados(Comparator<D> comparador) throws ExcecaoInfraestrutura {
		SortedSet<D> objetosDTO = new TreeSet<D>(comparador);
		objetosDTO.addAll(getObjetosDTO());
		return objetosDTO;
	}

	public Collection<D> getObjetosDTOOrdenados() throws ExcecaoInfraestrutura {
		SortedSet<D> objetosDTO = new TreeSet<D>();
		objetosDTO.addAll(getObjetosDTO());
		return objetosDTO;
	}

	public Collection<D> getObjetosDTOOrdenados(Collection<E> entidades, Comparator<D> comparador) throws ExcecaoInfraestrutura {
		SortedSet<D> dtos = new TreeSet<D>(comparador);
		dtos.addAll(getObjetosDTO(entidades));
		return dtos;
	}

	public Collection<D> getObjetosDTOOrdenados(Collection<E> entidades) throws ExcecaoInfraestrutura {
		SortedSet<D> dtos = new TreeSet<D>();
		dtos.addAll(getObjetosDTO(entidades));
		return dtos;
	}

	protected D criaDTO() throws ExcecaoInfraestrutura {
		try {
			return getClasseDTO().newInstance();
		} catch (Exception e) {
			throw new ExcecaoInfraestrutura("Erro ao instanciar um objeto DTO.", e);
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<? extends E> getClasseEntidade() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	protected Class<? extends D> getClasseDTO() {
		return (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}

	public DAO getDAO() {
		return Aplicacao.get().getDAO();
	}

	protected <T extends Repositorio> T getRepositorio(Class<T> interfaceDoRepositorio) {
		return Aplicacao.get().getRepositorio(interfaceDoRepositorio);
	}

	protected FabricaDeMensagens getFabricaDeMensagens() {
		return Aplicacao.get().getFabricaDeMensagens();
	}

	protected Collator getOrdenador() {
		return Aplicacao.get().getOrdenador();
	}

	protected Mensagem getMensagem(String chave) {
		return getFabricaDeMensagens().getMensagem(chave);
	}

	protected Mensagem getMensagem(String chave, String parametro) {
		return getFabricaDeMensagens().getMensagem(chave, parametro);
	}
}
