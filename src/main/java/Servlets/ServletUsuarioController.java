package Servlets;

import java.io.IOException;
import java.sql.SQLException;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String acao= request.getParameter("acao");
		
		if(acao !=null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar"))
				{
					String idUser= request.getParameter("id");
					
						try {
							daoUsuarioRepository.deletarID(idUser);
							request.setAttribute("msg", "Excluido com sucesso!");
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
					
						request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			String msg="Operacão realizada com sucesso";
			
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

			// verificar se o login já existe e se é um novo registro o id ainda é null.(usuario novo)
			if (daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg="Já existe este login tente outro!";
				request.setAttribute("msg", msg);
			} else {
				
				if(modelLogin.isnovo())
				{
					msg="Gravado com sucesso!";
				}else {
					msg="Alterado com sucesso";
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
