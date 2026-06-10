package udesc.br.controller;

import java.util.List;
import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.ManterPacienteVisao;

public class ManterPacienteControlador {
   
    private ManterPacienteVisao visao;
    private PacienteRepositorio repositorio;
    private Paciente pacienteModelo;
    
    public ManterPacienteControlador(ManterPacienteVisao manterPacienteVisaoPaciente, PacienteRepositorio produtoRepositorio){
        this.visao = visao;
        this.repositorio = repositorio;
        initTela();
    }
    
   
    
    public void initTela(){
        //Acão
        alterarPaciente();
        
        //Inicializar combobox
        List<Paciente> pacientes = repositorio.buscarTodosPacientes();
        visao.initCbProdutos(pacientes);
        
        this.visao.apresentarTela();
        
    }
    
    public void alterarPaciente() {
        pacienteModelo = visao.getPacienteSelecionado(); // ta errado aq ó, corrigir
        visao.preencherCampos(pacienteModelo);
    }
    
}