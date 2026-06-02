package main.java.udesc.br.model;

import java.time.LocalDate;

public class Consulta {
    private String id;
    private LocalDate data;
    private Paciente paciente;
    private String observacao;
    private double pesoPaciente;
    private double pressaoPaciente;

    public Consulta(String id, LocalDate data, String observacao, Paciente paciente, double pesoPaciente, double pressaoPaciente) {
        this.id = id;
        this.data = data;
        this.observacao = observacao;
        this.paciente = paciente;
        this.pesoPaciente = pesoPaciente;
        this.pressaoPaciente = pressaoPaciente;
    }
}
