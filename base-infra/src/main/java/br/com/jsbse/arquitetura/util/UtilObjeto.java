package br.com.jsbse.arquitetura.util;

public class UtilObjeto {
	
	public static boolean isDiferentes(Object objeto1, Object objeto2) {
			return objeto1 == null ? objeto2 != null : !objeto1.equals(objeto2);
		}
}

