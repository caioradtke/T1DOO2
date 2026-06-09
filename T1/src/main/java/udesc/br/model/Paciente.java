package udesc.br.model;

public class Paciente {
    private String nome;
    private String cpf;
    private double peso;
    private double altura;
    private double imc;
    private String telefone;

    public Paciente(String nome, String cpf, double peso, double altura, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.peso = peso;
        this.altura = altura;
        this.imc = peso / altura;
        this.telefone = telefone;
    }
    
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
