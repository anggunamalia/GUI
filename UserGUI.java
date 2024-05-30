import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField idField;
    private JTextField nameField;
    private JTextField favoriteMovieField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private String jdbcUrl = "jdbc:mysql://localhost:3306/gui_app_db";
    private String username = "root"; // ganti dengan username MySQL Anda
    private String password = "*Goodtoberight1"; // ganti dengan password MySQL Anda

    public UserGUI() {
        // Set up frame
        setTitle("User Data");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up table model and table
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Favorite Movie");
        loadData();

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Create panel for input fields and buttons
        JPanel inputPanel = new JPanel();
        GroupLayout layout = new GroupLayout(inputPanel);
        inputPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        idField.setEnabled(false); // Disable ID field since it's auto-increment

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel favoriteMovieLabel = new JLabel("Favorite Movie:");
        favoriteMovieField = new JTextField(20);

        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        // Group layout for input panel
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idLabel)
                        .addComponent(nameLabel)
                        .addComponent(favoriteMovieLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idField)
                        .addComponent(nameField)
                        .addComponent(favoriteMovieField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(addButton)
                        .addComponent(updateButton)
                        .addComponent(deleteButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel)
                        .addComponent(idField)
                        .addComponent(addButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField)
                        .addComponent(updateButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(favoriteMovieLabel)
                        .addComponent(favoriteMovieField)
                        .addComponent(deleteButton))
        );

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Make frame visible
        setVisible(true);
    }

    private void loadData() {
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            // Clear existing data
            model.setRowCount(0);

            // Add data to table model
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String favoriteMovie = resultSet.getString("favorite_movie");
                model.addRow(new Object[]{id, name, favoriteMovie});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUser() {
        String name = nameField.getText();
        String favoriteMovie = favoriteMovieField.getText();

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO users (name, favorite_movie) VALUES ('" + name + "', '" + favoriteMovie + "')";
            statement.executeUpdate(sql);

            statement.close();
            connection.close();

            // Reload data to reflect new entry
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Clear input fields
        nameField.setText("");
        favoriteMovieField.setText("");
    }

    private void updateUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to update");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        String name = nameField.getText();
        String favoriteMovie = favoriteMovieField.getText();

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            String sql = "UPDATE users SET name='" + name + "', favorite_movie='" + favoriteMovie + "' WHERE id=" + id;
            statement.executeUpdate(sql);

            statement.close();
            connection.close();

            // Reload data to reflect updates
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Clear input fields
        nameField.setText("");
        favoriteMovieField.setText("");
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM users WHERE id=" + id;
            statement.executeUpdate(sql);

            statement.close();
            connection.close();

            // Reload data to reflect deletion
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Clear input fields
        nameField.setText("");
        favoriteMovieField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UserGUI();
            }
        });
    }
}
