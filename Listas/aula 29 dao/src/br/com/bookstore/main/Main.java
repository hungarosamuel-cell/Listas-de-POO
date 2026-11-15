package br.com.bookstore.main;

import br.com.bookstore.dao.ConexaoFactory;
import br.com.bookstore.dao.LivroDAO;
import br.com.bookstore.dao.LivroDAOSqlite;
import br.com.bookstore.model.Livro;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        // Começa de um banco limpo para que os IDs gerados sejam sempre 1, 2 e 3
        new File(ConexaoFactory.ARQUIVO_BANCO).delete();

        // DIP: a variável é do tipo da interface, só o new conhece o SQLite
        LivroDAO dao = new LivroDAOSqlite();

        System.out.println("=== [SALVAR] ===");
        Livro l1 = new Livro("Clean Code", "Robert C. Martin", 89.90);
        Livro l2 = new Livro("Design Patterns", "Erich Gamma", 129.50);
        Livro l3 = new Livro("Refactoring", "Martin Fowler", 99.00);
        dao.salvar(l1);
        dao.salvar(l2);
        dao.salvar(l3);
        System.out.println("IDs gerados: " + l1.getId() + ", " + l2.getId() + ", " + l3.getId());

        System.out.println("\n=== [BUSCAR ID 2] ===");
        dao.buscarPorId(2L).ifPresentOrElse(
                livro -> System.out.println("Encontrado: " + livro),
                () -> System.out.println("Aviso: livro com ID 2 não encontrado."));

        System.out.println("\n=== [BUSCAR ID 999] ===");
        dao.buscarPorId(999L).ifPresentOrElse(
                livro -> System.out.println("Encontrado: " + livro),
                () -> System.out.println("Aviso: nenhum livro encontrado com ID 999."));

        System.out.println("\n=== [LISTAR TODOS] ===");
        imprimirTabela(dao.listarTodos());
    }

    private static void imprimirTabela(List<Livro> livros) {
        System.out.printf("%-4s | %-20s | %-20s | %-8s%n", "ID", "TITULO", "AUTOR", "PRECO");
        System.out.println("-----+----------------------+----------------------+---------");
        for (Livro l : livros) {
            System.out.printf("%-4d | %-20s | %-20s | %-8.2f%n",
                    l.getId(), l.getTitulo(), l.getAutor(), l.getPreco());
        }
    }
}
