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

    @Column (name ="IMC")
    private double imc;

    @Column(length = 20)
    private String telefone;

    public Paciente(String nome, String cpf, double peso, double altura, int idade, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
        this.idade = idade;
        this.imc = peso / (altura * altura)* 10000;
        this.idade = idade;
        this.telefone = telefone;
    }

    // JPA EXIGE CONSTRUTOR VAZIO
    public Paciente() {}

    @Override
    public String toString() {
      String imcFormatado = String.format("%.2f",imc);
      return "Paciente ID: " + id + "| Nome:" + nome + "| CPF:" + cpf;
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
        return this.imc;
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
    
    public void setImc (double peso, double altura) {
        this.imc = (peso / (altura * altura))* 10000;
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
