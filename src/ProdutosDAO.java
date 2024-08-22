
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        String sql = "INSERT INTO PRODUTOS (nome,valor,status) VALUES (?,?,?)";

        try {
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, produto.getNome());
            p.setInt(2, produto.getValor());
            p.setString(3, produto.getStatus());
            p.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar." + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        conn = new conectaDAO().connectDB(); // Utiliza a classe conectaDAO para obter a conexão

        String sql = "SELECT * FROM PRODUTOS";

        try {
            PreparedStatement p = conn.prepareStatement(sql);
            resultset = p.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close(); // Fecha o ResultSet no bloco finally
                }
                if (conn != null) {
                    conn.close(); // Fecha a conexão no bloco finally para garantir que seja fechado
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    public boolean venderProduto(int id) {
        Connection conn = null;
        PreparedStatement p = null;
        try {
            conn = new conectaDAO().connectDB();
            String sql = "UPDATE PRODUTOS SET status = 'Vendido' WHERE id = ?";
            p = conn.prepareStatement(sql);
            p.setInt(1, id);
            int rowsAffected = p.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (p != null) {
                    p.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
