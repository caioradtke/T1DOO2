package udesc.br.repository;

import udesc.br.model.Paciente;
import java.util.List;

public interface PacienteRepositorio {

    boolean salvarPaciente(Paciente produto);
    List<Paciente> buscarTodosPacientes();
    boolean apagar(Paciente paciente);

}