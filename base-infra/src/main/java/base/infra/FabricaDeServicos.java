package base.infra;

import br.com.jsbse.arquitetura.servico.ServicoBase;
import br.com.jsbse.arquitetura.spring.ContextoSpring;

public class FabricaDeServicos {
	@SuppressWarnings("unchecked")
	public static <T extends ServicoBase> T getServico(Class<T> tipoDoServico) {
		String nomeDaClasse = tipoDoServico.getSimpleName();

		String nomeDaInterfaceAux = nomeDaClasse.substring(0, 1).toLowerCase();
		nomeDaClasse = nomeDaInterfaceAux + nomeDaClasse.substring(1);

		return (T) ContextoSpring.getBean(nomeDaClasse);
	}

}
