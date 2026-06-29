package udesc.br.controller;

import udesc.br.controller.interfaces.ControladorPaineis;
import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.paciente.CadastrarPacienteVisao;


public class CadastrarPacienteControlador implements ControladorPaineis {

    private CadastrarPacienteVisao visao;
    private Paciente modelo;
    private PacienteRepositorio pacienteRepositorio;

    public CadastrarPacienteControlador(CadastrarPacienteVisao visao, PacienteRepositorio pacienteRepositorio){
        this.visao = visao;
        this.pacienteRepositorio = pacienteRepositorio;
        initTela();
    }

    public CadastrarPacienteControlador(PacienteRepositorio pacienteRepositorio){
        this.visao = new CadastrarPacienteVisao();
        this.pacienteRepositorio = pacienteRepositorio;
        initTela();
    }

    @Override
    public void initTela(){
        adicionarAcoes();
        visao.apresentarPacientes(pacienteRepositorio.buscarTodosPacientes());
    }

    @Override
    public void atualizarTela(){
        visao.limparTela();
        visao.apresentarPacientes(pacienteRepositorio.buscarTodosPacientes());
    }

    @Override
    public void adicionarAcoes(){
        visao.adicionarAcaoBtnSalvar(e -> salvarPaciente());
    }

    //Fluxo principal
    public void salvarPaciente(){
        try {
            System.out.println("Salvando Paciente");
            String nome = visao.getPacienteNome().trim();
            String cpf = visao.getPacienteCpf().trim();
            String telefone = visao.getPacienteTelefone().trim();
            double altura = visao.getPacienteAltura();
            double peso = visao.getPacientePeso();
            int idade = visao.getPacienteIdade();

            modelo = new Paciente(nome, cpf, peso, altura, idade, telefone);

            pacienteRepositorio.salvarPaciente(modelo);

            visao.apresentarPacientes(pacienteRepositorio.buscarTodosPacientes());
            visao.apresentarMensagem("Paciente salvo com sucesso");
            visao.limparTela();

        } catch (Exception ex) {
            System.err.println("Erro ao salvar no banco: " + ex.getMessage());
            visao.apresentarMensagem(ex.getMessage());
        }
    }
}
