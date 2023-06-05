package dao;

import entidades.SoftwareLicenca;
import entidades.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoSoftwareLicenca {

    private DaoCliente daoCliente;

    public DaoSoftwareLicenca(DaoCliente daoCliente) {
        this.daoCliente = daoCliente;
    }

    public Connection getConexao() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/trabalhopt2";
        String username = "root";
        String password = "";

        Connection conexao = DriverManager.getConnection(dbURL, username, password);

        return conexao;
    }

    public void cadastrar(SoftwareLicenca softwareLicenca) throws SQLException {
        Connection conexao = this.getConexao();

        String sql = "INSERT INTO softwarelicenca (nomeSoftware, cliente, emailCliente) VALUES (?, ?, ?)";

        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, softwareLicenca.getNomeSoftware());
        ps.setInt(2, softwareLicenca.getCliente().getId());
        ps.setString(3, softwareLicenca.getCliente().getEmailCliente());

        ps.executeUpdate();
    }

    public void atualizar(SoftwareLicenca softwareLicenca) throws SQLException {
        Connection conexao = this.getConexao();

        String sql = "UPDATE softwarelicenca SET nomeSoftware = ?, cliente = ?, emailCliente = ? WHERE id = ?";

        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, softwareLicenca.getNomeSoftware());
        ps.setInt(2, softwareLicenca.getCliente().getId());
        ps.setString(3, softwareLicenca.getCliente().getEmailCliente());
        ps.setInt(4, softwareLicenca.getId());

        ps.executeUpdate();
    }

    public void excluir(int id) throws SQLException {
        Connection conexao = this.getConexao();

        String sql = "DELETE FROM softwarelicenca WHERE id = ?";

        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();
    }

    public List<SoftwareLicenca> listarLicencas() throws SQLException {
        Connection conexao = this.getConexao();

        String sql = "SELECT * FROM softwarelicenca";

        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();

        List<SoftwareLicenca> softwareLicencas = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nomeSoftware = resultSet.getString("nomeSoftware");
            int idCliente = resultSet.getInt("cliente");
            String emailCliente = resultSet.getString("emailCliente");

            SoftwareLicenca softwareLicenca = new SoftwareLicenca();
            softwareLicenca.setId(id);
            softwareLicenca.setNomeSoftware(nomeSoftware);

            Cliente cliente = this.daoCliente.buscarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                continue;
            }

            cliente.setEmailCliente(emailCliente);

            softwareLicenca.setCliente(cliente);

            softwareLicencas.add(softwareLicenca);
        }

        return softwareLicencas;
    }

    public void excluirPorCliente(int idCliente) {
        // TODO Auto-generated method stub
    }


    public SoftwareLicenca buscarLicenca(int idLicenca) throws SQLException {
        Connection conexao = this.getConexao();

        String sql = "SELECT * FROM softwarelicenca WHERE id = ?";

        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, idLicenca);

        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nomeSoftware = resultSet.getString("nomeSoftware");
            int idCliente = resultSet.getInt("cliente");
            String emailCliente = resultSet.getString("emailCliente");

            SoftwareLicenca softwareLicenca = new SoftwareLicenca();
            softwareLicenca.setId(id);
            softwareLicenca.setNomeSoftware(nomeSoftware);

            Cliente cliente = this.daoCliente.buscarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return null;
            }

            cliente.setEmailCliente(emailCliente);

            softwareLicenca.setCliente(cliente);

            return softwareLicenca;
        }

        return null; // Retorna null se a licença não for encontrada
    }

}
