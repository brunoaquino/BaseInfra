package br.com.jsbse.arquitetura.entidade;

/**
 * Classe que representa um parâmetro de unicidade.
 * 
 */
public class ParametroDeConsulta {
	private String campo;
	private String valor;

	public ParametroDeConsulta() {
	}

	public ParametroDeConsulta(String campo, String valor) {
		super();
		this.campo = campo;
		this.valor = valor;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
