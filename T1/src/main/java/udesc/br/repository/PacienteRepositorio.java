package udesc.br.repository;

import udesc.br.model.Paciente;
import java.util.List;

public interface PacienteRepositorio {

    void salvarPaciente(Paciente paciente);
    List<Paciente> buscarTodosPacientes();
    void apagar(Paciente paciente);

}