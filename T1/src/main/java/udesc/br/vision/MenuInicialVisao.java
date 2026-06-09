package udesc.br.vision;

import udesc.br.controller.CriarPacienteControlador;
import udesc.br.dao.PacienteDAO;
import udesc.br.repository.PacienteRepositorio;
import udesc.br.vision.CriarPacienteVisao;

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
        btnCriarPaciente1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnCriarPaciente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCriarPaciente.setText("Criar Paciente");
        btnCriarPaciente.setToolTipText("");

        btnCriarPaciente1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCriarPaciente1.setText("Manter Paciente");
        btnCriarPaciente1.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCriarPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCriarPaciente1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnCriarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCriarPaciente1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCriarPaciente;
    private javax.swing.JButton btnCriarPaciente1;
    // End of variables declaration//GEN-END:variables
}
