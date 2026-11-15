// Pacote das exceções de negócio. RA: 25057253-2
package br.com.nexuscloud.exception;

// Estende Exception, logo é CHECKED: o compilador obriga tratar (try-catch) ou declarar (throws). RA: 25057253-2
public class CapacidadeExcedidaException extends Exception {

    // Recebe a mensagem de erro e a repassa ao construtor da classe pai. RA: 25057253-2
    public CapacidadeExcedidaException(String msg) {
        super(msg);
    }
}
