package udesc.br.model;

import java.time.LocalDate;

public abstract class MovimentaFinanceira {
    private String id;
    private String descricao;
    private LocalDate data;

    public MovimentaFinanceira(String id, String descricao, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
    }

    public abstract double calcularValor();

}
