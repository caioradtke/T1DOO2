package udesc.br.controller;

import jakarta.persistence.EntityManager;
import udesc.br.controller.interfaces.ControladorPaineis;
import udesc.br.dao.ConsultaDAO;
import udesc.br.dao.MedicamentoDAO;
import udesc.br.dao.MovimentacaoFinanceiraDAO;
import udesc.br.dao.PacienteDAO;
import udesc.br.jpa.JPAConnector;
import udesc.br.model.*;
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
import java.time.LocalDate;
import java.util.*;

public class FrameControlador implements ControladorPaineis {

    private ConsultaRepositorio consultaRepositorio;
    private MedicamentoRepositorio medicamentoRepositorio;
    private MovimentacaoFinanceiraRepositorio movFinanceiraRepositorio;
    private PacienteRepositorio pacienteRepositorio;

    private CadastrarPacienteControlador cadPacienteControlador;
    private ManterPacienteControlador manterPacienteControlador;
    private ManterAgendaControlador manterAgendaControlador;
    private CadastrarMedicamentoControlador cadMedicamentoControlador;
    private ManterMedicamentoControlador manterMedicamentoControlador;
    private FinanceiroControlador financeiroControlador;

    private LinkedList<Tela> telaHistorico;
    private Tela telaAtual;

    private FramePrincipalVisao visao;
    private TelaInicialVisao telaInicial;

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
        initTela(); // abre tela inicial

        visao.setLocationRelativeTo(null);
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

    @Override
    public void adicionarAcoes() {
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

    @Override
    public void initTela() {

        telaInicial = new TelaInicialVisao();
        telaInicial.adicionarAcaoPopular( e-> popularClasses());
        telaInicial.adicionarAcaoLimpar( e-> limparBD());

        visao.adicionarTela(telaInicial, "TELA-INICIAL");

        logTela("TELA-INICIAL", this);
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

    @Override
    public void atualizarTela() {}

    public void limparBD() {
        int opcao = telaInicial.mostrarMensagemConfirmacao("Tem certeza que deseja deletar o Banco de Dados?");

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        EntityManager em = JPAConnector.getEntityManager();

        em.getTransaction().begin();

        em.createQuery("DELETE FROM Consulta").executeUpdate();
        em.createQuery("DELETE FROM MovimentacaoFinanceira").executeUpdate();
        em.createQuery("DELETE FROM Medicamento").executeUpdate();
        em.createQuery("DELETE FROM Paciente").executeUpdate();

        em.getTransaction().commit();
        em.close();
    }

    public void popularClasses() {
        abrirTelaAgenda();
        abrirTelaFinanceiro();
        abrirTelaCadastrarMedicamento();
        abrirTelaCadastrarPaciente();
        abrirTelaListarMedicamentos();
        abrirTelaListarPacientes();

        visao.mostrarTela("TELA-INICIAL");

        // Pacientes
        Paciente p1 = new Paciente("João Silva", "11111111111", 82.5, 180, 35, "47 99999-1111");
        Paciente p2 = new Paciente("Maria Oliveira", "22222222222", 64.2, 165, 29, "47 99999-2222");
        Paciente p3 = new Paciente("Carlos Souza", "33333333333", 95.8, 178, 42, "47 99999-3333");
        Paciente p4 = new Paciente("Ana Pereira", "44444444444", 58.4, 162, 24, "47 99999-4444");
        Paciente p5 = new Paciente("Fernanda Lima", "55555555555", 70.1, 170, 31, "47 99999-5555");

        pacienteRepositorio.salvarPaciente(p1);
        pacienteRepositorio.salvarPaciente(p2);
        pacienteRepositorio.salvarPaciente(p3);
        pacienteRepositorio.salvarPaciente(p4);
        pacienteRepositorio.salvarPaciente(p5);

        // Medicamento
        Medicamento m1 = new Medicamento("Semaglutida", 0.45, 50000);
        Medicamento m2 = new Medicamento("Tirzepatida", 0.75, 35000);
        Medicamento m3 = new Medicamento("Vitamina B12", 0.08, 100000);

        medicamentoRepositorio.salvarMedicamento(m1);
        medicamentoRepositorio.salvarMedicamento(m2);
        medicamentoRepositorio.salvarMedicamento(m3);

        Map<Long, Paciente> pacientes = pacienteRepositorio.buscarTodosPacientes();
        Set<Medicamento> medicamentos = medicamentoRepositorio.buscarTodosMedicamentos();

        // Consultas
        consultaRepositorio.salvarConsulta(new Consulta(
                LocalDate.of(2026, 6, 5),
                "09:00",
                "Primeira consulta",
                pacientes.get((long) 1)));

        consultaRepositorio.salvarConsulta(new Consulta(
                LocalDate.of(2026, 6, 5),
                "10:00",
                "Retorno",
                pacientes.get((long)2)));

        consultaRepositorio.salvarConsulta(new Consulta(
                LocalDate.of(2026, 7, 6),
                "14:30",
                "Avaliação física",
                pacientes.get((long)3)));

        consultaRepositorio.salvarConsulta(new Consulta(
                LocalDate.of(2026, 6, 7),
                "15:00",
                "Acompanhamento",
                pacientes.get((long)4)));

        consultaRepositorio.salvarConsulta(new Consulta(
                LocalDate.of(2026, 7, 8),
                "16:00",
                "",
                pacientes.get((long)5)));

        // Entradas
        movFinanceiraRepositorio.salvarMovimentacaoFinanceira(new Entrada(
                "Consulta João Silva",
                LocalDate.of(2026, 7, 5),
                250.00,
                pacientes.get((long)1)));

        movFinanceiraRepositorio.salvarMovimentacaoFinanceira(new Entrada(
                "Consulta Maria Oliveira",
                LocalDate.of(2026, 7, 5),
                250.00,
                pacientes.get((long)2)));

        movFinanceiraRepositorio.salvarMovimentacaoFinanceira(new Entrada(
                "Consulta Carlos Souza",
                LocalDate.of(2026, 7, 6),
                300.00,
                pacientes.get((long)3)));

        // Despesas
        movFinanceiraRepositorio.salvarMovimentacaoFinanceira(new Despesa(
                "Aplicação Semaglutida",
                LocalDate.of(2026, 7, 5),
                90.00,
                200,
                0.45,
                (Medicamento) medicamentos.toArray()[0]));

        movFinanceiraRepositorio.salvarMovimentacaoFinanceira(new Despesa(
                "Aplicação Tirzepatida",
                LocalDate.of(2026, 7, 6),
                150.00,
                200,
                0.75,
                (Medicamento) medicamentos.toArray()[1]));

        movFinanceiraRepositorio.salvarMovimentacaoFinanceira(new Despesa(
                "Vitamina B12",
                LocalDate.of(2026, 7, 7),
                8.00,
                100,
                0.08,
                (Medicamento) medicamentos.toArray()[2]));
    }
}
