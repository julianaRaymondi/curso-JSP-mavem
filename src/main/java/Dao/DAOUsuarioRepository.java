package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.log.UserDataHelper.Mode;

import Connection.SingleConectionBanco;
import Model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto) throws SQLException {

		if (objeto.isnovo()) {
			String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
			PreparedStatement preparesql = connection.prepareStatement(sql);
			preparesql.setString(1, objeto.getLogin());
			preparesql.setString(2, objeto.getSenha());
			preparesql.setString(3, objeto.getNome());
			preparesql.setString(4, objeto.getEmail());
			preparesql.execute(); // faça isso!
			connection.commit(); // e grave no bd!

			return this.consultaUsuario(objeto.getLogin());
		} else {
			this.atualizaUsuario(objeto);

		}
		return objeto;
	}
	
	
	public List<ModelLogin> buscaUsuario(String nome) throws SQLException{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT*FROM model_login where upper(nome) like upper(?) and useradmin is false";
		PreparedStatement busca= connection.prepareStatement(sql);
		busca.setString(1, "%"+nome+"%");
		
		ResultSet resultado= busca.executeQuery();
		
		
		while(resultado.next()) {
			
			ModelLogin modelLogin=new ModelLogin(); 
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			
			retorno.add(modelLogin);
		}
		
		
		
		return retorno;
	}
	

	public ModelLogin consultaUsuario(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT*FROM model_login where upper(login)= upper('" + login + "') and useradmin is false;";

		PreparedStatement consulta = connection.prepareStatement(sql);

		ResultSet resultado = consulta.executeQuery(); // execute , não passar o sql duas vezes

		while (resultado.next()) {// se tem resultado, tras ele.

			// preenche o objeto e retornar
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));

		}
		return modelLogin;

	}
	
	

	// se já existe o login cadastrado
	public boolean validaLogin(String login) throws SQLException {

		String sql = "select count(*) > 0 as existe from model_login where upper(login) = upper('" + login + "');";
		PreparedStatement consulta = connection.prepareStatement(sql);
		ResultSet resultado = consulta.executeQuery(); // execute , não passar o sql duas vezes

		resultado.next();// para entrar nos resultados e avançar como se fosse um ponteiro

		return resultado.getBoolean("existe");

	}

	// atualizar
	public ModelLogin atualizaUsuario(ModelLogin objeto) throws SQLException {

		String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id=" + objeto.getId() + " and useradmin is false;";
		PreparedStatement atualiza = connection.prepareStatement(sql);
		atualiza.setString(1, objeto.getLogin());
		atualiza.setString(2, objeto.getSenha());
		atualiza.setString(3, objeto.getNome());
		atualiza.setString(4, objeto.getEmail());

		atualiza.executeUpdate(); // faça isso!
		connection.commit(); // e grave no bd!

		return objeto;
	}
	
	//deletar
	public void deletarID(String idUser) throws SQLException {
		
		String sql="DELETE FROM model_login WHERE id=? and useradmin is false;";
		PreparedStatement delete= connection.prepareStatement(sql);
		delete.setLong(1, Long.parseLong(idUser));
		delete.executeUpdate();
		
		connection.commit();
	}

	public ModelLogin verEditar(String id) throws SQLException {
		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT*FROM model_login where id=? and useradmin is false;";

		PreparedStatement consulta = connection.prepareStatement(sql);
		consulta.setLong(1, Long.parseLong(id));

		ResultSet resultado = consulta.executeQuery(); // execute , não passar o sql duas vezes

		while (resultado.next()) {// se tem resultado, tras ele.

			// preenche o objeto e retornar
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));

		}
		return modelLogin;
		
	}
	public List<ModelLogin> consultaTodosUsuarios() throws SQLException {

		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT*FROM model_login where useradmin is false";

		PreparedStatement consulta = connection.prepareStatement(sql);

		ResultSet resultado = consulta.executeQuery(); // execute , não passar o sql duas vezes

		while (resultado.next()) {// se tem resultado, tras ele.
			
			ModelLogin modelLogin = new ModelLogin();
			// preenche o objeto e retornar
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			retorno.add(modelLogin);
		}
		return retorno;

	}
	

	

	
}
