package udesc.br.repository;

import udesc.br.model.Paciente;
import java.util.List;
import java.util.Map;

public interface PacienteRepositorio {

    void salvarPaciente(Paciente paciente);
    Map<Long,Paciente> buscarTodosPacientes();
    void apagar(Paciente paciente);

}