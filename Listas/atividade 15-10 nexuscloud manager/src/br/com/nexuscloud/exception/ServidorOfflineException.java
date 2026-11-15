// Pacote das exceções de negócio. RA: 25057253-2
package br.com.nexuscloud.exception;

// Estende RuntimeException, logo é UNCHECKED: o compilador não obriga try-catch nem throws. RA: 25057253-2
public class ServidorOfflineException extends RuntimeException {

    // Recebe a mensagem de erro e a repassa ao construtor da classe pai. RA: 25057253-2
    public ServidorOfflineException(String msg) {
        super(msg);
    }
}
