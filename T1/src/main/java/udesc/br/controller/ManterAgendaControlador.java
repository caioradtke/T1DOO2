package udesc.br.controller;


import org.hibernate.grammars.hql.HqlParser;
import udesc.br.model.Agenda;
import udesc.br.repository.AgendaRepositorio;
import udesc.br.vision.agenda.ManterAgendaVisao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

public class ManterAgendaControlador implements Controlador {

    private ManterAgendaVisao visao;
    private AgendaRepositorio repositorio;
    private Agenda modelo;

    private List<Dia> diasMes;
    private int mesSelecionado;
    private int anoSelecionado;

    private int anoAtual;
    private int mesAtual;
    private int diaAtual;

    private boolean itemCB = false;

    public ManterAgendaControlador(ManterAgendaVisao visao, AgendaRepositorio repositorio) {
        this.visao = visao;
        this.repositorio = repositorio;
        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();

        LocalDate dataReal = LocalDate.now();
        anoAtual = dataReal.getYear();
        mesAtual = dataReal.getMonthValue();
        diaAtual = dataReal.getDayOfMonth();

        visao.limparCB();
        popularCB(anoAtual);

        mesSelecionado = mesAtual;
        anoSelecionado = anoAtual;
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoMesAnterior(e -> voltarMes());
        visao.adicionarAcaoProximoMes(e -> avancarMes());
        visao.adicionarAcaoCBAno(e -> {if (itemCB) atualizarTela();});
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

    public void popularCB(int anoAtual) {
        int menorAno = 2024;
        if (anoAtual < menorAno) {
            menorAno = anoAtual - 5;
        }
        for(int i = menorAno; i <= anoAtual; i++ ) {
            visao.adicionarItemCB(Integer.toString(i));
        }
        itemCB = true;
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

            diasMes.add(new Dia(i, primeiroDiaSemana, null));
        }

        return diasMes;
    }

    public void gerarCalendario() {
        List<Agenda> agendas = repositorio.buscarAgendasData(mesSelecionado, anoSelecionado);
        int diaSemana = diasMes.get(0).diaSemana;
        int totalDias = diasMes.size();

        for (int i = 0; i < diaSemana -1; i++) {
            visao.addCalendario(new JPanel());
        }
        for (Dia dia : diasMes) {
            for (Agenda a : agendas) {
                if (dia.dia == a.getData().getDayOfMonth()) {
                    dia.setAgenda(a);
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
        protected Agenda agenda;

        public Dia(int dia, int diaSemana, Agenda agenda) {
            this.dia = dia;
            this.diaSemana = diaSemana;
            this.agenda = agenda;
        }

        public JPanel gerarComponente() {
            JPanel div = new JPanel();
            div.setName(Integer.toString(dia));
            JLabel diaLabel = new JLabel();

            diaLabel.setText(dia == 0 ? "" :  Integer.toString(dia));
            div.add(diaLabel);

            if (agenda != null) {
                JLabel consultaLabel = new JLabel();
                consultaLabel.setText(agenda.getNome());
                div.add(consultaLabel);
            }

            return div;
        }

        public void setAgenda(Agenda agenda) {
            this.agenda = agenda;
        }
    }
}

















