/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udesc.br.controller;

import javax.print.attribute.standard.Media;
import udesc.br.model.Medicamento;
import udesc.br.repository.MedicamentoRepositorio;
import udesc.br.vision.medicamentos.CadastrarMedicamentoVisao;

/**
 *
 * @author mayur
 */
public class CadastrarMedicamentoControlador implements Controlador{
    private CadastrarMedicamentoVisao visao;
    private MedicamentoRepositorio medicamentoRepositorio;
    private Medicamento modelo;
    
    public CadastrarMedicamentoControlador(CadastrarMedicamentoVisao visao, MedicamentoRepositorio medicamentoRepositorio){
        this.visao = visao;
        this.medicamentoRepositorio = medicamentoRepositorio;
        initTela();
    }

    @Override
    public void initTela() {
        adicionarAcoes();
    }

    @Override
    public void adicionarAcoes() {
        visao.adicionarAcaoBtnCadastrar(e -> salvarMedicamento());
    }

    @Override
    public void atualizarTela() {
        visao.limparTela();
    }

    private void salvarMedicamento() {
        // pegar informações na tela
        try{
            String nome = visao.getMedicamentoNome().trim();
            double valor = visao.getMedicamentoValorCompra();
            double estoque = visao.getMedicamentoEstoque();
            
            modelo = new Medicamento(nome, valor, estoque);
            
            medicamentoRepositorio.salvarMedicamento(modelo);
            visao.apresentarMensagem("Medicamento cadastrado com sucesso!");
            visao.limparTela();
        }catch (Exception ex) {
            System.err.println("Erro ao salvar no banco: " + ex.getMessage());
            visao.apresentarMensagem("Erro ao salvar no banco de dados");
        }
    }
}
