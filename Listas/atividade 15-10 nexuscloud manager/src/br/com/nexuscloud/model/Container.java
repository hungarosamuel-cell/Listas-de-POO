// Pacote da camada de domínio. RA: 25057253-2
package br.com.nexuscloud.model;

// Utilitário para equals/hashCode seguros contra null. RA: 25057253-2
import java.util.Objects;

// Representa uma aplicação (container) que roda dentro de um servidor. RA: 25057253-2
public class Container {

    // ID: identidade lógica do container (RN01). RA: 25057253-2
    private String id;

    // Nome da aplicação que roda no container. RA: 25057253-2
    private String nomeApp;

    // Quantidade de RAM (em GB) que o container exige para rodar. RA: 25057253-2
    private double ramNecessaria;

    // Construtor: inicializa todos os atributos do container. RA: 25057253-2
    public Container(String id, String nomeApp, double ramNecessaria) {
        this.id = id;
        this.nomeApp = nomeApp;
        this.ramNecessaria = ramNecessaria;
    }

    // Getter do ID. RA: 25057253-2
    public String getId() {
        return id;
    }

    // Setter do ID. RA: 25057253-2
    public void setId(String id) {
        this.id = id;
    }

    // Getter do nome da aplicação. RA: 25057253-2
    public String getNomeApp() {
        return nomeApp;
    }

    // Setter do nome da aplicação. RA: 25057253-2
    public void setNomeApp(String nomeApp) {
        this.nomeApp = nomeApp;
    }

    // Getter da RAM necessária: usado pelo Stream em Servidor.calcularRamEmUso(). RA: 25057253-2
    public double getRamNecessaria() {
        return ramNecessaria;
    }

    // Setter da RAM necessária. RA: 25057253-2
    public void setRamNecessaria(double ramNecessaria) {
        this.ramNecessaria = ramNecessaria;
    }

    // Dois containers são iguais quando possuem o mesmo ID (RN01). RA: 25057253-2
    @Override
    public boolean equals(Object obj) {
        // Atalho: mesma referência em memória. RA: 25057253-2
        if (this == obj) {
            return true;
        }
        // Null ou tipo diferente não pode ser igual. RA: 25057253-2
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Cast seguro, pois a classe já foi verificada acima. RA: 25057253-2
        Container outro = (Container) obj;
        // Compara somente pelo ID. RA: 25057253-2
        return Objects.equals(id, outro.id);
    }

    // hashCode baseado no mesmo campo usado no equals (contrato Java). RA: 25057253-2
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Texto amigável do container. RA: 25057253-2
    @Override
    public String toString() {
        return id + " - " + nomeApp + " (" + ramNecessaria + " GB)";
    }
}
