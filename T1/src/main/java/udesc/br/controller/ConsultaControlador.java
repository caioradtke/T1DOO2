package udesc.br.controller;

import udesc.br.exception.ConsultaException;
import udesc.br.model.Consulta;
import udesc.br.model.Paciente;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.consulta.ConsultaVisao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsultaControlador implements Controlador {

    private ConsultaVisao visao;
    private ConsultaRepositorio consultaRepositorio;
    private PacienteRepositorio pacienteRepositorio;
    private long idConsulta;

    private boolean modoEdicao = false;

    public ConsultaControlador(ConsultaVisao consultaVisao, ConsultaRepositorio consultaRepositorio, PacienteRepositorio pacienteRepositorio, Consulta consulta) {
        this.visao = consultaVisao;
        this.consultaRepositorio = consultaRepositorio;
        this.pacienteRepositorio = pacienteRepositorio;
        this.idConsulta = consulta.getId();

        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();

        visao.popularCBPaciente(pacienteRepositorio.buscarTodosPacientes());

        visao.carregarConsulta(consultaRepositorio.buscarConsultaPorId(idConsulta));
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoEditar(e-> editarOuSalvarConsulta());
        visao.adicionarAcaoExcluir(e -> excluirConsulta());
        visao.adicionarAcaoFinalizar(e-> finalizarConsulta());
    }

    @Override
    public void atualizarTela() {
        visao.carregarConsulta(consultaRepositorio.buscarConsultaPorId(idConsulta));
    }

    public void editarOuSalvarConsulta() {
        if (modoEdicao) {
            try {
                Paciente paciente = visao.getPacienteSelecionado();
                String horario = visao.getHorario();
                LocalDate data = visao.getData();
                String observacao = visao.getObservacao();

                Consulta consulta = new Consulta(idConsulta, data, horario, observacao, paciente);
                consultaRepositorio.salvarConsulta(consulta);
                visao.mostrarMensagem("Consulta salva com sucesso!");
            } catch (ConsultaException ex) {
                System.err.println(ex.getMessage());
                visao.mostrarMensagem("Preencha todos os campos");
            } catch (DateTimeParseException dx) {
                System.err.println(dx.getMessage());
                visao.mostrarMensagem("Datas devem ser no formato AAAA-MM-DD");
            } catch (Exception e) {
                System.err.println(e.getMessage());
                visao.mostrarMensagem("Erro ao salvar no banco de dados");
            } finally {
                visao.carregarConsulta(consultaRepositorio.buscarConsultaPorId(idConsulta));
            }

        }
        visao.modoEdicao(modoEdicao);
        modoEdicao = !modoEdicao;
    }

    public void excluirConsulta() {

    }

    public void finalizarConsulta() {

    }
}
