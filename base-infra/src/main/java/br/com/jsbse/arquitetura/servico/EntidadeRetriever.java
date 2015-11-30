package br.com.jsbse.arquitetura.servico;

import java.io.Serializable;

import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

import com.inspiresoftware.lib.dto.geda.adapter.EntityRetriever;

public class EntidadeRetriever implements EntityRetriever {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object retrieveByPrimaryKey(Class entityInterface, Class entityClass, Object primaryKey) {
		Serializable id = (Serializable) primaryKey;
		if (id != null) {
			DAO dao = Aplicacao.get().getDAO();
			return dao.getObjeto((Class) entityInterface, id);
		}
		return null;
	}

}
