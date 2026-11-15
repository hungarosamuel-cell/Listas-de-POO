package br.com.autotech.dao;

import br.com.autotech.factory.ConexaoFactory;
import br.com.autotech.model.Peca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * KIT DE DESENVOLVIMENTO DE LABORATÓRIO • PECADAO (AUTOTECH)
 * ============================================================================
 * Persistência local em SQLite (autotech.db) para a oficina AutoTech funcionar
 * mesmo sem sinal de internet.
 * ============================================================================
 */
public class PecaDao {

    /**
     * MISSÃO 1: DDL automático. Cria a tabela "pecas" se ela ainda não existir.
     */
    public void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS pecas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                codigo TEXT UNIQUE NOT NULL,
                nome TEXT NOT NULL,
                preco REAL NOT NULL,
                estoque INTEGER NOT NULL
            );
        """;

        try (Connection conn = ConexaoFactory.obterConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("🔧 [AutoTech] Tabela 'pecas' verificada/criada com sucesso!");

        } catch (SQLException e) {
            System.err.println("❌ Erro na Missão 1 (criarTabela): " + e.getMessage());
        }
    }

    /**
     * MISSÃO 2: INSERT com resgate da chave primária gerada pelo SQLite.
     *
     * @param peca Objeto a ser salvo.
     * @return O mesmo objeto com o ID preenchido.
     */
    public Peca salvar(Peca peca) {
        String sql = "INSERT INTO pecas (codigo, nome, preco, estoque) VALUES (?, ?, ?, ?);";

        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Os parâmetros do JDBC começam no índice 1
            stmt.setString(1, peca.getCodigo());
            stmt.setString(2, peca.getNome());
            stmt.setDouble(3, peca.getPreco());
            stmt.setInt(4, peca.getEstoque());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                // Resgata o id AUTOINCREMENT e sincroniza com o objeto em memória
                try (ResultSet chaves = stmt.getGeneratedKeys()) {
                    if (chaves.next()) {
                        peca.setId(chaves.getLong(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro na Missão 2 (salvar): " + e.getMessage());
        }

        return peca;
    }

    /**
     * MISSÃO 3: consulta geral de peças, ordenadas pelo nome.
     *
     * @return Lista de peças encontradas no banco.
     */
    public List<Peca> listarTodas() {
        List<Peca> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nome, preco, estoque FROM pecas ORDER BY nome;";

        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Navega por todas as linhas devolvidas, lendo as colunas pelo nome
            while (rs.next()) {
                Peca p = new Peca(
                    rs.getLong("id"),
                    rs.getString("codigo"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("estoque")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro na Missão 3 (listarTodas): " + e.getMessage());
        }

        return lista;
    }

    /**
     * MISSÃO 4: consulta por código (UNIQUE), por isso if em vez de while.
     *
     * @param codigo Código alfanumérico da peça.
     * @return Objeto Peca se existir, ou null se não encontrada.
     */
    public Peca buscarPorCodigo(String codigo) {
        String sql = "SELECT id, codigo, nome, preco, estoque FROM pecas WHERE codigo = ?;";

        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Peca(
                        rs.getLong("id"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro na Missão 4 (buscarPorCodigo): " + e.getMessage());
        }

        return null;
    }

    /**
     * MISSÃO 5: baixa de estoque no balcão.
     * O "estoque >= ?" no WHERE impede que o estoque fique negativo.
     *
     * @param id Identificador da peça.
     * @param quantidadeRetirada Quantidade a subtrair.
     * @return true se atualizou, false se estoque insuficiente ou ID inexistente.
     */
    public boolean darBaixaEstoque(Long id, int quantidadeRetirada) {
        String sql = "UPDATE pecas SET estoque = estoque - ? WHERE id = ? AND estoque >= ?;";

        try (Connection conn = ConexaoFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantidadeRetirada);
            stmt.setLong(2, id);
            stmt.setInt(3, quantidadeRetirada);

            // Linhas afetadas: 0 significa ID inexistente ou estoque insuficiente
            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erro na Missão 5 (darBaixaEstoque): " + e.getMessage());
            return false;
        }
    }
}
