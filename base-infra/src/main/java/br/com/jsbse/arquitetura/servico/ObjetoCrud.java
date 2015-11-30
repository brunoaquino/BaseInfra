package br.com.jsbse.arquitetura.servico;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.servico.dto.DTO;
import br.com.jsbse.arquitetura.servico.dto.ObjetoDto;
import br.com.jsbse.arquitetura.tipo.AcaoCrud;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.assembler.Assembler;
import com.inspiresoftware.lib.dto.geda.assembler.DTOAssembler;

public abstract class ObjetoCrud<E extends Entidade<ID>, ID extends Serializable, D extends DTO<ID>>
		extends AuxiliarServicoBase<E, ID, D> {
	private Map<String, Object> converters = new HashMap<String, Object>();

	private BeanFactory beanFactory;
	
	public ObjetoCrud(ServicoBase servico) {
		super(servico);

		converters.put("Converter", new EntidadeConverter());
		converters.put("Retriever", new EntidadeRetriever());
		converters.put("Enumerado", new EnumeradoConverter());
		
		beanFactory = new BeanFactory() {

			@SuppressWarnings("rawtypes")
			Map<String, Class> mapa = new HashMap<String, Class>();

			{
				mapa.put(ObjetoDto.class.getName(), ObjetoDto.class);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Class getClazz(String entityBeanKey) {
				return mapa.get(entityBeanKey);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object get(String entityBeanKey) {
				return new ObjetoDto();
			}
		};
	}

	@Override
	protected void doPopulaEntidade(E entidade, D dto, AcaoCrud acao) {
		Assembler asm = DTOAssembler.newAssembler(getClasseDTO(),
				getClasseEntidade());
		asm.assembleEntity(dto, entidade, converters, beanFactory);
	}

	@Override
	protected void doPopulaDTO(E entidade, D dto) {
		Assembler asm = DTOAssembler.newAssembler(getClasseDTO(),
				getClasseEntidade());
		asm.assembleDto(dto, entidade, converters, null);
	}
}
