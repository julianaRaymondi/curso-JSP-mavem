package Filter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import Connection.SingleConectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//ctrl+shift+o: atualiza os imports

@WebFilter(urlPatterns = { "/principal/*" }) // intercepta todas as requisicoes que vem do projeto mapeado, não pode
												// filtrar o indice, pq ainda nao tem login ativo no indice
public class FilterAutenticacao extends HttpFilter implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {

	}

	// encerra os processos quando o servidor é parado
	// ex, mataria os processos de conexao com o banco
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	// intercpta todas as requisições e das respostas no sistema, tudo que fizer no
	// sistema passa por ele
	// ex, validacao de autenticacao, redirecionamentos, dar commit e rollback de
	// transacao com o banco...
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			// pegar a sessão denovo e verificar se ele está logado
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlAtenticar = req.getServletPath();// url que esta sendo usada

			// validar se esta logado se não direciona para login
			if ((usuarioLogado == null) && !urlAtenticar.equalsIgnoreCase("/principal/ServletLogin")) {// não
																										// esta
																										// logado

				RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp?url=" + urlAtenticar);
				request.setAttribute("msg", "Porfavor realize o login");
				redirecionar.forward(request, response);
				return;// para execução e redireciona para o login do sistema
			} else {
				chain.doFilter(request, response); // deixa o processo do software continuar
			}

			connection.commit();// se der udo certo comita as alterações no banco de dados

		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher requestDispather = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			requestDispather.forward(request, response);

			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}

		}
	}

	// inicio os processos ou recursos quando o servidor sobe os projetos
	// ex, iniciar ocnexao com o banco
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConectionBanco.getConnection();// teste para executar o banco
	}

}
