package udesc.br.repository;

import udesc.br.model.Agenda;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface AgendaRepositorio {
    void salvarRepositorio(Agenda agenda);
    List<Agenda> buscarTodasAgendas();
    void apagarAgenda(Agenda agenda);
    List<Agenda> buscarAgendasData(int mes, int ano);
}
