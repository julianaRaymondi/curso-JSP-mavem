package Servlets;

import java.io.IOException;

import Dao.DAOLoginRepository;
import Model.ModelLogin;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//servlets são controllers

@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) /* Mapeamento de url que vem da tela */
public class ServletsLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

	public ServletsLogin() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao=request.getParameter("acao");
		
		if(acao!=null && acao.isEmpty() && acao.equalsIgnoreCase("logout")){
			
			request.getSession().invalidate(); // invalidada a sessão
			
			RequestDispatcher requestDispather = request.getRequestDispatcher("/index.jsp");
			
			requestDispather.forward(request, response);
			
		}else {
			doPost(request, response);

		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				// if ((modelLogin.getLogin().equalsIgnoreCase("admin")) // ANTES DA BSE DE
				// DADOS CONECTADA
				// && (modelLogin.getSenha().equalsIgnoreCase("admin")))
				if (daoLoginRepository.validarAutenticacao(modelLogin)) {

					request.getSession().setAttribute("usuario", modelLogin.getLogin());// manter o usuario logado,
																						// coloca
																						// na sessão

					if (url == null || url.equals("null")) {// eu quero que a tela que apareca depois do login seja
															// essa.
						url = "principal/principal.jsp";
					}

					RequestDispatcher requestDispather = request.getRequestDispatcher(url);// redireciona para o site
																							// que
																							// antes queriam entrar
					requestDispather.forward(request, response);

				} else {
					RequestDispatcher requestDispather = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "informe todos os campos corretamente!");
					requestDispather.forward(request, response);

				}

			} else {
				RequestDispatcher requestDispather = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "preencha todos os campos!");
				requestDispather.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher requestDispather = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			requestDispather.forward(request, response);
		}

	}

}
