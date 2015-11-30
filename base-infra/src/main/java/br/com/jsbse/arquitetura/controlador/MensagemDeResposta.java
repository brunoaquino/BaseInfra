package br.com.jsbse.arquitetura.controlador;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.jsbse.arquitetura.mensagem.Mensagem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MensagemDeResposta {

	private String id;
	private String tipo;
	private String texto;
	private String parametro;

	public static final MensagemDeResposta SUCESSO = new MensagemDeResposta(
			"1", "Sucesso", "Sua requisição foi realizada com sucesso!", null);

	public MensagemDeResposta() {
	}

	public MensagemDeResposta(String id, String tipo, String texto,
			String parametro) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.texto = texto;
		this.parametro = parametro;
	}

	public MensagemDeResposta(Mensagem mensagem) {
		this.id = mensagem.getId();
		this.parametro = mensagem.getParametro();
		this.texto = mensagem.getTexto();
		this.tipo = mensagem.getTipo().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
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
