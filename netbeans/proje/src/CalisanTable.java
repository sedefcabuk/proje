
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sedef
 */
public class CalisanTable extends javax.swing.JFrame {

    DefaultTableModel model;
    /**
     * Creates new form Calisan
     */
    public CalisanTable() {
        initComponents();
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        populateTable();
    }
    
    public void populateTable(){
        model = (DefaultTableModel)tblCalisan.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Calisan> calisanlar = getCalisanlar();
            for(Calisan calisan: calisanlar){
                Object[] row = {calisan.getId(), calisan.getAd(),
                    calisan.getSoyad(), calisan.getTelNo()};
                model.addRow(row);
            }
            tblCalisan.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblCalisan.rowAtPoint(evt.getPoint());
                int col = tblCalisan.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    int selectedId =(int) model.getValueAt(row, 0);
                    String selectedAd = (String)model.getValueAt(row, 1);
                    String selectedSoyad = (String)model.getValueAt(row, 2);
                    String selectedTel = (String)model.getValueAt(row, 3);
                    showUpdateEmployeeDialog(CalisanTable.this, tblCalisan, row);
                    
                    
                }
            }
        });
           
           
        } catch (SQLException ex) {
            
        } 
    }
   public ArrayList<Calisan> getCalisanlar() throws SQLException{
        Connection connection=null;
        DbHelper dbHelper=new DbHelper();
        PreparedStatement statement=null;
        ResultSet resultSet;
        ArrayList<Calisan> calisanlar=null;
        
        try{
            connection = dbHelper.getConnection();
            String sql = "select * from projeler.calisan ";
            statement = connection.prepareStatement(sql);      
            resultSet= statement.executeQuery();
            calisanlar = new ArrayList<Calisan>();
            while(resultSet.next()){
                calisanlar.add(new Calisan(
                        
                        resultSet.getInt("calId"),
                        resultSet.getString("calAdı"),
                        resultSet.getString("calSoyadı"),
                        resultSet.getString("calTelNo")
                        
                ));
            }
        }catch(SQLException exception){
            dbHelper.showErrorMessage(exception);
            
        }finally{
            statement.close();
            connection.close();
        }
        return calisanlar;
    }

    /**


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblCalisan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblAd = new javax.swing.JLabel();
        txtAd = new javax.swing.JTextField();
        lblSoyad = new javax.swing.JLabel();
        txtSoyad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblCalisan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Ad", "Soyad", "Telefon Numarası"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCalisan);
        if (tblCalisan.getColumnModel().getColumnCount() > 0) {
            tblCalisan.getColumnModel().getColumn(0).setResizable(false);
            tblCalisan.getColumnModel().getColumn(1).setResizable(false);
            tblCalisan.getColumnModel().getColumn(2).setResizable(false);
            tblCalisan.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Çalışanlar");

        lblAd.setText("Ad");

        lblSoyad.setText("Soyad");

        jLabel4.setText("Telefon Numarası");

        jButton1.setText("Ekle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(248, 248, 248))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAd)
                    .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoyad)
                    .addComponent(txtSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Connection connection=null;
        DbHelper dbHelper=new DbHelper();
        PreparedStatement statement =null;
        try{
            connection = dbHelper.getConnection();
            String sql = "insert into calisan (calAdı,calSoyadı,calTelNo) values(?,?,?)";
            statement = connection.prepareStatement(sql);
        
            statement.setString(1, txtAd.getText());
            statement.setString(2, txtSoyad.getText());
            statement.setString(3, txtTel.getText());
            
            int result = statement.executeUpdate();
            txtAd.setText("");
            txtSoyad.setText("");
            txtTel.setText("");
            populateTable();
            
        }catch(SQLException exception){
            dbHelper.showErrorMessage(exception);
            
        }finally{
            try {
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    private static void showUpdateEmployeeDialog(JFrame frame, JTable table, int selectedRow) {
        int calisanId = (int)table.getValueAt(selectedRow, 0);
    
        String oldAd = (String) table.getValueAt(selectedRow, 1);
        String oldSoyad = (String) table.getValueAt(selectedRow, 2);
        String oldTelNo = (String) table.getValueAt(selectedRow, 3);

        JTextField adField = new JTextField(oldAd);
        adField.setPreferredSize(new Dimension(150, 30));
        JTextField soyadField = new JTextField(oldSoyad);
        soyadField.setPreferredSize(new Dimension(150, 30));
        JTextField telNoField = new JTextField(oldTelNo);
        telNoField.setPreferredSize(new Dimension(150, 30));
        
        JDialog dialog = new JDialog(frame, "Çalışan Bilgisi Güncelleme", true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Ad"));
        panel.add(adField);
        panel.add(new JLabel("Soyad"));
        panel.add(soyadField);
        panel.add(new JLabel("Tel No"));
        panel.add(telNoField);

        JButton saveButton = new JButton("Kaydet");
        JButton silButton = new JButton("Sil");
        JButton gorevButton = new JButton("Görevleri Göster");
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newAd = adField.getText();
                String newSoyad= soyadField.getText();
                 String newTelNo = telNoField.getText();

                if (!newAd.isEmpty() && !newSoyad.isEmpty() && !newTelNo.isEmpty()) {
                    Connection connection=null;
                    DbHelper dbHelper=new DbHelper();
                    PreparedStatement statement =null;
                    try{
                        connection = dbHelper.getConnection();
                        String sql = "update calisan set calAdı = ?, calSoyadı = ? , calTelNo = ? where calId=?" ;


                        statement = connection.prepareStatement(sql);
                        statement.setString(1, newAd);
                        statement.setString(2, newSoyad);
                        statement.setString(3, newTelNo);
                        statement.setInt(4,calisanId);

                        int result = statement.executeUpdate();
                        
                        ((DefaultTableModel) table.getModel()).setValueAt(newAd, selectedRow, 1);
                        ((DefaultTableModel) table.getModel()).setValueAt(newSoyad, selectedRow, 2);
                        ((DefaultTableModel) table.getModel()).setValueAt(newTelNo, selectedRow, 3);
                        
                        Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                        if  (window != null && window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose(); 
                        }
                

                    }catch(SQLException exception){
                        dbHelper.showErrorMessage(exception);

                    }finally{
                        try {
                            statement.close();
                            connection.close();
                        } catch (SQLException ex) {

                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Lütfen geçerli ad, soyad, telefon girin.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Connection connection=null;
                DbHelper dbHelper=new DbHelper();
                PreparedStatement statement =null;
                try{
                    connection = dbHelper.getConnection();
                    String sql = "delete from calisan where calId=?" ;


                    statement = connection.prepareStatement(sql);

                    statement.setInt(1, calisanId);


                    int result = statement.executeUpdate();
                    ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                    Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                        if  (window != null && window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose(); 
                        }

                }catch(SQLException exception){
                    dbHelper.showErrorMessage(exception);

                }finally{
                    try {
                        statement.close();
                        connection.close();
                    } catch (SQLException ex) {

                    }
                }
                
               
            }
        });
        
        gorevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
               Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                        if  (window != null && window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            dialog.dispose(); 
                        }
                CalisanGorevTable calisanGorevTable =new CalisanGorevTable(calisanId);
                 calisanGorevTable.setLocationRelativeTo(frame);
                 
                calisanGorevTable.setVisible(true);
                
               
            }
        });
        
       
        panel.add(saveButton);
        panel.add(silButton);
        panel.add(gorevButton);
        dialog.add(panel);

        
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalisanTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalisanTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalisanTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalisanTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalisanTable().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAd;
    private javax.swing.JLabel lblSoyad;
    private javax.swing.JTable tblCalisan;
    private javax.swing.JTextField txtAd;
    private javax.swing.JTextField txtSoyad;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
