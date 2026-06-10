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

    @Column(name = "idade")
    private int idade;

    @Transient // JPA nao vai criar esse campo (é um valor que é calculado de outros dois valores ent melhor so ficar no java)
    private double imc;

    @Column(length = 20)
    private String telefone;

    public Paciente(String nome, String cpf, double peso, double altura, int idade, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
        this.idade = idade;
        this.imc = peso / altura;
        this.idade = idade;
        this.telefone = telefone;
    }

    // JPA EXIGE CONSTRUTOR VAZIO
    public Paciente() {}

    @Override
    public String toString() {
      return "Paciente nome=" + nome + ", cpf=" + cpf + ", peso=" + peso + ", altura= " +altura+ ", imc=" + imc + ", telefone=" + telefone+ ", idade=" + idade;
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
    
    public int getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean setIdade(int idade) {
        if (idade > 0) {
            this.idade = idade;
            return true;
        }

        return false;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public boolean setId(Long id) {
        if (id > 0) {
            this.id = id;
            return true;
        }

        return false;
    }
}
