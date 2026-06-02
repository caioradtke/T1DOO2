package main.java.udesc.br.model;

import java.time.LocalDate;
import java.util.List;

public class Agenda {
    private LocalDate data;
    private List<Consulta> consultas;
    private List<Aplicacao> aplicacoes;

    public Agenda(List<Consulta> consultas, LocalDate data, List<Aplicacao> aplicacoes) {
        this.consultas = consultas;
        this.data = data;
        this.aplicacoes = aplicacoes;
    }
}
