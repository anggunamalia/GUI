import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {

    public static void main(String[] args) {
        // Konfigurasi koneksi database
        String jdbcUrl = "jdbc:mysql://localhost:3306/gui_app_db";
        String username = "root"; // ganti dengan username MySQL Anda
        String password = "*Goodtoberight1"; // ganti dengan password MySQL Anda

        try {
            // Membuat koneksi ke database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database.");

            // Membuat statement
            Statement statement = connection.createStatement();

            // Menjalankan query dan mendapatkan hasil
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            // Menampilkan data dari tabel
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
            }

            // Menutup koneksi
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
