package udesc.br.controller;

import udesc.br.controller.interfaces.ControladorPaineis;
import udesc.br.dao.MovimentacaoFinanceiraDAO;
import udesc.br.dao.PacienteDAO;
import udesc.br.exception.ConsultaException;
import udesc.br.model.Consulta;
import udesc.br.model.Paciente;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.MovimentacaoFinanceiraRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.consulta.ConsultaVisao;
import udesc.br.vision.consulta.FinalizarConsultaVisao;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ConsultaControlador implements ControladorPaineis {

    private ConsultaVisao visao;
    private ConsultaRepositorio consultaRepositorio;
    private PacienteRepositorio pacienteRepositorio;
    private MovimentacaoFinanceiraRepositorio movimentacaoFinanceiraRepositorio;
    private Consulta consulta;
    private FinalizarConsultaVisao telaFinalizarConsulta;

    private boolean modoEdicao = false;

    public ConsultaControlador(ConsultaVisao consultaVisao,
                               ConsultaRepositorio consultaRepositorio,
                               Consulta consulta) {
        this.visao = consultaVisao;
        this.consultaRepositorio = consultaRepositorio;
        this.pacienteRepositorio = new PacienteDAO();
        this.movimentacaoFinanceiraRepositorio = new MovimentacaoFinanceiraDAO();
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
        visao.adicionarAcaoAbrirTelaFinalizar(e-> abrirTelaFinalizar());
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
                String status = visao.getStatus();

                Consulta consultaTemp = new Consulta(consulta.getId(), data, horario, observacao, paciente);
                consultaTemp.setStatus(status);

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

    public void abrirTelaFinalizar() {
        telaFinalizarConsulta = new FinalizarConsultaVisao();
        telaFinalizarConsulta.setLocationRelativeTo(null);
        telaFinalizarConsulta.setVisible(true);
        telaFinalizarConsulta.adicionarAcaoFinalizar(e -> finalizarConsulta());
    }

    public void finalizarConsulta() {
        try {
            double peso = telaFinalizarConsulta.getPesoPaciente();
            double pressao = telaFinalizarConsulta.getPressao();

            Consulta consultaTemp = consulta;
            consultaTemp.setPesoPaciente(peso);
            consultaTemp.setPressaoPaciente(pressao);
            consultaTemp.setStatus("CONCLUIDA");

            consultaRepositorio.salvarConsulta(consultaTemp);
            consulta = consultaTemp;

            telaFinalizarConsulta.mostrarMensagem("Consulta finalizada com sucesso!");
            telaFinalizarConsulta.dispose();
        } catch (ConsultaException ce) {
            System.err.println(ce.getMessage());
            visao.mostrarMensagem(ce.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            visao.mostrarMensagem("Erro ao finalizar consulta");
        } finally {
            visao.carregarConsulta(consulta);
        }
    }
}
