package udesc.br.model;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Column(name = "nome", length = 30)
    private String nome;

    @OneToOne
    private Consulta consulta;

    @Column(name = "notas", length = 150)
    private String notas;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "cor", length = 30)
//    private Color corAgenda;

//    private List<Aplicacao> aplicacoes;

    public Agenda(Consulta consulta, LocalDate data) {
        this.consulta = consulta;
        this.data = data;
    }

    public Agenda() {}

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public String getNotas() {
        return notas;
    }

    public String getNome() {
        return nome;
    }

//    public Color getCorAgenda() {
//        return corAgenda;
//    }
}
