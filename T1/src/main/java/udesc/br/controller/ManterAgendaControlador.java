package udesc.br.controller;


import udesc.br.controller.interfaces.ConsultaRepositorioListener;
import udesc.br.controller.interfaces.ControladorPaineis;
import udesc.br.dao.MovimentacaoFinanceiraDAO;
import udesc.br.dao.PacienteDAO;
import udesc.br.model.Consulta;
import udesc.br.repository.ConsultaRepositorio;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.consulta.ConsultaVisao;
import udesc.br.vision.consulta.CadastrarConsultaVisao;
import udesc.br.vision.consulta.ManterAgendaVisao;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class ManterAgendaControlador implements ControladorPaineis, ConsultaRepositorioListener {

    private ManterAgendaVisao visao;
    private ConsultaRepositorio repositorio;
    private PacienteRepositorio pacienteRepositorio;

    private List<Dia> diasMes;
    private int mesSelecionado;
    private int anoSelecionado;

    private int anoAtual;
    private int mesAtual;

    private boolean itemCB = false;

    public ManterAgendaControlador(ManterAgendaVisao visao, ConsultaRepositorio repositorio, PacienteRepositorio pacienteRepositorio) {
        this.visao = visao;
        this.repositorio = repositorio;
        this.pacienteRepositorio = pacienteRepositorio;

        repositorio.setListener(this);

        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();

        LocalDate dataReal = LocalDate.now();
        anoAtual = dataReal.getYear();
        mesAtual = dataReal.getMonthValue();

        mesSelecionado = mesAtual;
        anoSelecionado = anoAtual;

        visao.setSpinnerAno(anoAtual);
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoMesAnterior( e -> voltarMes() );
        visao.adicionarAcaoProximoMes(  e -> avancarMes() );
        visao.adicionarAcaoSpinnerAno( e -> { atualizarTela(); });
        visao.adicionarAcaoCadastrarConsulta(e -> abrirTelaCadastroConsulta());
    }

    public void abrirTelaCadastroConsulta() {
        CadastrarConsultaVisao visaoCriarConsulta = new CadastrarConsultaVisao();
        CadastrarConsultaControlador telaCadastroConsulta =
                new CadastrarConsultaControlador(visaoCriarConsulta, pacienteRepositorio, repositorio);
        visaoCriarConsulta.setLocationRelativeTo(null);
    }

    @Override
    public void consultaAlterada(Consulta consulta) {
        atualizarTela();
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

    @Override
    public void atualizarTela() {
        visao.limparTela();
        visao.setLabelMes(getMesString(mesSelecionado));

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

            diasMes.add(new Dia(i, primeiroDiaSemana, visao, repositorio));
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
        int diasTotais = diaSemana + totalDias;
        int diasRestantes = diasTotais / 7 + 1 - diasTotais;
        for (int i = 0; i < diasRestantes; i++) {
            visao.addCalendario(new JPanel());
        }
    }

    public static class Dia {
        protected int dia;
        protected int diaSemana; // variavel zuada q armazena o primeiro dia do mes
        protected List<Consulta> consultas;
        protected ManterAgendaVisao manterAgendaVisao;
        protected ConsultaRepositorio consultaRepositorio;

        public Dia(int dia, int diaSemana, ManterAgendaVisao manterAgendaVisao,  ConsultaRepositorio consultaRepositorio) {
            this.dia = dia;
            this.diaSemana = diaSemana;
            this.consultas = new ArrayList<>();
            this.manterAgendaVisao = manterAgendaVisao;
            this.consultaRepositorio = consultaRepositorio;
        }

        public JPanel gerarComponente() {
            JPanel div = new JPanel();
            div.setName(Integer.toString(dia));

            div.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, false));
            div.setLayout(new BorderLayout());

            JLabel diaLabel = new JLabel();

            diaLabel.setText(dia == 0 ? "" :  Integer.toString(dia));

            diaLabel.setHorizontalAlignment(SwingConstants.LEFT);
            diaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

            div.add(diaLabel, BorderLayout.NORTH);

            JPanel diaPainel = new JPanel();
            diaPainel.setLayout(new BoxLayout(diaPainel, BoxLayout.Y_AXIS));

            for (Consulta consulta : consultas) {
                System.out.println("Adicionando Consulta " + dia);
                JButton consultaButton = new JButton();

                String nomePaciente = consulta.getPaciente().getNome();
                if (nomePaciente.length() > 3) {
                    nomePaciente = nomePaciente.substring(0, 12);
                }
                String text = nomePaciente;

                consultaButton.setText( "<html><u>"+ text + "</u></html>");
                consultaButton.setFont(new Font("Arial", Font.PLAIN, 12));
                consultaButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                consultaButton.setBorderPainted(false);
                consultaButton.setContentAreaFilled(false);
                consultaButton.setFocusPainted(false);
                consultaButton.setOpaque(false);
                consultaButton.addActionListener(e -> acaoAbrirConsulta(consulta));
                consultaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                diaPainel.add(consultaButton);
            }

            div.add(diaPainel, BorderLayout.LINE_START);
            return div;
        }

        public void addConsulta(Consulta consulta) {
            this.consultas.add(consulta);
        }

        public void acaoAbrirConsulta(Consulta consulta) {
            ConsultaVisao consultaVisao = new ConsultaVisao();
            ConsultaControlador consultaControlador =
                    new ConsultaControlador(consultaVisao, consultaRepositorio, consulta);
            consultaVisao.setLocationRelativeTo(null);
            consultaVisao.setVisible(true);
        }
    }
}

















