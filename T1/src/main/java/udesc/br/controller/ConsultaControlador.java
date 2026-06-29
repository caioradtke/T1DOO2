package udesc.br.controller;

import udesc.br.exception.ConsultaException;
import udesc.br.model.Consulta;
import udesc.br.model.Paciente;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.consulta.ConsultaVisao;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConsultaControlador implements ControladorPaineis {

    private ConsultaVisao visao;
    private ConsultaRepositorio consultaRepositorio;
    private PacienteRepositorio pacienteRepositorio;
    private Consulta consulta;

    private boolean modoEdicao = false;

    public ConsultaControlador(ConsultaVisao consultaVisao, ConsultaRepositorio consultaRepositorio, PacienteRepositorio pacienteRepositorio, Consulta consulta) {
        this.visao = consultaVisao;
        this.consultaRepositorio = consultaRepositorio;
        this.pacienteRepositorio = pacienteRepositorio;
        this.consulta = consulta;

        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();

        visao.popularCBPaciente(pacienteRepositorio.buscarTodosPacientes());

        visao.carregarConsulta(consulta);
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoEditar(e-> editarOuSalvarConsulta());
        visao.adicionarAcaoExcluir(e -> excluirConsulta());
        visao.adicionarAcaoFinalizar(e-> finalizarConsulta());
    }

    @Override
    public void atualizarTela() {
        visao.carregarConsulta(consulta);
    }

    public void editarOuSalvarConsulta() {
        if (modoEdicao) {
            try {
                Paciente paciente = visao.getPacienteSelecionado();
                String horario = visao.getHorario();
                LocalDate data = visao.getData();
                String observacao = visao.getObservacao();

                Consulta consultaTemp = new Consulta(consulta.getId(), data, horario, observacao, paciente);

                consultaRepositorio.salvarConsulta(consultaTemp);
                consulta = consultaTemp;

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
                visao.carregarConsulta(consulta);
            }

        }
        visao.modoEdicao(modoEdicao);
        modoEdicao = !modoEdicao;
    }

    public void excluirConsulta() {
        try {
            int opcao = JOptionPane.showConfirmDialog( visao,
                    "Deseja realmente excluir a consulta?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }
            consultaRepositorio.apagarConsulta(consulta);
            visao.dispose();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            visao.mostrarMensagem("Erro ao excluir consulta");
        }
    }

    public void finalizarConsulta() {

    }
}
