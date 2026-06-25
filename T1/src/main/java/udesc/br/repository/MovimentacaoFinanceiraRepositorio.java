package udesc.br.repository;

import java.util.List;
import udesc.br.model.Despesa;
import udesc.br.model.Entrada;
import udesc.br.model.MovimentacaoFinanceira;

public interface MovimentacaoFinanceiraRepositorio {
    void salvarMovimentacaoFinanceira(MovimentacaoFinanceira mov);
    List <MovimentacaoFinanceira> buscarTodasMovimentacaoFinanceiras();
    List <Despesa> buscarTodasDespesas();
    List <Entrada> buscarTodasEntradas();
    void apagar(MovimentacaoFinanceira mov);
    double buscarTotalSaldoMes();
}
