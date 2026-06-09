package udesc.br.vision;

import udesc.br.controller.CriarPacienteControlador;
import udesc.br.dao.PacienteDAO;
import udesc.br.repository.PacienteRepositorio;
import vision.CriarPacienteVisao;

public class MenuInicialVisao extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuInicialVisao.class.getName());

    /**
     * Creates new form MenuInicialVisao
     */
    public MenuInicialVisao() {
        initComponents();
        btnCriarPaciente.addActionListener(e -> acaoCriarPaciente());
        setVisible(true);
    }

    public void acaoCriarPaciente(){
        CriarPacienteVisao visao = new CriarPacienteVisao();
        PacienteRepositorio repositorio = new PacienteDAO();
         CriarPacienteControlador controlador = new CriarPacienteControlador(visao, repositorio);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCriarPaciente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCriarPaciente.setText("Criar Paciente");
        btnCriarPaciente.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(btnCriarPaciente)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(btnCriarPaciente)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCriarPaciente;
    // End of variables declaration//GEN-END:variables
}
