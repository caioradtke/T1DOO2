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

    @Column(name = "nome", length = 30)
    private String nome;

    @OneToOne
    private Consulta consulta;

    @Column(name = "notas", length = 150)
    private String notas;

    public Agenda(Consulta consulta) {
        this.consulta = consulta;
    }

    public Agenda() {}

    public String getId() {
        return id;
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

    public LocalDate getData() {
        return consulta.getData();
    }

}
