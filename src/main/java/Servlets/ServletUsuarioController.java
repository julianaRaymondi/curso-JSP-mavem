package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Dao.DAOUsuarioRepository;
import Model.ModelLogin;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//ctrl+shit+o
//@WebServlet("/ServletUsuarioController") esse caminho ja esta no web.xml
public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) /*PARA CONSULTA E DELETE*/
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
			String idUser = request.getParameter("id");

			try {
				daoUsuarioRepository.deletarID(idUser);
				request.setAttribute("msg", "Excluido com sucesso!");
			} catch (SQLException e) {

				e.printStackTrace();
			}

			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
			String idUser = request.getParameter("id");

			try {
				daoUsuarioRepository.deletarID(idUser);
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
			response.getWriter().write("Excluido com Ajax");
			
		} 
		else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarajax")) {
			
			String nomeBusca = request.getParameter("nomeBusca");

			List<ModelLogin> dadosJasonUser;
			try {
				dadosJasonUser = daoUsuarioRepository.buscaUsuario(nomeBusca);
				
				/*Mavem jacson jason Mavem, pegar a ultima dependencia e colar o pom.xml para passar a resposta lista como string : dependencia "jackson json maven"*/
				
				/*Jackson – Convert JSON array string to List
				<dependency>
					<groupId>com.fasterxml.jackson.core</groupId>
					<artifactId>jackson-databind</artifactId>
					<version>2.9.8</version>
				</dependency>*/
				
				ObjectMapper mapper= new ObjectMapper();
				String json= mapper.writeValueAsString(dadosJasonUser);
				response.getWriter().write(json);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
			try {
			String id= request.getParameter("id");		
		
			
				ModelLogin modelLogin = daoUsuarioRepository.verEditar(id);
						
						//redirecionar
				request.setAttribute("msg", "Usuario em edição");
				request.setAttribute("modelLogin", modelLogin);// preservar dados na tela
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		else {
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String msg = "Operacão realizada com sucesso";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			// inici o objeto / MODELO DA TABELA DE LOGIN
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);

			// verificar se o login já existe e se é um novo registro o id ainda é
			// null.(usuario novo)
			if (daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Já existe este login tente outro!";
				request.setAttribute("msg", msg);
			} else {

				if (modelLogin.isnovo()) {
					msg = "Gravado com sucesso!";
				} else {
					msg = "Alterado com sucesso";
				}
				// GRAVAR NO BD e receber em volta o objeto com os parametros e inserir em
				// model_login
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin);

			}
			// redirecionar
			request.setAttribute("msg", msg);
			request.setAttribute("modelLogin", modelLogin);// preservar dados na tela
			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
			redireciona.forward(request, response);

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}
