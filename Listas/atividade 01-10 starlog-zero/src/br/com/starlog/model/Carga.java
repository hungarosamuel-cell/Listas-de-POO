package br.com.starlog.model;

import java.util.Objects;

public class Carga {

    // RN01: o código de rastreio é a identidade da carga, por isso final e sem setter
    private final String codigoRastreio;
    private String categoria;
    private double pesoKg;
    private double valorSeguro;

    public Carga(String codigoRastreio, String categoria, double pesoKg, double valorSeguro) {
        // RN02: valida antes de qualquer atribuição (fail-fast)
        if (codigoRastreio == null || codigoRastreio.trim().isEmpty()) {
            throw new IllegalArgumentException("Codigo de rastreio da carga nao pode ser nulo ou vazio.");
        }
        if (pesoKg <= 0) {
            throw new IllegalArgumentException("Peso da carga deve ser maior que zero.");
        }
        this.codigoRastreio = codigoRastreio;
        this.categoria = categoria;
        this.pesoKg = pesoKg;
        this.valorSeguro = valorSeguro;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    // RN03: equals e hashCode usam somente o código de rastreio
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Carga outra = (Carga) obj;
        return Objects.equals(codigoRastreio, outra.codigoRastreio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoRastreio);
    }

    // RN04
    @Override
    public String toString() {
        return "Carga [rastreio=" + codigoRastreio + ", categoria=" + categoria
                + ", peso=" + pesoKg + "kg, seguro=R$ " + valorSeguro + "]";
    }
}
