package udesc.br.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

public class Aplicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // proprio postgres vai criar o ID automaticamente
    private Long id;

    @ManyToOne
    private Medicamento medicamento;

    @Column(name = "dose")
    private double dose;

    @Column(name = "preco")
    private double preco;

    // JPA EXIGE CONSTRUTOR VAZIO
    public Aplicacao(){}
    
    public Aplicacao(Medicamento medicamento, double dose,double preco ) {
        this.medicamento = medicamento;
        this.dose = dose;
        this.preco = preco;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public double getPreco() {
        
        if (dose == 2.5) {return 250;} 
        else if (dose == 5.0) {return 380;} 
        else if (dose == 7.5) {return 500;}
        
        return 0;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
}
