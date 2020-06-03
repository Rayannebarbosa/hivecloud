package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.view.FrmMenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rayanne.l.barbosa
 */
public class LoginDAO {

    private Connection con;

    public LoginDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Metodo efetua login
    public void efetuarLogin(String email, String senha) {
        try {

            String sql = "SELECT email, senha FROM tb_usuario WHERE email =? AND senha =?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //Usuario logou
                JOptionPane.showMessageDialog(null, "Bem vindo ao Sistema!");
                FrmMenu tela = new FrmMenu();
                tela.setVisible(true);
            
            }else{
                //Login Incorreto
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado ou senha incorreta!");
            }
            
            

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " +erro);
        }

    }

}
