
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    private Connection conn;

    // Abre uma nova conexão com o banco de dados
    private void openConnection() throws SQLException {
        conn = new conectaDAO().connectDB();
    }

    // Fecha a conexão com o banco de dados
    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO PRODUTOS (nome, valor, status) VALUES (?, ?, ?)";
        try {
            openConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            p.setString(1, produto.getNome());
            p.setDouble(2, produto.getValor()); // Use double para valor se for necessário
            p.setString(3, produto.getStatus());
            p.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Cadastrado com Sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTOS";
        try {
            openConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet resultset = p.executeQuery();
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor")); // Use double para valor se for necessário
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return lista;
    }

    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTOS WHERE status = 'Vendido'";
        try {
            openConnection();
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor")); // Use double para valor se for necessário
                produto.setStatus(rs.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return lista;
    }

    public ArrayList<ProdutosDTO> pesquisarProdutosVendidos(String consulta) {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTOS WHERE status = 'Vendido' AND (nome LIKE ? OR id = ?)";
        try {
            openConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + consulta + "%");
            pstmt.setInt(2, Integer.parseInt(consulta)); // Conversão para int pode causar exceção se a consulta não for numérica
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor")); // Use double para valor se for necessário
                produto.setStatus(rs.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar produtos vendidos: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return lista;
    }
}
