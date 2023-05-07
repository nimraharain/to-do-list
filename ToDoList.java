import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToDoList extends JFrame {
    private ArrayList<Task> tasks;
    private JList<Task> taskList;
    private DefaultListModel<Task> listModel;

    public ToDoList() {
        tasks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setSelectedIndex(-1);
        JScrollPane scrollPane = new JScrollPane(taskList);

        JButton addButton = new JButton("Add");
        JButton completeButton = new JButton("Complete");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog(null, "Enter task:");
                if (taskName != null && taskName.length() > 0) {
                    Task task = new Task(taskName);
                    tasks.add(task);
                    listModel.addElement(task);
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task task = listModel.get(selectedIndex);
                    task.setCompleted(true);
                    listModel.remove(selectedIndex);
                    if (task.isCompleted()) {
                        listModel.addElement(task);
                    } else {
                        listModel.add(selectedIndex, task);
                    }
                    taskList.setSelectedIndex(-1);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    tasks.remove(selectedIndex);
                    listModel.remove(selectedIndex);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        setTitle("To-Do List");
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class Task {
        private String name;
        private boolean completed;

        public Task(String name) {
            this.name = name;
            this.completed = false;
        }

        public String toString() {
            if (completed) {
                return "<html><strike>" + name + "</strike></html>";
            } else {
                return name;
            }
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ToDoList todo = new ToDoList();
                todo.setVisible(true);
            }
        });
    }
}
