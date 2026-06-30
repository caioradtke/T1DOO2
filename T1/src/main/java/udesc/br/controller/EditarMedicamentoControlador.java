/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udesc.br.controller;

import java.time.LocalDate;
import java.util.Set;

import udesc.br.controller.interfaces.ControladorPaineis;
import udesc.br.exception.MedicamentoException;
import udesc.br.model.Despesa;
import udesc.br.model.Medicamento;
import udesc.br.repository.MedicamentoRepositorio;
import udesc.br.repository.MovimentacaoFinanceiraRepositorio;
import udesc.br.vision.medicamentos.EditarMedicamentoVisao;

import javax.swing.*;

/**
 *
 * @author joaom
 */
public class EditarMedicamentoControlador implements ControladorPaineis {
    private EditarMedicamentoVisao visao;
    private ManterMedicamentoControlador manterMedicamentoControlador;
    private MedicamentoRepositorio medRepositorio;
    private Medicamento modelo;
    private Despesa despesa;
    private MovimentacaoFinanceiraRepositorio despesaRepositorio;
    
    public EditarMedicamentoControlador(EditarMedicamentoVisao visao, MedicamentoRepositorio medRepositorio, MovimentacaoFinanceiraRepositorio despesaRepositorio, ManterMedicamentoControlador manterMedicamentoControlador) {
        this.visao = visao;
        this.medRepositorio = medRepositorio;
        this.despesaRepositorio = despesaRepositorio;
        this.manterMedicamentoControlador = manterMedicamentoControlador;

        initTela();
    }

    @Override
    public void initTela() {
        visao.setLocationRelativeTo(null);
        visao.setVisible(true);
        atualizarCbMedicamentos();
        adicionarAcoes();
    }

    public void atualizarCbMedicamentos(){
        Set<Medicamento> lista = medRepositorio.buscarTodosMedicamentos();
        visao.initCbMedicamentos(lista);
        if (!lista.isEmpty()){
            preencherDados();
        }
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoBtnAdicionar(e -> atualizarEstoque());
        visao.adicionarAcaoBtnEditar(e -> atualizarMedicamento());
        visao.adicionarAcaoBtnExcluir(e -> excluirMedicamento());
        visao.adicionarAcaoCbMedicamentos(e -> preencherDados());
    }

    @Override
    public void atualizarTela() {
        visao.limparTela();
        visao.preencherCampos(modelo);
    }
    public Despesa gerarDespesa(Medicamento med, double quantidade){
        
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
            return despesa;
        
    }
public void atualizarEstoque() {
    try {
        modelo = visao.getMedicamento();

        if(modelo == null){
            throw new MedicamentoException("Selecione um medicamento!");
        }

        double quantidade = visao.getQuantidade();

        boolean sucesso = modelo.adicionarEstoque(quantidade);

        if (!sucesso) {
            throw new MedicamentoException("Quantidade inválida!");
        }

        System.out.println("1 - Estoque atualizado");

        medRepositorio.salvarMedicamento(modelo);

        System.out.println("2 - Medicamento salvo");

        Despesa despesa = gerarDespesa(modelo, quantidade);

        System.out.println("3 - Despesa gerada");

        despesaRepositorio.salvarMovimentacaoFinanceira(despesa);

        System.out.println("4 - Despesa salva");

        visao.apresentarMensagem("Estoque atualizado com sucesso!");

        atualizarTela();
        manterMedicamentoControlador.atualizarTela();
    } catch (MedicamentoException ex) {
        ex.printStackTrace();
        visao.apresentarMensagem(ex.getMessage());
    }catch (Exception ex) {
        ex.printStackTrace();
        visao.apresentarMensagem("Erro ao atualizar o estoque!");
    }
}
    
    public void atualizarMedicamento(){
        try {
            modelo = visao.getMedicamento();

            if (modelo == null) {
                throw new MedicamentoException(
                        "Selecione um medicamento.");
            }

            modelo.setValor(
                    visao.getNovoPreco());

            medRepositorio
                    .salvarMedicamento(modelo);

            visao.apresentarMensagem(
                    "Medicamento atualizado com sucesso!");

            atualizarTela();
            manterMedicamentoControlador.atualizarTela();

        } catch (MedicamentoException ex) {
            visao.apresentarMensagem(
                    ex.getMessage());

        } catch (Exception ex) {
            visao.apresentarMensagem(
                    "Erro ao atualizar medicamento.");
            ex.printStackTrace();
        }
    }
    public void excluirMedicamento(){
        try {
            modelo = visao.getMedicamento();

            if (modelo == null) {
                throw new MedicamentoException(
                        "Selecione um medicamento.");
            }

            int opcao = JOptionPane.showConfirmDialog(
                    visao,
                    "Deseja realmente excluir o medicamento \""
                            + modelo.getNome() + "\"?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }

            medRepositorio.apagar(modelo);

            visao.apresentarMensagem(
                    "Medicamento excluído com sucesso!");

            atualizarCbMedicamentos();
            manterMedicamentoControlador.atualizarTela();

        } catch (MedicamentoException ex) {
            visao.apresentarMensagem(
                    ex.getMessage());

        } catch (Exception ex) {
            visao.apresentarMensagem(
                    "Erro ao excluir medicamento.");
            ex.printStackTrace();
        }
    }

    private void preencherDados() {
            try {
        modelo = visao.getMedicamento();
            if (modelo == null) {
                throw new MedicamentoException("Selecione um medicamento.");
            }
        visao.preencheCampos(
                modelo.getValor(),
                modelo.getEstoque()
        );

    } catch (MedicamentoException ex) {
        visao.apresentarMensagem(ex.getMessage());
    }
    }
}
