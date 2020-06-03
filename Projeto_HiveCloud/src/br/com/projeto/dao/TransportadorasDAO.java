package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Transportadoras;
import br.com.projeto.model.WebServiceCep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public void alterarTransportadoras(Transportadoras obj) {

        try {

            // Inserção dos dados das transportadoras
            String sql = "UPDATE tb_transportadoras SET nome=?, email=?, empresa=?, telefone=?,"
                    + "celular=?, whatsapp=?, cep=?, estado=?, cidade=?, bairro=?, endereco=?, numero=? WHERE id=?";

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
            stmt.setInt(13, obj.getId());

            //Execução do comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Transportadora alterada com sucesso");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }

    }

    // Metodo Excluir Transportadoras
    public void excluirTransportadoras(Transportadoras obj) {
        try {

            // Delete dos dados das transportadoras
            String sql = "DELETE FROM tb_transportadoras WHERE ID = ?";

            // Trata as posições do comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());

            //Execução do comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Transportadora excluida com sucesso");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }

    }

    //Metodo Listar Transportadoras
    public List<Transportadoras> listarTransportadoras() {
        try {

            //Criação da lista
            List<Transportadoras> lista = new ArrayList<>();

            //Comando SQL
            String sql = "SELECT * FROM tb_transportadoras";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transportadoras obj = new Transportadoras();

                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setEmail(rs.getString("email"));
                obj.setEmpresa(rs.getString("empresa"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setWhatsapp(rs.getString("whatsapp"));
                obj.setCep(rs.getString("cep"));
                obj.setEstado(rs.getString("estado"));
                obj.setCidade(rs.getString("cidade"));
                obj.setBairro(rs.getString("bairro"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }

    }

    //Metodo consultar por nome
    public Transportadoras consultaPorNome(String nome) {
        try {
            String sql = "SELECT * FROM tb_transportadoras where nome = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();
            Transportadoras obj = new Transportadoras();

            if (rs.next()) {

                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setEmail(rs.getString("email"));
                obj.setEmpresa(rs.getString("empresa"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setWhatsapp(rs.getString("whatsapp"));
                obj.setCep(rs.getString("cep"));
                obj.setEstado(rs.getString("estado"));
                obj.setCidade(rs.getString("cidade"));
                obj.setBairro(rs.getString("bairro"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
            }
            return obj;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Transportadora não encontrada!");
            return null;
        }

    }

    //Metodo filtrar por nome (lista)
    public List<Transportadoras> filtrarPorNome(String nome) {
        try {

            //Criação da lista
            List<Transportadoras> lista = new ArrayList<>();

            //Comando SQL
            String sql = "SELECT * FROM tb_transportadoras where nome like ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transportadoras obj = new Transportadoras();

                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setEmail(rs.getString("email"));
                obj.setEmpresa(rs.getString("empresa"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setWhatsapp(rs.getString("whatsapp"));
                obj.setCep(rs.getString("cep"));
                obj.setEstado(rs.getString("estado"));
                obj.setCidade(rs.getString("cidade"));
                obj.setBairro(rs.getString("bairro"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));

                lista.add(obj);
            }

            return lista;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }

    }
    
    //Busca CEP
    	  public Transportadoras buscaCep(String cep) {
       
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);
       

        Transportadoras obj = new Transportadoras();

        if (webServiceCep.wasSuccessful()) {
            obj.setEndereco(webServiceCep.getLogradouroFull());
            obj.setCidade(webServiceCep.getCidade());
            obj.setBairro(webServiceCep.getBairro());
            obj.setEstado(webServiceCep.getUf());
            
            return obj;
        } else {
            JOptionPane.showMessageDialog(null, "Erro numero: " + webServiceCep.getResulCode());
            JOptionPane.showMessageDialog(null, "Descrição do erro: " + webServiceCep.getResultText());
            return null;
        }

    }

}
