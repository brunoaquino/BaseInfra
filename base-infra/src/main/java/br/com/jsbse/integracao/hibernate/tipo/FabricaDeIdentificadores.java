package br.com.jsbse.integracao.hibernate.tipo;

public class FabricaDeIdentificadores {

	public static IdentificadorEnumerado getIdentificador(
			TipoIdentificadorDeEnumerados tipo) {
		switch (tipo) {
		case INTEGER:
			return new IdentificadorEnumeradoInteger();
		case STRING:
			return new IdentificadorEnumeradoString();
		}
		return null;
	}
}
