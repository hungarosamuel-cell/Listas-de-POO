package br.com.starlog.exception;

// RN05: herda direto de Exception, logo é checked
public class CapacidadeExcedidaException extends Exception {

    public CapacidadeExcedidaException(String mensagem) {
        super(mensagem);
    }
}
