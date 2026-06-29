package udesc.br.controller;

import udesc.br.dao.ConsultaDAO;
import udesc.br.dao.MedicamentoDAO;
import udesc.br.dao.MovimentacaoFinanceiraDAO;
import udesc.br.dao.PacienteDAO;
import udesc.br.jpa.JPAConnector;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.MedicamentoRepositorio;
import udesc.br.repository.MovimentacaoFinanceiraRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.FramePrincipalVisao;
import udesc.br.vision.TelaInicialVisao;
import udesc.br.vision.components.TreeButton;
import udesc.br.vision.consulta.ManterAgendaVisao;
import udesc.br.vision.financeiro.MovimentacaoFinanceiraVisao;
import udesc.br.vision.medicamentos.CadastrarMedicamentoVisao;
import udesc.br.vision.medicamentos.ManterMedicamentoVisao;
import udesc.br.vision.paciente.CadastrarPacienteVisao;
import udesc.br.vision.paciente.ManterPacienteVisao;

import javax.swing.*;
import java.util.*;

public class FrameControlador {

    private final ConsultaRepositorio consultaRepositorio;
    private final MedicamentoRepositorio medicamentoRepositorio;
    private final MovimentacaoFinanceiraRepositorio movFinanceiraRepositorio;
    private final PacienteRepositorio pacienteRepositorio;

    private CadastrarPacienteControlador cadPacienteControlador;
    private ManterPacienteControlador manterPacienteControlador;
    private ManterAgendaControlador manterAgendaControlador;
    private CadastrarMedicamentoControlador cadMedicamentoControlador;
    private ManterMedicamentoControlador manterMedicamentoControlador;
    private FinanceiroControlador financeiroControlador;

    private LinkedList<Tela> telaHistorico;
    private Tela telaAtual;

    private final FramePrincipalVisao visao;

    class Tela {
        private String nome;
        private ControladorPaineis tela;

        public Tela(String nome, ControladorPaineis tela) {
            this.nome = nome;
            this.tela = tela;
        }

        public String getNome() {
            return nome;
        }

        public ControladorPaineis getTela() {
            return tela;
        }
    }

    public FrameControlador() {
        this.visao = new FramePrincipalVisao();

        this.pacienteRepositorio = new PacienteDAO();
        this.consultaRepositorio = new ConsultaDAO();
        this.medicamentoRepositorio = new MedicamentoDAO();
        this.movFinanceiraRepositorio = new MovimentacaoFinanceiraDAO();

        this.telaHistorico = new LinkedList<>();

        iniciar();
    }

    public void iniciar() {
        configurarMenus();
        adicionarAcoes();

        iniciarBD();
        abrirTelaInicio();

        visao.setVisible(true);
    }

