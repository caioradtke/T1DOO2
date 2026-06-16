/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udesc.br.controller;

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
    private Medicamento medicamentoModelo;
    
    public CadastrarMedicamentoControlador(CadastrarMedicamentoVisao visao, MedicamentoRepositorio medicamentoRepositorio){
        this.visao = visao;
        this.medicamentoRepositorio = medicamentoRepositorio;
        initTela();
    }

    @Override
    public void initTela() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void adicionarAcoes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void atualizarTela() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
