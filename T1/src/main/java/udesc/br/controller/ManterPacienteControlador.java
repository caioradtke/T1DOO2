package udesc.br.controller;

import java.util.List;
import java.util.Set;
import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.ManterPacienteVisao;

public class ManterPacienteControlador {
   
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
        visao.initCbProdutos(pacientes);
        
        this.visao.apresentarTela();
        
    }
    
    public void adicionarAcoes() {
        visao.adicionarAcaoBtnBuscar(e-> buscarPaciente());
        visao.adicionarAcaoBtnAlterar(e -> alterarPaciente());
    }
    
    public void buscarPaciente() {
        pacienteModelo = visao.getPacienteSelecionado(); 
        visao.preencherCampos(pacienteModelo);
    }
    
    public void alterarPaciente() {
        visao.alterarAtributos(pacienteModelo);
        visao.apresentarMensagem(pacienteModelo.toString() + " Alterações salvas com sucesso");
    }
}

//Está funcionando, porém, IMC ainda está zerado e ComboBox esta usando toString (ver se da pra mudar)
