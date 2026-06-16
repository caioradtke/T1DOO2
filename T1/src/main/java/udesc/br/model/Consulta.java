package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @ManyToOne
    private Paciente paciente;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "peso_paciente")
    private double pesoPaciente;

    @Column(name = "pressao_paciente")
    private double pressaoPaciente;

    public Consulta(String id, LocalDate data, String observacao, Paciente paciente, double pesoPaciente, double pressaoPaciente) {
        this.id = id;
        this.data = data;
        this.observacao = observacao;
        this.paciente = paciente;
        this.pesoPaciente = pesoPaciente;
        this.pressaoPaciente = pressaoPaciente;
    }

    public Consulta() {};

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getObservacao() {
        return observacao;
    }

    public double getPesoPaciente() {
        return pesoPaciente;
    }

    public double getPressaoPaciente() {
        return pressaoPaciente;
    }
}
