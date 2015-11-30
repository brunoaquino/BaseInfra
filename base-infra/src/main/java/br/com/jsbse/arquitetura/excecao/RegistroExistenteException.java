package br.com.jsbse.arquitetura.excecao;

public class RegistroExistenteException extends ExcecaoInfraestrutura {

	private static final long serialVersionUID = 1L;

	public RegistroExistenteException() {
		super("Registro existente no sistema.");
	}
}
