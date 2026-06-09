package udesc.br.model;

import java.time.LocalDate;

public abstract class MovimentacaoFinanceira {
    private int id;
    private static int geraID = 0;
    private Paciente paciente;
    private String descricao;
    private LocalDate data;

    public MovimentacaoFinanceira(String descricao, LocalDate data) {
        this.id = geraID++;
        this.descricao = descricao;
        this.data = data;
    }

    public abstract double calcularValor();
    public int getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }

}
