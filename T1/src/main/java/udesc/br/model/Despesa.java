package udesc.br.model;

import java.time.LocalDate;

public class Despesa extends MovimentaFinanceira{
    public Despesa(String id, String descricao, double valor, LocalDate data) {
        super(id, descricao, valor, data);
    }
}
