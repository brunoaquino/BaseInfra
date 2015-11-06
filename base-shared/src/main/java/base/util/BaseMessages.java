package base.util;

/**
 * Mensagens comuns dos sistemas que utilizam o Base.
 */
public enum BaseMessages {

	REQUISICAO_NAO_AUTENTICADA("001", TipoDeMensagem.ERRO, "Requisição não autenticada."),

	ACESSO_NAO_PERMITIDO("002", TipoDeMensagem.ERRO, "Você não tem acesso para realizar esta operação."),

	ACESSO_TIMEOUT("003", TipoDeMensagem.ERRO, "Não foi possível estabelecer uma conexão com o servidor.");

	private String id;
	private String defaultMessage;
	private Integer tipo;

	private BaseMessages(String id, Integer tipo, String message) {
		this.id = id;
		this.tipo = tipo;
		this.defaultMessage = message;
	}

	public String getId() {
		return id;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public Integer getTipo() {
		return tipo;
	}
}