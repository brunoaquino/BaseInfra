package br.com.jsbse.jsbse.aplicacao;

public interface UsuarioBaseMultiTenant extends UsuarioBase {

	public String getTenantId();
	
	public void setTenantId(String tenantId);
}
