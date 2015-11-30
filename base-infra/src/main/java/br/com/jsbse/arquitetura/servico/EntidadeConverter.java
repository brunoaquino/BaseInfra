package br.com.jsbse.arquitetura.servico;

import br.com.jsbse.arquitetura.entidade.Entidade;
import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.servico.dto.ObjetoDto;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.adapter.ValueConverter;

public class EntidadeConverter implements ValueConverter {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object convertToDto(Object object, BeanFactory beanFactory) {
		if (object instanceof Entidade) {
			Entidade entidade = (Entidade) object;
			ObjetoDto dto = new ObjetoDto();
			dto.setId(entidade.getId());
			dto.setNid(entidade.getNid());
			return dto;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object convertToEntity(Object object, Object oldEntity, BeanFactory beanFactory) {
		Long id = (Long) object;

		DAO dao = Aplicacao.get().getDAO();
		return dao.getObjeto((Class) oldEntity.getClass(), id);
	}

}
