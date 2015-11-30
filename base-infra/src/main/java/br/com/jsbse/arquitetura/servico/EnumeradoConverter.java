package br.com.jsbse.arquitetura.servico;

import br.com.jsbse.arquitetura.entidade.EnumeradoPersistente;
import br.com.jsbse.arquitetura.servico.dto.EnumeradoDto;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.adapter.ValueConverter;

public class EnumeradoConverter implements ValueConverter {

	@Override
	public Object convertToDto(Object object, BeanFactory beanFactory) {
		if (object instanceof EnumeradoPersistente) {
			EnumeradoPersistente entidade = (EnumeradoPersistente) object;
			EnumeradoDto dto = new EnumeradoDto();
			dto.setId(entidade.getId());
			dto.setDescricao(entidade.getDescricao());
			return dto;
		} else {
			return null;
		}
	}

	@Override
	public Object convertToEntity(Object object, Object oldEntity, BeanFactory beanFactory) {
		return null;
	}

}
