// Pacote da camada de domínio. RA: 25057253-2
package br.com.nexuscloud.model;

// Exceção checked lançada quando a RAM não comporta o novo container. RA: 25057253-2
import br.com.nexuscloud.exception.CapacidadeExcedidaException;
// Exceção unchecked lançada quando o servidor está desligado. RA: 25057253-2
import br.com.nexuscloud.exception.ServidorOfflineException;
// ArrayList: implementação de lista que mantém a ordem cronológica de inserção. RA: 25057253-2
import java.util.ArrayList;
// List: interface usada como tipo do atributo (programar para a interface). RA: 25057253-2
import java.util.List;
// Utilitário para equals/hashCode. RA: 25057253-2
import java.util.Objects;

// Servidor virtual da NexusCloud, que hospeda containers. RA: 25057253-2
public class Servidor {

    // IP: identidade lógica do servidor (RN01). RA: 25057253-2
    private String ip;

    // Valor cobrado por hora de uso do servidor. RA: 25057253-2
    private double precoPorHora;

    // Capacidade total de RAM (GB). RA: 25057253-2
    private double ramTotal;

    // Indica se o servidor está ligado. O valor padrão do boolean é false: o servidor nasce desligado. RA: 25057253-2
    private boolean statusLigado;

    // Lista de containers inicializada na declaração para evitar NullPointerException (RN02). RA: 25057253-2
    private List<Container> containers = new ArrayList<>();

    // Construtor: não recebe status nem containers, pois nascem desligado e vazio. RA: 25057253-2
    public Servidor(String ip, double ramTotal, double precoPorHora) {
        this.ip = ip;
        this.ramTotal = ramTotal;
        this.precoPorHora = precoPorHora;
    }

    // Liga o servidor, permitindo novas alocações. RA: 25057253-2
    public void ligar() {
        this.statusLigado = true;
    }

    // Desliga o servidor. RA: 25057253-2
    public void desligar() {
        this.statusLigado = false;
    }

    // Aloca um container aplicando Fail-Fast: valida tudo ANTES de alterar o estado do servidor (RN03). RA: 25057253-2
    public void alocarContainer(Container c) throws CapacidadeExcedidaException {
        // Regra 1 (unchecked): servidor desligado não aceita alocação. RA: 25057253-2
        if (!statusLigado) {
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }
        // Regra 2 (checked): RAM já em uso + RAM do novo container não pode passar do total. RA: 25057253-2
        if (calcularRamEmUso() + c.getRamNecessaria() > ramTotal) {
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + ip + ".");
        }
        // Só chega aqui se passou nas duas validações; então é seguro adicionar. RA: 25057253-2
        containers.add(c);
    }

    // Soma a RAM de todos os containers alocados usando Streams, sem laço for (RN04). RA: 25057253-2
    public double calcularRamEmUso() {
        // stream() cria o fluxo; mapToDouble extrai a RAM de cada container; sum() soma tudo. RA: 25057253-2
        return containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    // Getter do IP. RA: 25057253-2
    public String getIp() {
        return ip;
    }

    // Setter do IP. RA: 25057253-2
    public void setIp(String ip) {
        this.ip = ip;
    }

    // Getter do preço por hora: usado na fatura do CloudManager. RA: 25057253-2
    public double getPrecoPorHora() {
        return precoPorHora;
    }

    // Setter do preço por hora. RA: 25057253-2
    public void setPrecoPorHora(double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    // Getter da RAM total. RA: 25057253-2
    public double getRamTotal() {
        return ramTotal;
    }

    // Setter da RAM total. RA: 25057253-2
    public void setRamTotal(double ramTotal) {
        this.ramTotal = ramTotal;
    }

    // Para boolean, a convenção Java é "is" no lugar de "get". Usado no filter() da fatura. RA: 25057253-2
    public boolean isStatusLigado() {
        return statusLigado;
    }

    // Retorna os containers alocados. RA: 25057253-2
    public List<Container> getContainers() {
        return containers;
    }

    // Dois servidores são iguais quando possuem o mesmo IP (RN01). RA: 25057253-2
    @Override
    public boolean equals(Object obj) {
        // Mesma referência em memória. RA: 25057253-2
        if (this == obj) {
            return true;
        }
        // Null ou classe diferente não é igual. RA: 25057253-2
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Cast seguro após a verificação de classe. RA: 25057253-2
        Servidor outro = (Servidor) obj;
        // Compara apenas pelo IP. RA: 25057253-2
        return Objects.equals(ip, outro.ip);
    }

    // hashCode coerente com equals: baseado no IP. RA: 25057253-2
    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    // Texto amigável do servidor. RA: 25057253-2
    @Override
    public String toString() {
        return "Servidor " + ip + " (" + (statusLigado ? "LIGADO" : "DESLIGADO") + ")";
    }
}
