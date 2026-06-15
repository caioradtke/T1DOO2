package udesc.br.controller;

import java.util.List;
import java.util.Set;
import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.paciente.ManterPacienteVisao;

import javax.swing.*;

public class ManterPacienteControlador implements Controlador {
   
    private ManterPacienteVisao visao;
    private PacienteRepositorio repositorio;
    private Paciente pacienteModelo;
    
    public ManterPacienteControlador(ManterPacienteVisao visao, PacienteRepositorio repositorio){
        this.visao = visao;
        this.repositorio = repositorio;
        initTela();
    }

    public void initTela(){
        //Acão
        adicionarAcoes();
        
        //Inicializar combobox
        List<Paciente> pacientes = repositorio.buscarTodosPacientes();
        visao.initCbPacientes(pacientes);
        
    }

    public void atualizarTela(){
        visao.limparTela();
        List<Paciente> pacientes = repositorio.buscarTodosPacientes();
        visao.initCbPacientes(pacientes);
    }
    
    public void adicionarAcoes() {
        visao.adicionarAcaoBtnBuscar(e-> buscarPaciente());
        visao.adicionarAcaoBtnAlterar(e -> alterarPaciente());
        visao.adicionarAcaoBtnExcluir(e ->excluirPaciente());
    }
    
    public void buscarPaciente() {
        pacienteModelo = visao.getPacienteSelecionado(); 
        visao.preencherCampos(pacienteModelo);
    }
    
    public void alterarPaciente() {
        visao.alterarAtributos(pacienteModelo);
        repositorio.salvarPaciente(pacienteModelo);
        visao.apresentarMensagem(pacienteModelo.toString() + " Alterações salvas com sucesso");
        visao.limparTela();
    }
    
    public void excluirPaciente() {

        int confirma = JOptionPane.showConfirmDialog(visao, "Tem certeza que deseja excluir paciente? Esta ação é irreversível.", "Confirmar Exclusão", JOptionPane.OK_CANCEL_OPTION );
        if (confirma == 0) {
            pacienteModelo = visao.getPacienteSelecionado();
            repositorio.apagar(pacienteModelo);
            visao.apresentarMensagem("Paciente removido: " + pacienteModelo.toString());
            visao.initCbPacientes(repositorio.buscarTodosPacientes());
            visao.limparTela();
        }
    }
}


