
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;



public class ProjectController {
    
    public void save (Project project){
        String sql = "INSERT INTO projects ( name,"
        + "description," 
        + "createdAt,"
        + "updatedAt) VALUES (?,?,?,?)";
                
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //cria conexão com o banco
            connection = ConnectionFactory.getConnection();
            //cria um PreparedStatement, classe usada para excutar a query 
            statement = connection.prepareStatement(sql);
            //seta os valores
            statement.setString(1,project.getName());
            statement.setString (2,project.getDescription());
            statement.setDate (3, new java.sql.Date (project.getCreatedAt().getTime()));
            statement.setDate (4, new java.sql.Date (project.getUpdatedAt().getTime()));
            //manda executar
            statement.execute();
        } catch (SQLException ex){
            throw new RuntimeException ("Erro ao salvar o projeto", ex);
        } finally {
            
            try{
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
             }    
        }
    }
    
    public void update (Project project){
        
        String sql = "UPDATE projects SET "
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updatedAt = ? "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //cria conexão com o banco
            connection = ConnectionFactory.getConnection();
            //cria um PreparedStatement, classe usada para excutar a query 
            statement = connection.prepareStatement(sql);
            //seta os valores
            statement.setString(1,project.getName());
            statement.setString(2,project.getDescription());
            statement.setDate(3, new java.sql.Date (project.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date (project.getUpdatedAt().getTime()));
            statement.setInt (5, project.getId());
            //executa a query para inserção de dados
            statement.execute();
            
        } catch (SQLException ex){
            throw new RuntimeException ("Erro ao atualizar o projeto ",ex);
        } finally{
             try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
    }      
    
    public void removeById (int id) {
        String sql = "DELETE FROM projects WHERE id =?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();        
        } catch (SQLException ex){
            throw new RuntimeException ("Erro ao deletar o projeto ", ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
            
        }
        
    }
    
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM projects";
        
        List <Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        //classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;
        
        try {
            
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            //enquanto existir dados do banco de dados, faça
            while (resultSet.next()){
                
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName (resultSet.getString("name"));
                project.setDescription (resultSet.getString("description"));
                project.setCreatedAt (resultSet.getDate("createdAt"));
                project.setUpdatedAt (resultSet.getDate("updatedAt"));
                
                //adiciono o projeto na lista de projetos
                projects.add(project);
                
            }
        } catch (SQLException ex) {            
            throw new RuntimeException ("Erro ao buscar o projeto ", ex);            
        } finally {try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
        return projects;       
    }
    
}
