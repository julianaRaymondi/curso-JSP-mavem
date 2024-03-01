package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.SingleConectionBanco;
import Model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto) throws SQLException {

		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		PreparedStatement preparesql = connection.prepareStatement(sql);
		preparesql.setString(1, objeto.getLogin());
		preparesql.setString(2, objeto.getSenha());
		preparesql.setString(3, objeto.getNome());
		preparesql.setString(4, objeto.getEmail());
		preparesql.execute(); //faça isso!
		connection.commit(); // e grave no bd!
		
		return this.consultaUsuario(objeto.getLogin());

	}
	
	public ModelLogin consultaUsuario(String login) throws SQLException {
		
		ModelLogin modelLogin=new ModelLogin();
		
		String sql = "SELECT*FROM model_login where upper(login)= upper('?');";
		
		PreparedStatement consulta = connection.prepareStatement(sql);		
		
		ResultSet resultado= consulta.executeQuery(); //execute , não passar o sql duas vezes
		
		while(resultado.next()) {//se tem resultado, tras ele.
			
			//preenche o objeto e retornar
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			
		}
		return modelLogin;
		
	}
}
