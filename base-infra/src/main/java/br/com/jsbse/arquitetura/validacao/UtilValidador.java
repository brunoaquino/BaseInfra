package br.com.jsbse.arquitetura.validacao;

import br.com.jsbse.arquitetura.mensagem.FabricaDeMensagens;
import br.com.jsbse.arquitetura.mensagem.Mensagens;
import br.com.jsbse.arquitetura.tipo.MensagemDeErro;
import br.com.jsbse.arquitetura.util.Util;
import br.com.jsbse.arquitetura.util.UtilString;
import br.com.jsbse.jsbse.aplicacao.Aplicacao;

public class UtilValidador {

	public static boolean erroSeIndefinido(Object objeto, String parametro, Mensagens mensagens) {
		return erroSeIndefinido(objeto, parametro, mensagens, null);
	}

	public static boolean erroSeIndefinido(Object objeto, String parametro, Mensagens mensagens, String mensagem) {
		if (Validacao.isIndefinido(objeto)) {
			if (mensagem == null) {
				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_INDEFINIDO.getChave(), parametro));
			} else {
				mensagens.acrescentaMensagemDeErro(parametro, mensagem);
			}
			return true;
		}
		return false;
	}

	public static boolean erroSeTextoIndefinidoOuVazio(String valor, String parametro, Mensagens mensagens) {
		if (UtilString.isVazio(valor)) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_ZERADO_OU_VAZIO.getChave(), parametro));
			return true;
		}
		return false;
	}

	public static boolean erroSeZeroOuVazio(Object valor, String parametro, Mensagens mensagens) {
		ValidadorTipo validador = FabricaDeValidadores.getValidador(valor);
		if (validador.isZeroOuVazio(valor)) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.OBJETO_ZERADO_OU_VAZIO.getChave(), parametro));
			return true;
		}
		return false;
	}

	public static boolean erroSeTextoMaiorQue(String texto, int tamanho, String parametro, Mensagens mensagens) {
		if (texto == null)
			return false;

		if (texto.length() > tamanho) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.TEXTO_MAIOR_QUE_O_TAMANHO_DEFINIDO.getChave(), parametro,
					new String[] { String.valueOf(tamanho) }));
			return true;
		}
		return false;
	}

	public static boolean erroSeTextoMenorQue(String texto, int tamanho, String parametro, Mensagens mensagens) {
		if (texto == null)
			return false;

		if (texto.length() < tamanho) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.TEXTO_MENOR_QUE_O_TAMANHO_DEFINIDO.getChave(), parametro,
					new String[] { String.valueOf(tamanho) }));
			return true;
		}
		return false;
	}

	public static <T> boolean erroSeAnteriorA(Comparable<T> valor, T referencia, String parametro, Mensagens mensagens) {
		ValidadorTipo validador = FabricaDeValidadores.getValidador(valor);

		if (Validacao.isAnteriorA(valor, referencia)) {
			mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.ANTERIOR_A.getChave(), parametro,
					new String[] { validador.getObjetoFormatado(referencia) }));
			return true;
		}
		return false;
	}

	public static <T> boolean erroSePosteriorA(Comparable<T> valor, T referencia, String parametro, Mensagens mensagens, String mensagem) {
		ValidadorTipo validador = FabricaDeValidadores.getValidador(valor);

		if (Validacao.isPosteriorA(valor, referencia)) {
			if (mensagem == null) {
				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem(MensagemDeErro.POSTERIOR_A.getChave(), parametro,
						new String[] { validador.getObjetoFormatado(referencia) }));
			} else {
				mensagens.acrescentaMensagemDeErro(parametro, mensagem);
			}
			return true;
		}
		return false;
	}

	public static <T> boolean erroSePosteriorA(Comparable<T> valor, T referencia, String parametro, Mensagens mensagens) {
		return erroSePosteriorA(valor, referencia, parametro, mensagens, null);
	}

	public static FabricaDeMensagens getFabricaDeMensagens() {
		return Aplicacao.get().getFabricaDeMensagens();
	}

	public static boolean erroSeEmailInvalido(String email, String parametro, Mensagens mensagens) {
		if (email != null) {
			if (!(email.length() >= 5 && email.length() <= 155) || !Util.isCoincideComAExpressao(email, "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

				mensagens.acrescentaMensagem(getFabricaDeMensagens().getMensagem("email.invalido", parametro));
				return true;
			}
		}

		return false;
	}
	
	public static boolean erroSeCpfInvalido(String cpf, String parametro, Mensagens mensagens) {
		if (cpf == null)
			return false;
		
		if (!Util.isCpfValido(cpf)) {
			mensagens.acrescentaMensagem("cpf.invalido", parametro, cpf);
			return true;
		}
		return false;
	}
	
	public static boolean erroSeCnpjInvalido(String cnpj, String parametro, Mensagens mensagens) {
		if (cnpj == null)
			return false;
		
		if (!Util.isCnpjValido(cnpj)) {
			mensagens.acrescentaMensagem("cnpj.invalido", parametro, cnpj);
			return true;
		}
		return false;
	}
}