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

    @Column (name = "valor")
    private double valor;

    // JPA exige construtor vazio
    protected MovimentacaoFinanceira() {
    }

    public MovimentacaoFinanceira(String descricao, LocalDate data, double valor) {
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }
    
     public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

}
