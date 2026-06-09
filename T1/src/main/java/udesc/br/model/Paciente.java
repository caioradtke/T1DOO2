package udesc.br.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // proprio postgres vai criar o ID automaticamente
    private Long id;

    @Column(nullable = false, length = 150) // not null e limite de 150 caracteres
    private String nome;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(name = "peso")
    private double peso;

    @Column(name = "altura")
    private double altura;

    @Transient // JPA nao vai criar esse campo (é um valor que é calculado de outros dois valores ent melhor so ficar no java)
    private double imc;

    @Column(length = 20)
    private String telefone;

    public Paciente(String nome, String cpf, double peso, double altura, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
        this.imc = peso / altura;
        this.telefone = telefone;
    }

    // JPA EXIGE CONSTRUTOR VAZIO
    public Paciente() {}

    @Override
    public String toString() {
      return "Paciente nome=" + nome + ", cpf=" + cpf + ", peso=" + peso + ", altura= " +altura+ ", imc=" + imc + ", telefone=" + telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public double getImc() {
        return imc;
    }

    public String getTelefone() {
        return telefone;
    }


}
