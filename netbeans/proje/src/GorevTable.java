
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sedef
 */
public class GorevTable extends javax.swing.JFrame {
DefaultTableModel model;
int projeId;
Calisan selectedCalisan;
    /**
     * Creates new form GorevTable
     */
    public GorevTable(int proje) {
        projeId=proje;
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        populateTable();
        

    }
    
    public void populateTable(){
        model = (DefaultTableModel)tblGorevler.getModel();
        model.setRowCount(0);
        try {
            ArrayList<Gorev> gorevler = getGorevler(projeId);
            for(Gorev gorev: gorevler){
                String gorevDurumu="";
                String calisanAdSoyad="";

                  if (gorev.getBaslangicTarihi().after(new Date())) {

                      gorevDurumu="Tamamlanacak";
                  }
                  else if(gorev.getBaslangicTarihi().before(new Date()) && gorev.getBitisTarihi().after(new Date())){
                      gorevDurumu="Devam ediyor";
                    }
                  else if(gorev.getBitisTarihi().before(new Date())){
                      gorevDurumu="Tamamlandı";
                  }
                  if(gorev.getCalisanId()==0){
                      calisanAdSoyad="Çalışan Yok";
                  }else{
                      calisanAdSoyad =gorev.getCalisanAdi() + " " +gorev.getCalisanSoyadi();
                  }
                      
                Object[] row = {gorev.getId(), gorev.getAd(),
                    gorev.getBaslangicTarihi(), gorev.getAdamGunDegeri(), gorev.getBitisTarihi(),gorevDurumu,
                    gorev.getCalisanId(), calisanAdSoyad };
                model.addRow(row);
            }
             tblGorevler.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    int row = tblGorevler.rowAtPoint(evt.getPoint());
                    int col = tblGorevler.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col >= 0) {
                        
                        int gorevId =(int) model.getValueAt(row, 0);
                        int calisanId =(int) model.getValueAt(row, 6);
                        showCustomDialog(GorevTable.this, tblGorevler, gorevId,calisanId,row);

                    }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(GorevTable.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });

           
        } catch (SQLException ex) {
            
        } 
    }
    public ArrayList<Gorev> getGorevler(int projeId) throws SQLException{
        Connection connection=null;
        DbHelper dbHelper=new DbHelper();
        PreparedStatement statement=null;
        ResultSet resultSet;
        ArrayList<Gorev> gorevler=null;
        
        try{
            connection = dbHelper.getConnection();
            String sql = "select g.gorId,g.gorAd,g.gorBasTar,g.gorAdamGun,g.gorBitTar,g.gorDurumu,c.calId, c.calAdı,c.calSoyadı from projeler.gorev g left join projeler.calisan c on c.calId=g.calId where g.prjId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,projeId);       
            resultSet= statement.executeQuery();
            gorevler = new ArrayList<Gorev>();
            while(resultSet.next()){
                gorevler.add(new Gorev(
                        
                        resultSet.getInt("gorId"),
                        resultSet.getString("gorAd"),
                        resultSet.getDate("gorBasTar"),
                        resultSet.getInt("gorAdamGun"),
                        resultSet.getDate("gorBitTar"),
                        resultSet.getString("gorDurumu"),
                        resultSet.getInt("calId"),
                        resultSet.getString("calAdı"),
                        resultSet.getString("calSoyadı")
                        
                ));
            }
        }catch(SQLException exception){
            dbHelper.showErrorMessage(exception);
            
        }finally{
            statement.close();
            connection.close();
        }
        return gorevler;
    }
    
    public void showCustomDialog(JFrame parentFrame, JTable table, int gorevId, int calisanId, int selectedRow) throws SQLException {
        JDialog dialog = new JDialog(parentFrame, "Çalışan Ata", true);

         ArrayList<Calisan> calisanlar;
         calisanlar = getCalisanlar();
         
         

        
        JComboBox<Calisan> customComboBox = new JComboBox<>(calisanlar.toArray(new Calisan[0]));

        
        customComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                selectedCalisan = (Calisan) customComboBox.getSelectedItem();
                
            }
        });

        for (Calisan calisan : calisanlar) {
            if (calisan.getId()== calisanId) {
                customComboBox.setSelectedItem(calisan);
            }
        }
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Çalışan Seçin:"));
        panel.add(customComboBox);

        JButton kaydetButton = new JButton("Kaydet");
        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection=null;
                    DbHelper dbHelper=new DbHelper();
                    PreparedStatement statement =null;
                    try{
                        connection = dbHelper.getConnection();
                        String sql = "update gorev set calId = ? where gorId=?" ;


                        statement = connection.prepareStatement(sql);
                        statement.setInt(1,selectedCalisan.getId());
                        statement.setInt(2,gorevId);

                        int result = statement.executeUpdate();
                        ((DefaultTableModel) table.getModel()).setValueAt(selectedCalisan.getId(), selectedRow, 6);

                        ((DefaultTableModel) table.getModel()).setValueAt(selectedCalisan.getAd() + ' ' +selectedCalisan.getSoyad(), selectedRow, 7);
                        
                        dialog.dispose();
                

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

        panel.add(kaydetButton);
        dialog.add(panel);

        
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblGorevler = new javax.swing.JTable();
        lblGorev = new javax.swing.JLabel();
        txtAd = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBasTar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAdamGun = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBitTar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnEkle = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 422));

        tblGorevler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Ad", "Başlangıç Tarihi", "Adam Gün Değeri", "Bitiş Tarihi", "Durumu", "Çalışan Id", "Çalışan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblGorevler);
        if (tblGorevler.getColumnModel().getColumnCount() > 0) {
            tblGorevler.getColumnModel().getColumn(0).setResizable(false);
            tblGorevler.getColumnModel().getColumn(1).setResizable(false);
            tblGorevler.getColumnModel().getColumn(2).setResizable(false);
            tblGorevler.getColumnModel().getColumn(3).setResizable(false);
            tblGorevler.getColumnModel().getColumn(4).setResizable(false);
            tblGorevler.getColumnModel().getColumn(5).setResizable(false);
            tblGorevler.getColumnModel().getColumn(6).setResizable(false);
            tblGorevler.getColumnModel().getColumn(7).setResizable(false);
        }

        lblGorev.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblGorev.setText("Görevler");

        jLabel2.setText("BaşlangıçTarihi");

        txtBasTar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBasTarActionPerformed(evt);
            }
        });

        jLabel3.setText("Adam Gün Değeri");

        jLabel4.setText("Bitiş Tarihi");

        jLabel1.setText("Ad");

        btnEkle.setText("Ekle");
        btnEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEkle)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtBasTar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtBitTar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAdamGun, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(300, 300, 300)
                            .addComponent(lblGorev, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGorev)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtBasTar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtBitTar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtAdamGun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addComponent(btnEkle)
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBasTarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBasTarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBasTarActionPerformed

    private void btnEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEkleActionPerformed
        Connection connection=null;
        DbHelper dbHelper=new DbHelper();
        PreparedStatement statement =null;
        try{
            connection = dbHelper.getConnection();
            String sql = "insert into gorev (gorAd,gorBasTar,gorBitTar,gorAdamGun,prjId) values(?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
        
            statement.setString(1, txtAd.getText());
            
    
            statement.setString(2, txtBasTar.getText());
            statement.setString(3, txtBitTar.getText());
            statement.setInt(4, Integer.parseInt(txtAdamGun.getText()));
            statement.setInt(5, projeId);
            
            int result = statement.executeUpdate();
            txtAd.setText("");
            txtBasTar.setText("");
            txtAdamGun.setText("");
            txtBitTar.setText("");
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
                                          
    }//GEN-LAST:event_btnEkleActionPerformed

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
            java.util.logging.Logger.getLogger(GorevTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GorevTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GorevTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GorevTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GorevTable(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEkle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGorev;
    private javax.swing.JTable tblGorevler;
    private javax.swing.JTextField txtAd;
    private javax.swing.JTextField txtAdamGun;
    private javax.swing.JTextField txtBasTar;
    private javax.swing.JTextField txtBitTar;
    // End of variables declaration//GEN-END:variables
}
