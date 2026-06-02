package main.java.udesc.br.model;

public class Paciente {
    private String nomeCompleto;
    private String cpf;
    private double peso;
    private double altura;
    private double imc;
    private String telefone;

    public Paciente(String nomeCompleto, String cpf, double peso, double altura, String telefone) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
        this.imc = peso / altura;
        this.telefone = telefone;
    }
}
