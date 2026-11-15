// Pacote da classe executável. RA: 25057253-2
package br.com.nexuscloud.main;

// Importa a exceção checked, necessária nos blocos try-catch. RA: 25057253-2
import br.com.nexuscloud.exception.CapacidadeExcedidaException;
// Importa as classes de domínio usadas no roteiro de testes. RA: 25057253-2
import br.com.nexuscloud.model.Cliente;
import br.com.nexuscloud.model.CloudManager;
import br.com.nexuscloud.model.Container;
import br.com.nexuscloud.model.Servidor;

// Campo de testes: executa o roteiro da atividade passo a passo. RA: 25057253-2
public class MainCloud {

    // Ponto de entrada do programa. RA: 25057253-2
    public static void main(String[] args) {

        // Passo 1: instancia o gerenciador (banco de dados principal). RA: 25057253-2
        CloudManager manager = new CloudManager();

        // Passo 2: cria um cliente com CPF e nome. RA: 25057253-2
        Cliente cliente = new Cliente("111.222.333-44", "Wayne Enterprises");

        // Passo 3: cria um servidor (IP, RAM total de 16 GB, R$ 5,50 por hora). Nasce desligado. RA: 25057253-2
        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);

        // Passo 4: registra o cliente e vincula o servidor a ele. RA: 25057253-2
        manager.registrarCliente(cliente);
        manager.provisionarServidor(cliente, servidor);

        // Passo 5: cria dois containers diferentes (10 GB e 8 GB). RA: 25057253-2
        Container container1 = new Container("C-01", "API Gateway", 10.0);
        Container container2 = new Container("C-02", "Banco de Dados", 8.0);

        // Passo 6: teste da falha UNCHECKED. Foi executada e a ServidorOfflineException apareceu no console. RA: 25057253-2
        // Linha comentada (Passo 7) para o programa poder prosseguir. Como é unchecked, não precisa de try-catch. RA: 25057253-2
        // servidor.alocarContainer(container1);

        // Passo 7: liga o servidor, liberando as alocações. RA: 25057253-2
        servidor.ligar();

        // Passo 8: agora o servidor está ligado e tem 16 GB livres, então a alocação funciona. RA: 25057253-2
        // O try-catch é obrigatório porque a CapacidadeExcedidaException é checked. RA: 25057253-2
        try {
            servidor.alocarContainer(container1);
            System.out.println("Container " + container1.getId() + " alocado com sucesso.");
        } catch (CapacidadeExcedidaException e) {
            System.out.println(e.getMessage());
        }

        // Passo 9: teste da falha CHECKED. 10.0 + 8.0 = 18.0, que ultrapassa os 16.0 do servidor. RA: 25057253-2
        try {
            servidor.alocarContainer(container2);
            System.out.println("Container " + container2.getId() + " alocado com sucesso.");
        } catch (CapacidadeExcedidaException e) {
            // Captura o erro e imprime a mensagem de forma elegante, sem derrubar o programa. RA: 25057253-2
            System.out.println(e.getMessage());
        }

        // Passo 10: imprime a fatura ativa do cliente. Esperado: 5.50, pois só há um servidor ligado. RA: 25057253-2
        System.out.println("Fatura ativa de " + cliente.getNome() + ": R$ " + manager.calcularFaturaAtiva(cliente));
    }
}
