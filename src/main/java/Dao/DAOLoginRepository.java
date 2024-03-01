package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.SingleConectionBanco;
import Model.ModelLogin;

public class DAOLoginRepository {

	private Connection connection;

	public DAOLoginRepository() { // quando instaciar a classe dao já faz a conexao.
		connection = SingleConectionBanco.getConnection();
	}

	// metodo para validar o login, se o usuario existe true se nao false
	public boolean validarAutenticacao(ModelLogin modelLogin) throws SQLException {
		String sql = "select * from model_login where login= ? and senha= ?";
		//Caso queria maiusculo no Bacno Postegres : String sql = "select * from model_login where upper(login)= upper(?) and upper(senha)= upper(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());

		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return true;// autenticado
		}
		return false;// não autenticado

	}

}
