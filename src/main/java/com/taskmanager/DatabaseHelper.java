package com.taskmanager;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
	
	// private - this variable is visible only in the class where it is defined
	// static - it belongs to the class, not to any specific instance (it can be accesed without instantiating an object)
	// final - it has a constant value that can't be changed after it has been passed
	// String DB_URL - variable that retains the database URL
	// jdbc:sqlite: - we use JDBC to connect at a SQLite database
	// taskmanager.db - name of the database
	private static final String DB_URL = "jdbc:sqlite:taskmanager.db";
	
	// constructor 
	public DatabaseHelper() {
        connectToDatabase();
        createTaskTable();
    }
	
	// method to set the database connection
	public void connectToDatabase() {
		// creates a Connection object
		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			// the connection was succesful 
            if (conn != null) {
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
	}
	
	// method to create the task table
	public void createTaskTable() {
		   // sql command for creating a table with its attributes
		   String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "titlu TEXT NOT NULL, "
                   + "descriere TEXT, "
                   + "dataLimita TEXT, "
                   + "status TEXT NOT NULL"
                   + ");";

		   // tries creating a connection 
	        try (Connection conn = DriverManager.getConnection(DB_URL);
	        	// creates a Statement object
	        	// Statement class from JDBC is used to execute SQL commands on the database
	            Statement stmt = conn.createStatement()) {
	        	// execute sql command
	            stmt.execute(sql);
	            System.out.println("Table 'tasks' is ready.");
	        } catch (SQLException e) {
	            System.out.println("Error creating table: " + e.getMessage());
	        }
	}
	
	// method for adding a task 
	public boolean addTask(Task task) {
		// sql command
	    String sql = "INSERT INTO tasks (titlu, descriere, dataLimita, status) VALUES (?, ?, ?, ?);";

	    try (Connection conn = DriverManager.getConnection(DB_URL);
	    	// PreparedStatement is a JDBC class that allows executing parameterized SQL queries,
	    	// meaning queries that use placeholders (?) instead of hardcoded values.
	        PreparedStatement pstmt = conn.prepareStatement(sql)) {

	    	// set the parameter in the sql query with the value returned from the method
	        pstmt.setString(1, task.getTitle());
	        pstmt.setString(2, task.getDescription());
	        pstmt.setString(3, task.getDeadline().toString()); 
	        pstmt.setString(4, task.getStatus().name());  

	        //execute the query
	        int rowsInserted = pstmt.executeUpdate();
	        return rowsInserted > 0; 

	    } catch (SQLException e) {
	        System.out.println("Error inserting task: " + e.getMessage());
	        return false;
	    }
	}
	
	// method for getting all the tasks
	public List<Task> getAllTasks(){
		// list of tasks
		List<Task> taskList = new ArrayList<>();
		// sql query
		String sql = "SELECT * FROM tasks";
		
		// creates a connection
		try(Connection conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				// ResultSet - class of JDBC that represents the result of a sql query
				// when executing a query, the database returns a set of results
				// ResultSet lets us go through it and extract data of each row
				ResultSet rs = stmt.executeQuery(sql)){
			
			// gets all the tasks
			// rs.next() iterates row by row and checks if there is another row in the set 
			while (rs.next()) {
	            int id = rs.getInt("id");
	            String titlu = rs.getString("titlu");
	            String descriere = rs.getString("descriere");
	            LocalDate dataLimita = LocalDate.parse(rs.getString("dataLimita"));
	            // converts the status from String (database) to TaskStatus enum
	            // the value stored in the database must exactly match the enum name
	            TaskStatus status = TaskStatus.valueOf(rs.getString("status"));
	            Task task = new Task(id, titlu, descriere, dataLimita, status);
	            taskList.add(task);
	        }
			
		}catch (SQLException e) {
			System.out.println("Error getting all tasks: " + e.getMessage());
		}
		
		return taskList;
	}
	
	// method for updating a task
	public boolean updateTask(Task task) {
		// sql command
		String sql = "UPDATE tasks SET titlu = ?, descriere = ?, dataLimita = ?, status = ? WHERE id = ?;";
		
		// creates a connection
		try (Connection conn = DriverManager.getConnection(DB_URL);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setString(1, task.getTitle());
		        pstmt.setString(2, task.getDescription());
		        pstmt.setString(3, task.getDeadline().toString()); 
		        pstmt.setString(4, task.getStatus().name());  
		        pstmt.setInt(5, task.getId());
		        
		        int rowsInserted = pstmt.executeUpdate();
		        return rowsInserted > 0; 

		    } catch (SQLException e) {
		        System.out.println("Error updating task: " + e.getMessage());
		        return false;
		    }
	}

	// method to delete a task
	public boolean deleteTask(int id) {
		// sql command
		String sql = "DELETE FROM tasks where id = ?;";
	
		try (Connection conn = DriverManager.getConnection(DB_URL);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

			    // gets the id of the task to be deleted
			    pstmt.setInt(1, id);
		        int rowsInserted = pstmt.executeUpdate();
		        return rowsInserted > 0; 

		    } catch (SQLException e) {
		        System.out.println("Error deleting task: " + e.getMessage());
		        return false;
		    }
	}
	
}
