# Task-Manager
This is a simple Task Manager application that allows users to add, edit, and delete tasks.

1. Create a Maven Project 
 1.1. Open Eclipse → File → New → Project... → Maven → Maven Project → Next  
 1.2. Select Create a simple project (skip archetype selection) → Next  
2. Add SQLite Dependency in pom.xml (located in the root directory).
 - SQLite is a **lightweight, serverless, self-contained SQL database engine.  
 - Unlike MySQL or PostgreSQL, it does not require a separate server.  
3. Project Structure:  
📂com.taskmanager (package)  
   ├── 📄 DatabaseHelper.java – Manages database operations 
   ├── 📄 Task.java – Model for tasks  
   ├── 📄 TaskManagerUI.java – User interface with Swing
   ├── 📄 TaskStatus.java – Enum that defines a set of predefined constants  
