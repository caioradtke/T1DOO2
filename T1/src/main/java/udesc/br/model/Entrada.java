package main.java.udesc.br.model;

import java.time.LocalDate;

public class Entrada extends MovimentaFinanceira {
    public Entrada(String id, String descricao, double valor, LocalDate data) {
        super(id, descricao, valor, data);
    }
}
