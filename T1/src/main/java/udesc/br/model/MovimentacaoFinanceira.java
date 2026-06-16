package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movimentacao_financeira")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MovimentacaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    // JPA exige construtor vazio
    protected MovimentacaoFinanceira() {
    }

    public MovimentacaoFinanceira(String descricao, LocalDate data) {
        this.descricao = descricao;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public abstract double calcularValor();

    public String getDescricao() {
        return descricao;
    }

}
