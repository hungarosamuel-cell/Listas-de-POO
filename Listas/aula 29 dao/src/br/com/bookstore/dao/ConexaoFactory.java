package br.com.bookstore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    public static final String ARQUIVO_BANCO = "bookstore.db";
    private static final String URL = "jdbc:sqlite:" + ARQUIVO_BANCO;

    private ConexaoFactory() {
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
