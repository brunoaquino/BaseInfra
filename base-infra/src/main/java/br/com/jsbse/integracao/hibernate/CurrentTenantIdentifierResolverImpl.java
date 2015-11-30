package br.com.jsbse.integracao.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import br.com.jsbse.arquitetura.excecao.ExcecaoUsuarioNaoAutenticado;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;
import br.com.jsbse.jsbse.aplicacao.UsuarioBaseMultiTenant;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		try {
			UsuarioBaseMultiTenant usuario = (UsuarioBaseMultiTenant) Aplicacao.get().getUsuarioDaRequisicaoCorrente();
			return usuario.getTenantId();
		} catch (ExcecaoUsuarioNaoAutenticado e) {
			return getMasterDbName();
		}
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
	
	public String getMasterDbName() {
		return "master";
	}
}