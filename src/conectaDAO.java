
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    public Connection connectDB() {
        Connection conn = null;

        try {
            // Adicionando useSSL=false à URL de conexão para desabilitar SSL
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?user=root&password=F010116m&useSSL=false");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }

}
