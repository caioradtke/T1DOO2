package udesc.br.repository;

import java.util.List;
import udesc.br.model.Medicamento;

public interface MedicamentoRepositorio {
    
    void salvarPaciente(Medicamento medicamento);
    List<Medicamento> buscarTodosPacientes();
    void apagar(Medicamento medicamento);
}
