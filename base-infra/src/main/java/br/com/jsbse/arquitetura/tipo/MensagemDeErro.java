package br.com.jsbse.arquitetura.tipo;

public enum MensagemDeErro {
	OBJETO_INDEFINIDO("objeto.indefinido"),

	OBJETO_ZERADO_OU_VAZIO("objeto.zero.vazio"),

	TEXTO_MAIOR_QUE_O_TAMANHO_DEFINIDO("texto.maior.tamanho.definido"),

	TEXTO_MENOR_QUE_O_TAMANHO_DEFINIDO("texto.menor.tamanho.definido"),

	ANTERIOR_A("anterior.a"),

	POSTERIOR_A("posterior.a"),
	
	UNICIDADE("registro.existente");

	private String chave;

	private MensagemDeErro(String chave) {
		this.chave = chave;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
}
