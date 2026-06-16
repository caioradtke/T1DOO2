package udesc.br.repository;

import java.util.List;
import udesc.br.model.MovimentacaoFinanceira;

public interface MovimentacaoFinanceiraRepositorio {
    void salvarMovimentacaoFinanceira(MovimentacaoFinanceira mov);
    List <MovimentacaoFinanceira> buscarTodasMovimentacaoFinanceiras();
    void apagar(MovimentacaoFinanceira mov);
}
