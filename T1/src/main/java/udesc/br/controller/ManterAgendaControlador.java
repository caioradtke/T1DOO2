package udesc.br.controller;


import org.hibernate.grammars.hql.HqlParser;
import udesc.br.model.Agenda;
import udesc.br.repository.AgendaRepositorio;
import udesc.br.vision.agenda.ManterAgendaVisao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;

public class ManterAgendaControlador implements Controlador {

    private ManterAgendaVisao visao;
    private AgendaRepositorio repositorio;
    private Agenda modelo;

    private int[] diasMes;
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
        gerarCalendario(diasMes);
    }

    private int[] carregarMes(int mes, int ano) {
        YearMonth mesAno = YearMonth.of(ano, mes);

        int diasNoMes = mesAno.lengthOfMonth(); // 31

        LocalDate primeiroDia = mesAno.atDay(1);

        int dayOfWeek = primeiroDia.getDayOfWeek().getValue();
        int diaSemana = dayOfWeek == 7 ? 0  : dayOfWeek;

        int[] mesTotal = new int[diasNoMes + diaSemana];

        int contDiaSemana = diaSemana; // 0
        int contDiaMes = 0;
        for (int i = 0; i < diasNoMes + diaSemana; i++ ) {
            if (contDiaSemana == 0) {
                contDiaMes++;
                mesTotal[i] = contDiaMes;
                continue;
            }

            mesTotal[i] = 0;
            contDiaSemana--;
        }

        return mesTotal;
    }

    public void gerarCalendario(int[] dias) {
        for (int i = 0; i < 49; i++) {
            if (i >= dias.length) {
                visao.addCalendario(gerarDiaMes(0));
                continue;
            }
            visao.addCalendario(gerarDiaMes(dias[i]));
        }
    }

    public JPanel gerarDiaMes(int dia) {
        JPanel div = new JPanel();
        JLabel diaLabel = new JLabel();

        diaLabel.setText(dia == 0 ? "" :  Integer.toString(dia));

        div.add(diaLabel);

        return div;
    }
}

















