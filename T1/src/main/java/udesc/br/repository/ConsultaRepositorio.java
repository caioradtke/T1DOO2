package udesc.br.repository;

import udesc.br.controller.ConsultaRepositorioListener;
import udesc.br.controller.ManterAgendaControlador;
import udesc.br.model.Consulta;

import java.time.Month;
import java.time.Year;
import java.util.List;

public interface ConsultaRepositorio {

    void salvarConsulta(Consulta consulta);
    List<Consulta> buscarTodasConsultas();
    void apagarConsulta(Consulta consulta);
    List<Consulta> buscarConsultasData(int mes, int ano);
    Consulta buscarConsultaPorId(long id);

    void setListener(ConsultaRepositorioListener listener);
}
