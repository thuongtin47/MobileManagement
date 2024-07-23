/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mobilemanagement.View;

import com.mobilemanagement.Dao.IEMISPDao;
import com.mobilemanagement.Model.IEMISP;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import com.mobilemanagement.View.IEMILDH;
import java.util.ArrayList;

/**
 *
 * @author DINHVU
 */
public class IEMILDH extends javax.swing.JDialog {

    private BanHangJPanel banHangJPanel;
    IEMISPDao iemiDao = new IEMISPDao();
    private int maSPCT;
    private String maSP;
    private boolean formClosed = false;
    private Object[] selectedIEMIData;

    public IEMILDH(java.awt.Frame parent, boolean modal, int maSPCT) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.maSPCT = maSPCT;
        iemiDao = new IEMISPDao(); // Khởi tạo đối tượng iemiDao
        // Load dữ liệu từ MaSPCT và đổ vào bảng
        loadDataToTable();
    }

    public javax.swing.JTable getTblIEMI() {
        return tblIEMI;
    }

    public void setBanHangJPanel(BanHangJPanel banHangJPanel) {
        this.banHangJPanel = banHangJPanel;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    private void loadDataToTable() {
        List<IEMISP> iemiList = iemiDao.selectByMaSPCT(maSPCT);

        DefaultTableModel model = (DefaultTableModel) tblIEMI.getModel();
        model.setRowCount(0);

        int stt = 1; // Thêm biến stt để đánh số thứ tự
        for (IEMISP iemi : iemiList) {
            Object[] rowData = {
                stt++,
                iemi.getIMEI(),
                iemi.getXuatXu(), // Thêm các trường dữ liệu khác tùy thuộc vào cấu trúc bảng
            };
            model.addRow(rowData);
        }
    }

    void getDuLieu() {
        int row = tblIEMI.getSelectedRow();

        String cot2 = tblIEMI.getValueAt(row, 1).toString();
        String cot3 = tblIEMI.getValueAt(row, 2).toString();

        // Truyền dữ liệu về form BanHangJPanel
        if (banHangJPanel != null) {
            banHangJPanel.capNhatTblDonHang(cot2, cot3);
            // Gọi phương thức xóa dòng đã chọn trong tblIEMI
            DefaultTableModel model = (DefaultTableModel) tblIEMI.getModel();
            model.removeRow(row);
        }
    }

    void timKiemIEMI() {
        String iemi = txtIEMI.getText();

        // Gọi hàm tìm kiếm từ DAO hoặc Service
        List<IEMISP> listiemi = iemiDao.selectByIEMI(iemi);

        // Xóa dữ liệu hiện tại của bảng
        DefaultTableModel model = (DefaultTableModel) tblIEMI.getModel();
        model.setRowCount(0);
        int stt = 1;
        // Thêm dữ liệu mới từ kết quả tìm kiếm
        for (IEMISP iemi1 : listiemi) {
            Object[] row = {
                stt++,
                iemi1.getIMEI(),
                iemi1.getXuatXu()

            };
            model.addRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIEMI = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIEMI = new javax.swing.JTable();
        btncloseForm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Tìm Kiếm :");

        txtIEMI.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtIEMI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIEMIKeyReleased(evt);
            }
        });

        tblIEMI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblIEMI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "IEMI", "Xuất Sứ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblIEMI.setRowHeight(30);
        tblIEMI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIEMIMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblIEMI);
        if (tblIEMI.getColumnModel().getColumnCount() > 0) {
            tblIEMI.getColumnModel().getColumn(0).setResizable(false);
            tblIEMI.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblIEMI.getColumnModel().getColumn(1).setResizable(false);
            tblIEMI.getColumnModel().getColumn(2).setResizable(false);
        }

        btncloseForm.setText("Hủy");
        btncloseForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseFormActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIEMI, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(btncloseForm)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIEMI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncloseForm, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblIEMIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIEMIMouseClicked
        getDuLieu();


    }//GEN-LAST:event_tblIEMIMouseClicked

    private void btncloseFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseFormActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btncloseFormActionPerformed

    private void txtIEMIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIEMIKeyReleased
        // TODO add your handling code here:
        timKiemIEMI();
    }//GEN-LAST:event_txtIEMIKeyReleased

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
            java.util.logging.Logger.getLogger(IEMILDH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IEMILDH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IEMILDH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IEMILDH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int maSPCT = 123; // Giả sử giá trị cụ thể, bạn thay thế bằng giá trị thực của mình
                IEMILDH dialog = new IEMILDH(new javax.swing.JFrame(), true, maSPCT);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncloseForm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblIEMI;
    private javax.swing.JTextField txtIEMI;
    // End of variables declaration//GEN-END:variables
}
