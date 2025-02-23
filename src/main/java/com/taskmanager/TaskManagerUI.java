package com.taskmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TaskManagerUI {
    private DatabaseHelper dbHelper;
    // JFrame - class from Swing that represents the main window of a GUI application
    private JFrame frame;
    // JTable - Swing component used to display and manipulate tabular data in rows and columns
    private JTable table;
    // DefaultTabelModel - default implementation of the TableModel interface, used to manage the data of a JTable
    private DefaultTableModel tableModel;

    public TaskManagerUI() {
        dbHelper = new DatabaseHelper();
        initializeUI();
    }

    private void initializeUI() {
    	// creates app window with the "Task Manager" title
        frame = new JFrame("Task Manager"); 
        // exits the app when user closes the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // window dimensions
        frame.setSize(600, 400);
        // uses BorderLayour for components organization
        frame.setLayout(new BorderLayout());

        // creates a table model 
        tableModel = new DefaultTableModel(new Object[]{"ID", "Titlu", "Descriere", "Data", "Status"}, 0);
        // creates a table with defined model
        table = new JTable(tableModel);
        // scrolling the table if there are too many tasks
        JScrollPane scrollPane = new JScrollPane(table);

        // buttons
        JButton addButton = new JButton("Adauga Task");
        JButton deleteButton = new JButton("Sterge Task");

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        // displays the table in the center of the window
        frame.add(scrollPane, BorderLayout.CENTER);
        // place the buttons at the bottom of the window
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this::handleAddTask);
        deleteButton.addActionListener(this::handleDeleteTask);

        // load existing tasks
        loadTasks();
        // makes the window visible
        frame.setVisible(true);
    }

    private void loadTasks() {
        tableModel.setRowCount(0); 
        List<Task> tasks = dbHelper.getAllTasks();
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{
                task.getId(), task.getTitle(), task.getDescription(), task.getDeadline(), task.getStatus()
            });
        }
    }

    private void handleAddTask(ActionEvent e) {
        String titlu = JOptionPane.showInputDialog("Titlu task:");
        String descriere = JOptionPane.showInputDialog("Descriere task:");
        String data = JOptionPane.showInputDialog("Data limită (YYYY-MM-DD):");
        String status = JOptionPane.showInputDialog("Status (TO_DO, IN_PROGRESS, DONE):");

        if (titlu != null && descriere != null && data != null && status != null) {
            Task newTask = new Task(0, titlu, descriere, java.time.LocalDate.parse(data), TaskStatus.valueOf(status));
            dbHelper.addTask(newTask);
            loadTasks(); 
        }
    }

    private void handleDeleteTask(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int taskId = (int) tableModel.getValueAt(selectedRow, 0);
            dbHelper.deleteTask(taskId);
            loadTasks(); 
        } else {
            JOptionPane.showMessageDialog(frame, "Selectează un task pentru ștergere!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManagerUI::new);
    }
}
