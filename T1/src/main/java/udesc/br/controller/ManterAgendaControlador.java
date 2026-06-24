package udesc.br.controller;


import udesc.br.model.Agenda;
import udesc.br.model.Consulta;
import udesc.br.model.Paciente;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.agenda.ManterAgendaVisao;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManterAgendaControlador implements Controlador {

    private ManterAgendaVisao visao;
    private ConsultaRepositorio repositorio;
    private PacienteRepositorio pacienteRepositorio;
    private Agenda modelo;

    private List<Dia> diasMes;
    private int mesSelecionado;
    private int anoSelecionado;

    private int anoAtual;
    private int mesAtual;
    private int diaAtual;

    private boolean itemCB = false;

    public ManterAgendaControlador(ManterAgendaVisao visao, ConsultaRepositorio repositorio, PacienteRepositorio pacienteRepositorio) {
        this.visao = visao;
        this.repositorio = repositorio;
        this.pacienteRepositorio = pacienteRepositorio;
        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();

        LocalDate dataReal = LocalDate.now();
        anoAtual = dataReal.getYear();
        mesAtual = dataReal.getMonthValue();
        diaAtual = dataReal.getDayOfMonth();

        popularCBAno(anoAtual);
        popularCBPacientes(pacienteRepositorio.buscarTodosPacientes());

        mesSelecionado = mesAtual;
        anoSelecionado = anoAtual;
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoMesAnterior(e -> voltarMes());
        visao.adicionarAcaoProximoMes(e -> avancarMes());
        visao.adicionarAcaoCBAno(e -> {if (itemCB) atualizarTela();});
        visao.adicionarAcaoAgendarConsulta(e -> salvarConsulta());
    }

    public void salvarConsulta() {
        Consulta consultaModelo = new Consulta(visao.getCampoData(), visao.getObservacao(), visao.getPacienteSelecionado());
        repositorio.salvarConsulta(consultaModelo);
    }

    public String getMesString(int mes) {
        switch (mes) {
            case 1:
                return "JANEIRO";
            case 2:
                return "FEVEREIRO";
            case 3:
                return "MARÇO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAIO";
            case 6:
                return "JUNHO";
            case 7:
                return "JULHO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SETEMBRO";
            case 10:
                return "OUTUBRO";
            case 11:
                return "NOVEMBRO";
            case 12:
                return "DEZEMBRO";
            default:
                return "NÂO EXISTE";
        }
    }

    public void voltarMes() {
        mesSelecionado--;
        if (mesSelecionado < 1) {
            mesSelecionado = 12;
        }
        atualizarTela();
    }

    public void avancarMes() {
        mesSelecionado++;
        if (mesSelecionado > 12) {
            mesSelecionado = 1;
        }
        atualizarTela();
    }

    public void popularCBPacientes(Map<Long,Paciente> pacientes){
        JComboBox<Paciente> cbPacientePop = visao.getCbPaciente();
        cbPacientePop.removeAllItems();
        for (Paciente p : pacientes.values()) {
            cbPacientePop.addItem(p);
        }
        visao.setCbPaciente(cbPacientePop);
    }

    public void popularCBAno(int anoAtual) {
        visao.limparCBAno();
        int menorAno = 2024;
        if (anoAtual < menorAno) {
            menorAno = anoAtual - 5;
        }
        for(int i = menorAno; i <= anoAtual; i++ ) {
            visao.adicionarItemCBAno(Integer.toString(i));
        }
        itemCB = true;
    }

    @Override
    public void atualizarTela() {
        visao.limparTela();
        visao.setLabelMes(getMesString(mesSelecionado));

        popularCBPacientes(pacienteRepositorio.buscarTodosPacientes());

        anoSelecionado = visao.getAno();

        diasMes = carregarMes(mesSelecionado, anoSelecionado);
        gerarCalendario();
    }

    private List<Dia> carregarMes(int mes, int ano) {
        YearMonth mesAno = YearMonth.of(ano, mes);

        int diasNoMes = mesAno.lengthOfMonth(); // 31

        LocalDate primeiroDia = mesAno.atDay(1);

        int dayOfWeek = primeiroDia.getDayOfWeek().getValue();
        int primeiroDiaSemana = dayOfWeek == 7 ? 1 : dayOfWeek + 1;

        List<Dia> diasMes = new ArrayList<>();

        for (int i = 1; i <= diasNoMes; i++ ) {

            diasMes.add(new Dia(i, primeiroDiaSemana, null));
        }

        return diasMes;
    }

    public void gerarCalendario() {
        List<Consulta> consultas = repositorio.buscarConsultasData(mesSelecionado, anoSelecionado);
        int diaSemana = diasMes.get(0).diaSemana;
        int totalDias = diasMes.size();

        for (int i = 0; i < diaSemana -1; i++) {
            visao.addCalendario(new JPanel());
        }
        for (Dia dia : diasMes) {
            for (Consulta c : consultas) {
                if (dia.dia == c.getData().getDayOfMonth()) {
                    dia.addConsulta(c);
                }
            }
            visao.addCalendario(dia.gerarComponente());
        }
        int diasRestantes = 49 - (diaSemana - 1 + totalDias);
        for (int i = 0; i < diasRestantes; i++) {
            visao.addCalendario(new JPanel());
        }
    }

    public class Dia {
        protected int dia;
        protected int diaSemana; // variavel zuada q armazena o primeiro dia do mes
        protected List<Consulta> consultas;

        public Dia(int dia, int diaSemana, Agenda agenda) {
            this.dia = dia;
            this.diaSemana = diaSemana;
            this.consultas = new ArrayList<>();
        }

        public JPanel gerarComponente() {
            JPanel div = new JPanel();
            div.setName(Integer.toString(dia));
//            div.setSize(30, 30);
            div.setBackground(Color.LIGHT_GRAY);
            div.setLayout(new BorderLayout());

            JLabel diaLabel = new JLabel();

            diaLabel.setText(dia == 0 ? "" :  Integer.toString(dia));

            diaLabel.setHorizontalAlignment(SwingConstants.LEFT);
            diaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            div.add(diaLabel, BorderLayout.NORTH);

            JPanel diaPainel = new JPanel();
            diaPainel.setSize(20, 50);
            diaPainel.setLayout(new BoxLayout(diaPainel, BoxLayout.Y_AXIS));

            for (Consulta consulta : consultas) {
                System.out.println("Adicionando Consulta " + dia);
                JButton consultaButton = new JButton();

                consultaButton.setText("Consulta " + consulta.getStatus());
                consultaButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                consultaButton.setBorderPainted(false);
                consultaButton.setContentAreaFilled(false);
                consultaButton.setFocusPainted(false);
                consultaButton.setOpaque(false);

                diaPainel.add(consultaButton);
            }

            div.add(diaPainel, BorderLayout.CENTER);
            return div;
        }

        public void addConsulta(Consulta consulta) {
            this.consultas.add(consulta);
        }

        public void removeConsulta(Consulta consulta) {
            this.consultas.remove(consulta);
        }
    }
}

















