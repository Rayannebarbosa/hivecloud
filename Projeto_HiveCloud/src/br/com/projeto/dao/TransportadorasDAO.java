package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Transportadoras;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rayanne.l.barbosa
 */
public class TransportadorasDAO {

    private Connection con;

    public TransportadorasDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    // Metodo Cadastrar Transportadoras
    public void cadastrarTransportadoras(Transportadoras obj) {
        try {

            // Inserção dos dados das transportadoras
            String sql = "INSERT INTO tb_transportadoras "
                    + "(nome,email,empresa,telefone,celular,whatsapp,cep,estado,cidade,bairro,endereco,numero) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            // Trata as posições do comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getEmpresa());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCelular());
            stmt.setString(6, obj.getWhatsapp());
            stmt.setString(7, obj.getCep());
            stmt.setString(8, obj.getEstado());
            stmt.setString(9, obj.getCidade());
            stmt.setString(10, obj.getBairro());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());

            //Execução do comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Transportadora cadastrada com sucesso");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }

    }

    // Metodo Alterar Transportadoras
    public void alterarTransportadoras() {

    }

    // Metodo Excluir Transportadoras
    public void excluirTransportadoras() {

    }

}
