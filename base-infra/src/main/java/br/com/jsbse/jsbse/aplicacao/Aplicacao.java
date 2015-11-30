package br.com.jsbse.jsbse.aplicacao;

import java.text.Collator;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import br.com.jsbse.arquitetura.integracao.DAO;
import br.com.jsbse.arquitetura.integracao.FabricaDeRepositorios;
import br.com.jsbse.arquitetura.integracao.Repositorio;
import br.com.jsbse.arquitetura.mensagem.FabricaDeMensagens;

@Component("aplicacao")
public class Aplicacao {

	public static final String USUARIO_BASE = "usuarioBase";

	private static Aplicacao instancia;

	@Resource(name = "fabricaDeRepositorios")
	private FabricaDeRepositorios fabricaDeRepositorios;

	@Resource(name = "fabricaDeMensagens")
	private FabricaDeMensagens fabricaDeMensagens;
	
	@Resource(name = "servicoAutenticacao")
	private ServicoAutenticacao servicoAutenticacao;

	@Resource(name = "baseDAO")
	private DAO dao;

	private Aplicacao() {
		instancia = this;
	}

	public static Aplicacao get() {
		return instancia;
	}

	public DAO getDAO() {
		return dao;
	}

	@SuppressWarnings("unchecked")
	public <T extends Repositorio> T getRepositorio(Class<? extends Repositorio> interfaceDoRepositorio) {
		return (T) fabricaDeRepositorios.getRepositorio(interfaceDoRepositorio);
	}

	public FabricaDeMensagens getFabricaDeMensagens() {
		return fabricaDeMensagens;
	}

	public Collator getOrdenador() {
		Collator ordenador = Collator.getInstance(new Locale("pt", "BR"));
		ordenador.setStrength(Collator.PRIMARY);
		return ordenador;
	}

	public UsuarioBase getUsuarioDaRequisicaoCorrente() {
		return Autenticador.getInstancia().getUsuarioDaRequisicaoCorrente();
	}

	public ServicoAutenticacao getServicoAutenticacao() {
		return servicoAutenticacao;
	}
}
