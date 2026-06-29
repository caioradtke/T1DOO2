package udesc.br.controller;

import udesc.br.repository.MovimentacaoFinanceiraRepositorio;
import udesc.br.vision.financeiro.MovimentacaoFinanceiraVisao;

public class FinanceiroControlador implements ControladorPaineis {
    
    private MovimentacaoFinanceiraVisao visao;
    private MovimentacaoFinanceiraRepositorio repositorio;

    public FinanceiroControlador(MovimentacaoFinanceiraVisao visao, MovimentacaoFinanceiraRepositorio repositorio) {
        this.visao = visao;
        this.repositorio = repositorio;
        initTela();
    }

    
    @Override
    public void initTela() {
        visao.apresentarTela(repositorio.buscarTodasEntradas(),repositorio.buscarTodasDespesas());
        visao.getSaldoTotalMes(repositorio.buscarTotalSaldoMes());
    }

    @Override
    public void adicionarAcoes() {
        
    }

    @Override
    public void atualizarTela() {
        visao.apresentarTela(repositorio.buscarTodasEntradas(),repositorio.buscarTodasDespesas());
        visao.getSaldoTotalMes(repositorio.buscarTotalSaldoMes());
    }
}
