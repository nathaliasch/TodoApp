
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;


public class TaskController {
    
    public void save (Task task){
        
        String sql = "INSERT INTO tasks ( idProject,"
        + "name," 
        + "description," 
        + "completed,"
        + "notes,"
        + "deadline,"
        + "createdAt,"
        + "updatedAt) VALUES (?,?,?,?,?,?,?,?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,task.getIdProject());
            statement.setString(2,task.getName());
            statement.setString (3,task.getDescription());
            statement.setBoolean (4,task.isCompleted());
            statement.setString (5,task.getNotes());
            statement.setDate (6,new Date (task.getDeadline().getTime()));
            statement.setDate (7, new Date (task.getCreatedAt().getTime()));
            statement.setDate (8, new Date (task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (SQLException ex){
            throw new RuntimeException ("Erro ao salvar a tarefa", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);      
        }
    }    
    
    public void update (Task task){
   
        String sql = "UPDATE tasks SET"
                + "idProject = ?,"
                + "name = ?,"
                + "decription = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "completed =?,"
                + "createdAt = ?,"
                + "updatedAt = ? "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1,task.getIdProject());
            statement.setString(2,task.getName());
            statement.setString(3,task.getDescription());
            statement.setString(4,task.getNotes());
            statement.setDate(5,new Date (task.getDeadline().getTime()));
            statement.setBoolean(6,task.isCompleted());
            statement.setDate(7, new Date (task.getCreatedAt().getTime()));
            statement.setDate(8, new Date (task.getUpdatedAt().getTime()));
            statement.setInt (9, task.getId());
            statement.execute();
        } catch (SQLException ex){
            throw new RuntimeException ("Erro ao atualizar a tarefa ", ex);
        }
    }
    
    public void removeById (int taskId) {
        String sql = "DELETE FROM tasks WHERE id =?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();        
        } catch (SQLException ex){
            throw new RuntimeException ("Erro ao deletar a tarefa ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public List<Task> getAll(int idProject){
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List <Task> tasks = new ArrayList<Task>();
        
        try {
            
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject (resultSet.getInt("idProject"));
                task.setName (resultSet.getString("name"));
                task.setDescription (resultSet.getString("description"));
                task.setNotes (resultSet.getString("notes"));
                task.setCompleted (resultSet.getBoolean("completed"));
                task.setDeadline (resultSet.getDate("deadline"));
                task.setCreatedAt (resultSet.getDate("createdAt"));
                task.setUpdatedAt (resultSet.getDate("updatedAt"));
                
                tasks.add(task);
                
            }
        } catch (SQLException ex) {            
            throw new RuntimeException ("Erro ao inserir a tarefa ", ex);            
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }          
        return tasks;       
    }
     
   
    
}
