package udesc.br.controller;

import udesc.br.exception.ConsultaException;
import udesc.br.model.Consulta;
import udesc.br.model.Paciente;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.consulta.CriarConsultaVisao;
import udesc.br.vision.consulta.ManterAgendaVisao;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;

public class CriarConsultaControlador implements Controlador {

    private CriarConsultaVisao visao;
    private PacienteRepositorio pacienteRepositorio;
    private ConsultaRepositorio consultaRepositorio;

    public CriarConsultaControlador(CriarConsultaVisao visao, PacienteRepositorio pacienteRepositorio, ConsultaRepositorio consultaRepositorio) {
        this.visao = visao;
        this.pacienteRepositorio = pacienteRepositorio;
        this.consultaRepositorio = consultaRepositorio;

        initTela();
    }

    @Override
    public void initTela() {
        popularCBPacientes(pacienteRepositorio.buscarTodosPacientes());

        visao.setVisible(true);
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoAgendarConsulta(e -> salvarConsulta());
    }

    public void popularCBPacientes(Map<Long,Paciente> pacientes){
        JComboBox<Paciente> cbPacientePop = visao.getCbPaciente();
        cbPacientePop.removeAllItems();
        for (Paciente p : pacientes.values()) {
            cbPacientePop.addItem(p);
        }
        visao.setCbPaciente(cbPacientePop);
    }

    public void salvarConsulta() {
        try {
            LocalDate dataConsulta = visao.getCampoData();
            String observacao = visao.getObservacao();
            Paciente paciente = visao.getPacienteSelecionado();

            Consulta consultaModelo = new Consulta(dataConsulta, observacao, paciente);
            consultaRepositorio.salvarConsulta(consultaModelo);

            visao.mostrarMensagem("Consulta agendada com sucesso");
        } catch (ConsultaException c) {
            System.err.println("Erro ao processar: " + c.getMessage());
            visao.mostrarMensagem(c.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro ao salvar: " + ex.getMessage());
            visao.mostrarMensagem("Erro ao salvar no banco de dados");
        } finally {

        }
    }

    @Override
    public void atualizarTela() {
        popularCBPacientes(pacienteRepositorio.buscarTodosPacientes());
    }
}
