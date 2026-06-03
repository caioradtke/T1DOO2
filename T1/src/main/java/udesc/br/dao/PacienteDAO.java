package udesc.br.dao;

import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;

import java.util.List;

public class PacienteDAO implements PacienteRepositorio {
    @Override
    public boolean salvarPaciente(Paciente paciente) {
        return false;
    }

    @Override
    public List<Paciente> buscarTodosPacientes() {
        return List.of();
    }

    @Override
    public boolean apagar(Paciente paciente) {
        return false;
    }
}
