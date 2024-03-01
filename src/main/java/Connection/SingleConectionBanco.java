package Connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.managed.XAConnectionFactory;

public class SingleConectionBanco {
	// url, ususario e senha.

	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;

	public static Connection getConnection() {
		return connection;
	}

	static {
		try {
			conectar();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // caso a classe seja chamada direta
	}

	public SingleConectionBanco() throws ClassNotFoundException, SQLException {
		conectar();// quando se criar uma nova instancia ele conecta, somente caso esteja null, se
					// nao retorna a mesma conecção
	}

	private static void conectar() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			Class.forName("org.postgresql.Driver");// carrega o drive de conexao do banco
			connection = DriverManager.getConnection(banco, user, senha);// atribuir a conexao a variavel de connection
			connection.setAutoCommit(false);// FALSE PARA NAO EFETUAR ALTERaCOES NO BANCO SEM O NOSSO COMANDO
		}

	}

}