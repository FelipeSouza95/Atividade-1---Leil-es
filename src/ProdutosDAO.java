
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

    // Método para listar todos os produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = new conectaDAO().connectDB();
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Para debug
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Para debug
            }
        }
        return lista;
    }

    // Método para pesquisar produtos vendidos por ID ou nome
    public ArrayList<ProdutosDTO> pesquisarProdutosVendidos(String consulta) {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = new conectaDAO().connectDB();
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido' AND (id = ? OR nome LIKE ?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, consulta);
            pst.setString(2, "%" + consulta + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                lista.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}
