package udesc.br.controller;

import udesc.br.controller.interfaces.ControladorPaineis;
import udesc.br.repository.MedicamentoRepositorio;
import udesc.br.repository.MovimentacaoFinanceiraRepositorio;
import udesc.br.vision.medicamentos.EditarMedicamentoVisao;
import udesc.br.vision.medicamentos.ManterMedicamentoVisao;

public class ManterMedicamentoControlador implements ControladorPaineis {
    ManterMedicamentoVisao visao;
    MedicamentoRepositorio medRepo;
    MovimentacaoFinanceiraRepositorio despesaRepo;
    
    public ManterMedicamentoControlador(ManterMedicamentoVisao visao, MedicamentoRepositorio medRepo, MovimentacaoFinanceiraRepositorio despesaRepo){
        this.visao = visao;
        this.medRepo = medRepo;
        this.despesaRepo = despesaRepo;
        initTela();
    }
    @Override
    public void initTela() {
        adicionarAcoes();
        visao.apresentarMedicamentos(medRepo.buscarTodosMedicamentos());
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoBtnEditar(e -> abrirTelaEditar());
    }

    @Override
    public void atualizarTela() {
        visao.apresentarMedicamentos(medRepo.buscarTodosMedicamentos());
    }

    private void abrirTelaEditar(){
        System.out.println("Clicou!");
        EditarMedicamentoVisao editarVisao =
                new EditarMedicamentoVisao();
        new EditarMedicamentoControlador(
                editarVisao,
                medRepo,
                despesaRepo);
        editarVisao.setLocationRelativeTo(null);
    }
}
