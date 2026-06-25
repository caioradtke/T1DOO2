package udesc.br.repository;

import java.util.List;
import java.util.Set;

import udesc.br.model.Medicamento;

public interface MedicamentoRepositorio {
    
    void salvarMedicamento(Medicamento medicamento);
    Set<Medicamento> buscarTodosMedicamentos();
    void apagar(Medicamento medicamento);
}
