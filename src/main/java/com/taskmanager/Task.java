package com.taskmanager;
import java.time.LocalDate;

public class Task {
	private int id;
	private String title;
	private String description;
	private LocalDate deadline;
	private TaskStatus status;

	// implicit constructor
	public Task() {
		this.status = TaskStatus.TO_DO;
	}
	
	// parameterized constructor
	public Task(int id, String title, String description, LocalDate deadline, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }
	
	// getters and setters 
    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public String getTitle() {
		return title;
	}

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
		return description;
	}
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getDeadline() {
		return deadline;
	}
    
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    
    public int getId() {
		return id;
	}
    
    public void setId(int id) {
        this.id = id;
    }
    
    // overridden toString() method that returns a string representation of a Task object 
    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", titlu='" + title + '\'' +
               ", descriere='" + description + '\'' +
               ", dataLimita=" + deadline +
               ", status=" + status +
               '}';
    }

	
}