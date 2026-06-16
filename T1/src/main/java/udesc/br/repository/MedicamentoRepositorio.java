package udesc.br.repository;

import java.util.List;
import udesc.br.model.Medicamento;

public interface MedicamentoRepositorio {
    
    void salvarMedicamento(Medicamento medicamento);
    List<Medicamento> buscarTodosMedicamentos();
    void apagar(Medicamento medicamento);
}
