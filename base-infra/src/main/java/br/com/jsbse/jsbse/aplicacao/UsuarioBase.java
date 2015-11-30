package br.com.jsbse.jsbse.aplicacao;

import java.io.Serializable;

public interface UsuarioBase {

	public Serializable getId();

	public String getLogin();

	public String getSenha();

	public boolean isBloqueado();
}
