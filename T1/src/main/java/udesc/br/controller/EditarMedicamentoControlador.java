/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udesc.br.controller;

import java.time.LocalDate;
import java.util.List;
import udesc.br.exception.MedicamentoException;
import udesc.br.model.Despesa;
import udesc.br.model.Medicamento;
import udesc.br.model.MovimentacaoFinanceira;
import udesc.br.repository.MedicamentoRepositorio;
import udesc.br.vision.medicamentos.EditarMedicamentoVisao;

/**
 *
 * @author joaom
 */
public class EditarMedicamentoControlador implements Controlador{
    private EditarMedicamentoVisao visao;
    private MedicamentoRepositorio medRepositorio;
    private Medicamento modelo;
    private Despesa despesa;
    private MovimentacaoFinanceira despesaRepositorio;
    
    public EditarMedicamentoControlador(EditarMedicamentoVisao visao, MedicamentoRepositorio medRepositorio){
        this.visao = visao;
        this.medRepositorio = medRepositorio;
        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();
        List<Medicamento> lista = medRepositorio.buscarTodosMedicamentos();
        visao.initCbMedicamentos(lista);
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoBtnAdicionar(e -> atualizarEstoque());
        visao.adicionarAcaoBtnAdicionar(e -> atualizarMedicamento());
        visao.adicionarAcaoBtnExcluir(e -> excluirMedicamento());
    }

    @Override
    public void atualizarTela() {
        visao.limparTela();
    }
    public void gerarDespesa(Medicamento med, double quantidade){
        //String descricao, LocalDate data, double valor, int quantidade, double valorPorMg, Medicamento medicamento
            String descricao = "";

            double valorPorMg = med.getValor();

            double valorTotal = quantidade * valorPorMg;

            despesa = new Despesa(
                descricao,
                LocalDate.now(),
                valorTotal,
                quantidade,
                valorPorMg,
                med
    );
        
    }
    public void atualizarEstoque(){
        try{
            modelo = visao.getMedicamento();
            double quantidade = visao.getQuantidade();
            boolean sucesso = modelo.adicionarEstoque(quantidade);
            if (!sucesso){
                visao.apresentarMensagem("Quantia Inválida");
                return;
            }
            medRepositorio.salvarMedicamento(modelo);
            gerarDespesa(modelo, quantidade);
            atualizarTela();
        }catch(MedicamentoException ex){
            visao.apresentarMensagem(ex.getMessage());
        }
    }
    
    public void atualizarMedicamento(){
           try {
        modelo = visao.getMedicamento();
        
        modelo.setValor(visao.getNovoPreco());

        medRepositorio.salvarMedicamento(modelo);

        visao.apresentarMensagem("Medicamento atualizado com sucesso!");

        atualizarTela();

    } catch (MedicamentoException ex) {
        visao.apresentarMensagem(ex.getMessage());
    } catch (Exception ex) {
        visao.apresentarMensagem("Erro ao atualizar medicamento.");
        ex.printStackTrace();
    }
    }
    public void excluirMedicamento(){
        
    }
}
