package udesc.br.vision.financeiro;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import udesc.br.model.Despesa;
import udesc.br.model.Entrada;
import udesc.br.model.Paciente;
import udesc.br.repository.MovimentacaoFinanceiraRepositorio;
import udesc.br.repository.PacienteRepositorio;

public class MovimentacaoFinanceiraVisao extends javax.swing.JPanel {

    DefaultTableModel tabela;
    private MovimentacaoFinanceiraRepositorio repositorio;
    
    public MovimentacaoFinanceiraVisao() {
        initComponents();
    }
    
    public void apresentarTela(List<Entrada> entradas,List<Despesa> despesas) {
        apresentarEntradas(entradas);
        apresentarDespesas(despesas);
    }

    public void apresentarEntradas(List<Entrada> entradas) {
        tabela = (DefaultTableModel) tabEntradas.getModel();
            tabela.setRowCount(0);
        
          for (Entrada e : entradas) {
                tabela.addRow(new Object[]{
                e.getValor(),
                e.getData(),
                e.getPaciente(),        
            });
        }
    }
    
    public void apresentarDespesas(List<Despesa> despesas) {
        tabela = (DefaultTableModel) tabDespesas.getModel();
            tabela.setRowCount(0);
        
          for (Despesa d : despesas) {
                tabela.addRow(new Object[]{
                d.getValor(),
                d.getValorPorMg(), 
                d.getMedicamento(),
                d.getQuantidade(),
                d.getData(),
            });
        }
    }
    
    public void getSaldoTotalMes(Double saldo) {
        tfSaldo.setText(Double.toString(saldo));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabEntradas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabDespesas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfSaldo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        tabEntradas.setAutoCreateRowSorter(true);
        tabEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Valor", "Data", "Paciente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabEntradas);

        tabDespesas.setAutoCreateRowSorter(true);
        tabDespesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Valor", "Valor por Mligrama", "Medicamento", "Quantidade", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabDespesas);

        jLabel1.setText("Entradas");

        jLabel2.setText("Despesas");

        jLabel3.setText("Saldo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(tfSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabDespesas;
    private javax.swing.JTable tabEntradas;
    private javax.swing.JTextField tfSaldo;
    // End of variables declaration//GEN-END:variables
}
