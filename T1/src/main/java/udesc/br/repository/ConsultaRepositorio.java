package udesc.br.repository;

import udesc.br.controller.interfaces.ConsultaRepositorioListener;
import udesc.br.model.Consulta;

import java.util.List;

public interface ConsultaRepositorio {

    void salvarConsulta(Consulta consulta);
    List<Consulta> buscarTodasConsultas();
    void apagarConsulta(Consulta consulta);
    List<Consulta> buscarConsultasData(int mes, int ano);
    Consulta buscarConsultaPorId(long id);

    void setListener(ConsultaRepositorioListener listener);
}
