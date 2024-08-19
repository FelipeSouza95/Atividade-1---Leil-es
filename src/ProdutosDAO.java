
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

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

        return listagem;
    }

}
