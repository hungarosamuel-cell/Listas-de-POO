package br.com.starlog.main;

import br.com.starlog.dao.ConexaoFactory;
import br.com.starlog.dao.ProdutoDAO;
import br.com.starlog.dao.ProdutoDAOSqlite;
import br.com.starlog.model.Produto;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Começa de um banco limpo para a simulação ser repetível
        new File(ConexaoFactory.ARQUIVO_BANCO).delete();

        ProdutoDAO dao = new ProdutoDAOSqlite();

        // [Create]
        System.out.println("=== [CREATE] ===");
        Produto teclado = new Produto("Teclado Mecânico RGB", 349.90, 10);
        Produto mousepad = new Produto("Mousepad Speed XL", 59.90, 25);
        dao.salvar(teclado);
        dao.salvar(mousepad);
        System.out.println("Teclado salvo com ID: " + teclado.getId());
        System.out.println("Mousepad salvo com ID: " + mousepad.getId());

        // [Read]
        System.out.println("\n=== [READ] ===");
        imprimirTabela(dao.listarTodos());

        // [Update]
        System.out.println("\n=== [UPDATE] ===");
        teclado.setEstoque(15);
        teclado.setPreco(289.90);
        boolean atualizado = dao.atualizar(teclado);
        System.out.println("Atualizou? " + atualizado);
        Optional<Produto> recarregado = dao.buscarPorId(teclado.getId());
        System.out.println(recarregado.map(Produto::toString).orElse("Produto não encontrado."));

        // [Delete]
        System.out.println("\n=== [DELETE] ===");
        System.out.println("Excluiu Mousepad (ID " + mousepad.getId() + ")? " + dao.excluir(mousepad.getId()));
        boolean excluiuInexistente = dao.excluir(888L);
        if (!excluiuInexistente) {
            System.out.println("Aviso: nenhum produto com ID 888 foi encontrado para exclusão.");
        }

        // [Final State]
        System.out.println("\n=== [ESTADO FINAL] ===");
        List<Produto> finais = dao.listarTodos();
        imprimirTabela(finais);
        System.out.println("Total de produtos no banco: " + finais.size());
    }

    private static void imprimirTabela(List<Produto> produtos) {
        System.out.printf("%-4s | %-24s | %-10s | %-7s%n", "ID", "NOME", "PRECO", "ESTOQUE");
        System.out.println("-----+--------------------------+------------+--------");
        for (Produto p : produtos) {
            System.out.printf("%-4d | %-24s | %-10.2f | %-7d%n",
                    p.getId(), p.getNome(), p.getPreco(), p.getEstoque());
        }
    }
}
