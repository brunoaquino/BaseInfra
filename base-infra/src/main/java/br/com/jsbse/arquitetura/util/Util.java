package br.com.jsbse.arquitetura.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

public class Util {

	public static boolean isCnpjValido(String cnpj) {
		if (cnpj == null)
			return false;

		cnpj = cnpj.replace("/", "").replace("-", "").replace(".", "");

		if (StringUtils.isEmpty(cnpj) || cnpj.length() > 14)
			return false;

		String cnpjSemDigito = StringUtils.left(cnpj, 13);
		int digito = Integer.parseInt(StringUtils.right(cnpj, 1));

		return getDigitoModulo11(cnpjSemDigito, true) == digito;
	}

	public static boolean isCpfValido(String cpf) {
		if (cpf == null)
			return false;

		cpf = cpf.replace("/", "").replace("-", "").replace(".", "");

		if (StringUtils.isEmpty(cpf) || cpf.length() > 11)
			return false;

		String cnpjSemDigito = StringUtils.left(cpf, 10);
		int digito = Integer.parseInt(StringUtils.right(cpf, 1));

		return getDigitoModulo11(cnpjSemDigito, false) == digito;
	}

	public static boolean isCoincideComAExpressao(String valor, String expressaoRegular) {
		if (valor != null && expressaoRegular != null) {
			try {
				Pattern pattern = Pattern.compile(expressaoRegular);
				Matcher matcher = pattern.matcher(valor);
				return matcher.matches();
			} catch (PatternSyntaxException e) {
				return false;
			}
		}
		return false;
	}

	public static boolean isContemAExpressao(String valor, String expressaoRegular) {
		try {
			Pattern pattern = Pattern.compile(expressaoRegular);
			Matcher matcher = pattern.matcher(valor);
			return matcher.find();
		} catch (PatternSyntaxException e) {
			return false;
		}
	}

	/**
	 * @param valor
	 * @param isCnpj
	 * @return O d�gito verificador calculado pelo m�todo M�dulo11. Se o resto for 10 retorna 0. Se isCnpj o m�todo � ligeiramente diferente.
	 */
	public static int getDigitoModulo11(String valor, boolean isCnpj) {
		int tamanho = valor.length();
		int calculo = 0;
		int fator = 2;
		for (int i = tamanho - 1; i >= 0; i--) {
			if (isCnpj && fator > 9) {
				fator -= 8;
			}
			calculo += (valor.charAt(i) - '0') * fator++;
		}
		calculo = 11 - calculo % 11;
		if (calculo > 9) {
			return 0;
		}
		return calculo;
	}
}
