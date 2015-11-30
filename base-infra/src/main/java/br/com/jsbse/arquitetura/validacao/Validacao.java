package br.com.jsbse.arquitetura.validacao;

public class Validacao {

	public static <T> boolean isAnteriorA(Comparable<T> valor, T referencia) {
		if (valor != null && referencia != null) {
			return valor.compareTo(referencia) < 0;
		}
		return false;
	}

	public static boolean isIgualA(Object valor, Object referencia) {
		if (valor != null) {
			return valor.equals(referencia);
		}
		return false;
	}

	public static boolean isIndefinido(Object objeto) {
		return objeto == null;
	}

	public static <T> boolean isPosteriorA(Comparable<T> valor, T referencia) {
		if (valor != null && referencia != null) {
			return valor.compareTo(referencia) > 0;
		}
		return false;
	}
}