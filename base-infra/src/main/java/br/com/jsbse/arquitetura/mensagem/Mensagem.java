package br.com.jsbse.arquitetura.mensagem;

import java.io.Serializable;

import br.com.jsbse.arquitetura.tipo.TipoDeMensagem;

public class Mensagem implements Serializable {
	
	private static final long serialVersionUID = 1738569798341206232L;
	private String id;
	private TipoDeMensagem tipo;
	private String texto;
	private String parametro;

	public Mensagem() {

	}

	public Mensagem(String id, TipoDeMensagem tipo, String texto) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.texto = texto;
	}

	public Mensagem(String id, TipoDeMensagem tipo, String texto, String parametro) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.texto = texto;
		this.parametro = parametro;
	}

	public Mensagem(TipoDeMensagem tipo, String texto) {
		super();
		this.tipo = tipo;
		this.texto = texto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoDeMensagem getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMensagem tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
}