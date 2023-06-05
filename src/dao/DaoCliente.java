package dao;

import entidades.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoCliente {

	public Connection getConexao() throws SQLException {
		String dbURL = "jdbc:mysql://localhost:3306/trabalhopt2";
		String username = "root";
		String password = "";

		Connection conexao = DriverManager.getConnection(dbURL, username, password);

		return conexao;
	}

	public void cadastrar(Cliente cliente) throws SQLException {
		Connection conexao = this.getConexao();

		String sql = "INSERT INTO cliente (nomeCliente, emailCliente, cpf) VALUES (?, ?, ?)";

		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setString(1, cliente.getNomeCliente());
		ps.setString(2, cliente.getEmailCliente());
		ps.setString(3, cliente.getCpf());

		ps.executeUpdate();
	}

	public void excluir(int id) throws SQLException {
		Connection conexao = this.getConexao();

		String sql = "DELETE FROM cliente WHERE id = ?";

		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setInt(1, id);

		ps.executeUpdate();
	}

	public List<Cliente> listarClientes() throws SQLException {
		Connection conexao = this.getConexao();

		String sql = "SELECT * FROM cliente";

		PreparedStatement ps = conexao.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();

		List<Cliente> clientes = new ArrayList<>();

		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String nomeCliente = resultSet.getString("nomeCliente");
			String emailCliente = resultSet.getString("emailCliente");
			String cpf = resultSet.getString("cpf");

			Cliente cliente = new Cliente();
			cliente.setId(id);
			cliente.setNomeCliente(nomeCliente);
			cliente.setEmailCliente(emailCliente);
			cliente.setCpf(cpf);

			clientes.add(cliente);
		}

		return clientes;
	}

	public Cliente buscarCliente(int id) throws SQLException {
		Connection conexao = this.getConexao();

		String sql = "SELECT * FROM cliente WHERE id = ?";

		PreparedStatement ps = conexao.prepareStatement(sql);
		ps.setInt(1, id);

		ResultSet resultSet = ps.executeQuery();

		if (resultSet.next()) {
			Cliente cliente = new Cliente();
			cliente.setId(id);
			cliente.setNomeCliente(resultSet.getString("nomeCliente"));
			cliente.setEmailCliente(resultSet.getString("emailCliente"));
			cliente.setCpf(resultSet.getString("cpf"));

			return cliente;
		}

		return null;
	}

	public void atualizar(Cliente cliente) throws SQLException {
		String sql = "UPDATE cliente SET nomeCliente = ?, emailCliente = ?, cpf = ? WHERE id = ?";

		try (Connection conexao = this.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

			ps.setString(1, cliente.getNomeCliente());
			ps.setString(2, cliente.getEmailCliente());
			ps.setString(3, cliente.getCpf());
			ps.setInt(4, cliente.getId());

			ps.executeUpdate();
		}
	}
}