    private void iniciarBD() {
        // pro app nao demorar quando clicar em uma tela
        try {
             JPAConnector.getEntityManager().close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void configurarMenus() {
        TreeButton treePacientes = new TreeButton(visao.getBtnPacientes());
        treePacientes.addButton(visao.getBtnCadastrarPaciente());
        treePacientes.addButton(visao.getBtnListarPacientes());
        treePacientes.setActive(true);

        TreeButton treeMedicamentos = new TreeButton(visao.getBtnMedicamentos());
        treeMedicamentos.addButton(visao.getBtnCadastrarMedicamento());
        treeMedicamentos.addButton(visao.getBtnListarMedicamentos());
        treeMedicamentos.setActive(true);

        visao.adicionarComponente(treePacientes);
        visao.adicionarComponente(treeMedicamentos);
    }

    private void adicionarAcoes() {
        visao.adicionarAcaoCadastrarPaciente(e -> abrirTelaCadastrarPaciente());
        visao.adicionarAcaoListarPacientes(e -> abrirTelaListarPacientes());

        visao.adicionarAcaoAgenda(e -> abrirTelaAgenda());

        visao.adicionarAcaoCadastrarMedicamento(e -> abrirTelaCadastrarMedicamento());
        visao.adicionarAcaoListarMedicamentos(e -> abrirTelaListarMedicamentos());

        visao.adicionarAcaoFinanceiro(e -> abrirTelaFinanceiro());

        visao.adicionarAcaoVoltar(e -> voltarTela());
        visao.adicionarAcaoAvancar(e -> avancarTela());
        visao.adicionarAcaoRecarregar(e -> recarregarTelaAtual());
    }

    public void logTela(String nomeTela, ControladorPaineis tela) {
        visao.mostrarTela(nomeTela, tela);
        telaAtual = new Tela(nomeTela, tela);
        telaHistorico.addFirst(telaAtual);
    }

    public void recarregarTelaAtual() {
        telaAtual.getTela().atualizarTela();
    }

    public void avancarTela() {
        limitHistoricoSize();

        if (telaHistorico.indexOf(telaAtual) - 1 < 0) { return;}
        int indexProx = telaHistorico.indexOf(telaAtual) - 1;

        telaAtual = telaHistorico.get(indexProx);
        visao.mostrarTela(telaAtual.getNome(), telaAtual.getTela());
    }

    public void voltarTela() {
        limitHistoricoSize();

        if (telaHistorico.indexOf(telaAtual) + 1 > telaHistorico.size() - 1) { return;}
        int indexAnt = telaHistorico.indexOf(telaAtual) + 1;

        if (telaHistorico.get(indexAnt) == null) { return;}
        telaAtual = telaHistorico.get(indexAnt);
        visao.mostrarTela(telaAtual.getNome(), telaAtual.getTela());
    }

    public void limitHistoricoSize() {
        if (telaHistorico.size() > 10) {
            telaHistorico.remove(telaHistorico.getLast());
        }
    }

    public void abrirTelaInicio() {

        TelaInicialVisao tela = new TelaInicialVisao();

        visao.adicionarTela(tela, "TELA-INICIAL");

        visao.mostrarTela("TELA-INICIAL");
    }

    public void abrirTelaCadastrarPaciente() {
        if (this.cadPacienteControlador == null) {
            CadastrarPacienteVisao tela = new CadastrarPacienteVisao();
            this.cadPacienteControlador = new CadastrarPacienteControlador(tela, this.pacienteRepositorio);

            visao.adicionarTela(tela, "CADASTRAR-PACIENTE");
        }

        logTela("CADASTRAR-PACIENTE", this.cadPacienteControlador);
    }

    public void abrirTelaListarPacientes() {
        if (this.manterPacienteControlador == null) {
            ManterPacienteVisao tela = new ManterPacienteVisao();
            this.manterPacienteControlador = new ManterPacienteControlador(tela, this.pacienteRepositorio);

            visao.adicionarTela(tela, "LISTAR-PACIENTES");
        }

        logTela("LISTAR-PACIENTES", this.manterPacienteControlador);
    }

    public void abrirTelaAgenda() {
        if (this.manterAgendaControlador == null) {
            ManterAgendaVisao tela = new ManterAgendaVisao();
            this.manterAgendaControlador = new ManterAgendaControlador(
                    tela, this.consultaRepositorio, this.pacienteRepositorio
            );

            visao.adicionarTela(tela, "LISTAR-AGENDA");
        }

        logTela("LISTAR-AGENDA", this.manterAgendaControlador);
    }

    public void abrirTelaCadastrarMedicamento() {
        if (this.cadMedicamentoControlador == null) {
            CadastrarMedicamentoVisao tela = new CadastrarMedicamentoVisao();
            this.cadMedicamentoControlador = new CadastrarMedicamentoControlador(tela, this.medicamentoRepositorio);

            visao.adicionarTela(tela, "CADASTRAR-MEDICAMENTO");
        }

        logTela("CADASTRAR-MEDICAMENTO", this.cadMedicamentoControlador);
    }

    public void abrirTelaListarMedicamentos() {
        if (this.manterMedicamentoControlador == null) {
            ManterMedicamentoVisao tela = new ManterMedicamentoVisao();
            this.manterMedicamentoControlador = new ManterMedicamentoControlador(
                    tela, this.medicamentoRepositorio, this.movFinanceiraRepositorio
            );

            visao.adicionarTela(tela, "LISTAR-MEDICAMENTOS");
        }

        logTela("LISTAR-MEDICAMENTOS", this.manterMedicamentoControlador);
    }

    public void abrirTelaFinanceiro() {
        if (this.financeiroControlador == null) {
            MovimentacaoFinanceiraVisao tela = new MovimentacaoFinanceiraVisao();
            this.financeiroControlador = new FinanceiroControlador(tela, this.movFinanceiraRepositorio);

            visao.adicionarTela(tela, "FINANCEIRO");
        }

        logTela("FINANCEIRO", this.financeiroControlador);
    }
}
