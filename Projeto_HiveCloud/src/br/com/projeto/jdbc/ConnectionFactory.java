package br.com.projeto.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author rayanne.l.barbosa
 */
public class ConnectionFactory {
    
    public Connection getConnection(){
        
        try {
            
           return DriverManager.getConnection("jdbc:mysql://localhost/transp","root","");
            
        } catch (Exception erro) {
            throw new RuntimeException(erro);
        }
    
    }
    
}
