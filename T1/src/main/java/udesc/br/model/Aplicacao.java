package udesc.br.model;

import java.time.LocalDate;

public class Aplicacao {
    private Medicamento medicamento;
    private double dose;
    private LocalDate data;

    public Aplicacao(Medicamento medicamento, double dose, LocalDate data) {
        this.medicamento = medicamento;
        this.dose = dose;
        this.data = data;
    }
}
