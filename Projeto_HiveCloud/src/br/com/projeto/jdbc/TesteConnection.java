package br.com.projeto.jdbc;
import javax.swing.JOptionPane;

/**
 *
 * @author rayanne.l.barbosa
 */
public class TesteConnection {
    
    public static void main(String[] args) {
        try {
            
            new ConnectionFactory().getConnection();
            JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
            
        } catch (Exception erro) {
            
            JOptionPane.showMessageDialog(null, "Ocorreu o erro: " + erro);
        }
    }
    
}
