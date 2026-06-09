package udesc.br.controller;

import exception.PacienteException;
import udesc.br.model.Paciente;
import udesc.br.repository.PacienteRepositorio;
import vision.CriarPacienteVisao;


public class CriarPacienteControlador {

    private CriarPacienteVisao visao;
    private Paciente modelo;
    private PacienteRepositorio pacienteRepositorio;

    public CriarPacienteControlador(CriarPacienteVisao visao, PacienteRepositorio pacienteRepositorio){
        this.visao = visao;
        this.pacienteRepositorio = pacienteRepositorio;
        initTela();
    }

    public void initTela(){
        adicionarAcoes();
        visao.apresentarPacientes(pacienteRepositorio.buscarTodosPacientes());
        this.visao.apresentarTela();
    }

    public void adicionarAcoes(){
        
        visao.adicionarAcaoBtnSalvar(e -> salvarPaciente());
    }



    //Fluxo principal
    public void salvarPaciente(){
        try {
            String nome = visao.getPacienteNome();
            String cpf = visao.getPacienteCpf();
            String telefone = visao.getPacienteTelefone();
            Double altura = visao.getPacienteAltura();
            Double peso = visao.getPacienteAltura();

            modelo = new Paciente(nome, cpf, peso, altura, telefone);

            pacienteRepositorio.salvarPaciente(modelo);

            visao.apresentarPacientes(pacienteRepositorio.buscarTodosPacientes());
            visao.apresentarMensagem(modelo.toString() + " Salvo com sucesso");
            visao.limparTela();

        } catch (PacienteException ex){
            visao.apresentarMensagem(ex.getMessage());
        } catch (Exception ex) {
            visao.apresentarMensagem("Erro ao salvar no banco: " + ex.getMessage());
        }
    }
}
