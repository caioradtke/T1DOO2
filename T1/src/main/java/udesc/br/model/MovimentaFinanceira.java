package main.java.udesc.br.model;

import java.time.LocalDate;

public class MovimentaFinanceira {
    private String id;
    private String descricao;
    private double valor;
    private LocalDate data;

    public MovimentaFinanceira(String id, String descricao, double valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
}
