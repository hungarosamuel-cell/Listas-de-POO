// Declara o pacote onde a classe Cliente vive (camada de domínio). RA: 25057253-2
package br.com.nexuscloud.model;

// Importa Objects, utilitário que compara e gera hash de forma segura contra null. RA: 25057253-2
import java.util.Objects;

// Classe de domínio (POJO) que representa o cliente da NexusCloud. RA: 25057253-2
public class Cliente {

    // CPF: é a identidade lógica do cliente. Privado para respeitar o encapsulamento. RA: 25057253-2
    private String cpf;

    // Nome do cliente (razão social ou nome completo). Privado pelo mesmo motivo. RA: 25057253-2
    private String nome;

    // Construtor: recebe CPF e nome e guarda nos atributos do objeto. RA: 25057253-2
    public Cliente(String cpf, String nome) {
        // "this" diferencia o atributo da classe do parâmetro de mesmo nome. RA: 25057253-2
        this.cpf = cpf;
        this.nome = nome;
    }

    // Getter do CPF: permite ler o valor sem expor o atributo diretamente. RA: 25057253-2
    public String getCpf() {
        return cpf;
    }

    // Setter do CPF: permite corrigir o CPF de forma controlada. RA: 25057253-2
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Getter do nome. RA: 25057253-2
    public String getNome() {
        return nome;
    }

    // Setter do nome. RA: 25057253-2
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Dois clientes são iguais quando possuem o mesmo CPF (RN01), e não o mesmo endereço de memória. RA: 25057253-2
    @Override
    public boolean equals(Object obj) {
        // Mesmo endereço de memória: certamente é o mesmo cliente, retorna logo. RA: 25057253-2
        if (this == obj) {
            return true;
        }
        // Null ou classe diferente nunca é igual a um Cliente. RA: 25057253-2
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Converte o Object para Cliente para poder acessar o CPF dele. RA: 25057253-2
        Cliente outro = (Cliente) obj;
        // A comparação de negócio acontece apenas pelo CPF. RA: 25057253-2
        return Objects.equals(cpf, outro.cpf);
    }

    // hashCode precisa ser coerente com equals: mesmo CPF gera o mesmo hash. Essencial para HashMap/HashSet. RA: 25057253-2
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    // Representação textual para facilitar impressão e depuração. RA: 25057253-2
    @Override
    public String toString() {
        return nome + " (" + cpf + ")";
    }
}
