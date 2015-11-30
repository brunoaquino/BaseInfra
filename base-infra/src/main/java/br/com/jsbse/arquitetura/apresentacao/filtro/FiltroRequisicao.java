package br.com.jsbse.arquitetura.apresentacao.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.com.jsbse.jsbse.aplicacao.Aplicacao;
import br.com.jsbse.jsbse.aplicacao.Autenticador;
import br.com.jsbse.jsbse.aplicacao.UsuarioBase;

public class FiltroRequisicao implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// O usuário é atribuido na sessão através do login. Para as requisições não acessarem a sessão, transferimos
		// o registro do usuário para uma	Thread local o qual é desfeito no final da requisição.
		// A intenção é não ter os serviços atrelados a uma sessão http.
		Autenticador.getInstancia().registraUsuario(
				(UsuarioBase) ((HttpServletRequest) request).getSession().getAttribute(Aplicacao.USUARIO_BASE));
		chain.doFilter(request, response);
		Autenticador.getInstancia().cancelaRegistroDeUsuario();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}