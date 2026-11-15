// Pacote da camada de domínio. RA: 25057253-2
package br.com.nexuscloud.model;

// HashMap e HashSet: implementações baseadas em tabela hash, de alta performance (RN02). RA: 25057253-2
import java.util.HashMap;
import java.util.HashSet;
// Interfaces usadas como tipo das variáveis. RA: 25057253-2
import java.util.Map;
import java.util.Set;

// Banco de dados principal: relaciona cada Cliente aos seus Servidores. RA: 25057253-2
public class CloudManager {

    // Chave = Cliente; valor = conjunto de Servidores (Set: sem duplicados e sem ordem). Usa HashMap (RN02). RA: 25057253-2
    private Map<Cliente, Set<Servidor>> bancoDeDados = new HashMap<>();

    // Construtor padrão: o mapa já nasce inicializado na declaração. RA: 25057253-2
    public CloudManager() {
    }

    // Registra um cliente com um conjunto vazio de servidores. RA: 25057253-2
    public void registrarCliente(Cliente c) {
        // putIfAbsent evita sobrescrever os servidores de um cliente que já exista (mesmo CPF). RA: 25057253-2
        bancoDeDados.putIfAbsent(c, new HashSet<>());
    }

    // Associa um servidor a um cliente. RA: 25057253-2
    public void provisionarServidor(Cliente c, Servidor s) {
        // computeIfAbsent garante que exista um Set para o cliente, evitando NullPointerException. RA: 25057253-2
        // O Set descarta automaticamente servidores duplicados (mesmo IP, via equals/hashCode). RA: 25057253-2
        bancoDeDados.computeIfAbsent(c, chave -> new HashSet<>()).add(s);
    }

    // Soma o preço por hora apenas dos servidores LIGADOS do cliente, usando Streams (RN04). RA: 25057253-2
    public double calcularFaturaAtiva(Cliente c) {
        // getOrDefault devolve um Set vazio se o cliente não existir, evitando NullPointerException. RA: 25057253-2
        return bancoDeDados.getOrDefault(c, new HashSet<>())
                .stream()
                // filter deixa passar APENAS servidores ligados. RA: 25057253-2
                .filter(Servidor::isStatusLigado)
                // mapToDouble extrai o preço por hora de cada servidor restante. RA: 25057253-2
                .mapToDouble(Servidor::getPrecoPorHora)
                // sum() produz o valor final da fatura. RA: 25057253-2
                .sum();
    }
}
