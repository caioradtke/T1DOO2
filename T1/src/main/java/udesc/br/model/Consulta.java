package udesc.br.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Column(name = "horario")
    private String horario;

    @Column(name = "status")
    private String status; // PENDENTE, CONCLUIDO, CANCELADO

    @ManyToOne
    private Paciente paciente;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "peso_paciente")
    private double pesoPaciente;

    @Column(name = "pressao_paciente")
    private double pressaoPaciente;

    public Consulta(LocalDate data, String horario, String observacao, Paciente paciente) {
        this.data = data;
        this.observacao = observacao;
        this.horario = horario;
        this.paciente = paciente;
        this.pesoPaciente = 0;
        this.pressaoPaciente = 0;
        this.status = "PENDENTE";
    }

    public Consulta(long id, LocalDate data, String horario, String observacao, Paciente paciente) {
        this.id = id;
        this.data = data;
        this.observacao = observacao;
        this.horario = horario;
        this.paciente = paciente;
        this.pesoPaciente = 0;
        this.pressaoPaciente = 0;
        this.status = "PENDENTE";
    }

    public Consulta() {};

    public long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public String getHorario() {
        return horario;
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

    public String getStatus() {
        return status;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setPesoPaciente(double pesoPaciente) {
        this.pesoPaciente = pesoPaciente;
    }

    public void setPressaoPaciente(double pressaoPaciente) {
        this.pressaoPaciente = pressaoPaciente;
    }
}
