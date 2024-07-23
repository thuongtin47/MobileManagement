/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mobilemanagement.View;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import com.mobilemanagement.Utility.Auth;
import com.mobilemanagement.Utility.MsgBox;
import com.mobilemanagement.Utility.XDate;
import com.mobilemanagement.Utility.XImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author DINHVU
 */
public class MainJFram extends javax.swing.JFrame {

    /**
     * Creates new form MainJFram
     */
    public MainJFram() {
        initComponents();

        init();
        OpenHome();
    }

    void init() {
        setIconImage(XImage.getApImage());
        setLocationRelativeTo(null);
        setTitle("PHẦN MỀM QUẢN LÝ CỬA HÀNG ĐIỆN THOẠI TVT");

        new Timer(100, new ActionListener() {
//            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
//                lblTime.setText(format.format(new Date()));
                lblDate.setText(XDate.getCurrentDateFormat());

            }
        }).start();
//        this.showIntroAndLogin();
//        this.openLoginDialog();
    }

//    private void InitContent() {
//        ShowJPanel(new TrangChuJPanel());
//    }
    void showIntroAndLogin() {
        new IntroJdialog(this, true).setVisible(true);

        // Sau khi IntroJDialog đóng, hiển thị LoginFrom
//        if (!introDialog.isVisible()) {
//            introDialog.dispose();  // Đóng IntroJDialog
//            openLoginDialog();
//        }
    }

    private void openLoginDialog() {
        new Login(this, true).setVisible(true);
    }

    public static void ShowJPanel(JPanel p) {
        p.setSize(1178, 780);
        p.setLocation(0, 0);

        content.removeAll();
        content.add(p, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }

    void DangXuat() {
        if (MsgBox.confirm(this, "Bạn muốn thoát khỏi phần mềm ?")) {
            Auth.clear();
            this.dispose();
            openLoginDialog();
        }
    }

    void OpenKhachHang() {
        ShowJPanel(new khachHangJPanel());
        txtFrom.setText("Quản Lý Khách Hàng");
    }

    void OpenHome() {
        ShowJPanel(new TrangChuJPanel());
        txtFrom.setText("Trang Chủ");
    }

    void OpenSanPham() {
        ShowJPanel(new SanPhamJPanel());
        txtFrom.setText("Quản Lý Sản Phẩm");
    }

    void OpenDonHang() {
        ShowJPanel(new DonHangJPanel());
        txtFrom.setText("Quản Lý Đơn Hàng");
    }

    void OpenGiamGia() {
        ShowJPanel(new GiamGiaJPanel());
        txtFrom.setText("Quản Lý Giảm Giá");
    }

    void OpenTaiKhoan() {
        ShowJPanel(new TaikhoanJPanel());
        txtFrom.setText("Quản Lý Tài Khoản");
    }

    void OpenThongKe() {
        ShowJPanel(new ThongKeJPanel());
        txtFrom.setText("Quản Lý Thống Kê");
    }

    void OpenBanHang() {
        ShowJPanel(new BanHangJPanel());
        txtFrom.setText("Quản Lý Bán Hàng");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Container = new javax.swing.JLayeredPane();
        PnMenu = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnGiamGia = new javax.swing.JButton();
        btnTrangChu = new javax.swing.JButton();
        btnSanPham = new javax.swing.JButton();
        btnBanHang = new javax.swing.JButton();
        btnDonHang = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnbanHang = new javax.swing.JButton();
        PnHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        txtFrom = new javax.swing.JLabel();
        content = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PnMenu.setBackground(new java.awt.Color(28, 35, 65));
        PnMenu.setForeground(new java.awt.Color(255, 255, 255));
        PnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PnMenuMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PnMenuMousePressed(evt);
            }
        });

        lblLogo.setBackground(new java.awt.Color(28, 35, 65));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/logo-100.png"))); // NOI18N

        btnGiamGia.setBackground(new java.awt.Color(28, 35, 65));
        btnGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGiamGia.setForeground(new java.awt.Color(255, 255, 255));
        btnGiamGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-discounts-66.png"))); // NOI18N
        btnGiamGia.setText("Giảm Giá");
        btnGiamGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnGiamGia.setBorderPainted(false);
        btnGiamGia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGiamGia.setIconTextGap(30);
        btnGiamGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiamGiaActionPerformed(evt);
            }
        });

        btnTrangChu.setBackground(new java.awt.Color(28, 35, 65));
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-home-32.png"))); // NOI18N
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnTrangChu.setBorderPainted(false);
        btnTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTrangChu.setIconTextGap(30);
        btnTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnTrangChuMouseReleased(evt);
            }
        });
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });

        btnSanPham.setBackground(new java.awt.Color(28, 35, 65));
        btnSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-box-64.png"))); // NOI18N
        btnSanPham.setText("Sản Phẩm");
        btnSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnSanPham.setBorderPainted(false);
        btnSanPham.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSanPham.setIconTextGap(30);
        btnSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSanPhamActionPerformed(evt);
            }
        });

        btnBanHang.setBackground(new java.awt.Color(28, 35, 65));
        btnBanHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-shutdown-48.png"))); // NOI18N
        btnBanHang.setText("Đăng Xuất");
        btnBanHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnBanHang.setBorderPainted(false);
        btnBanHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBanHang.setIconTextGap(30);
        btnBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanHangActionPerformed(evt);
            }
        });

        btnDonHang.setBackground(new java.awt.Color(28, 35, 65));
        btnDonHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDonHang.setForeground(new java.awt.Color(255, 255, 255));
        btnDonHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-bill-80.png"))); // NOI18N
        btnDonHang.setText("Đơn Hàng");
        btnDonHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnDonHang.setBorderPainted(false);
        btnDonHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDonHang.setIconTextGap(30);
        btnDonHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDonHangActionPerformed(evt);
            }
        });

        btnTaiKhoan.setBackground(new java.awt.Color(28, 35, 65));
        btnTaiKhoan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-person-48.png"))); // NOI18N
        btnTaiKhoan.setText("Tài Khoản");
        btnTaiKhoan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnTaiKhoan.setBorderPainted(false);
        btnTaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTaiKhoan.setIconTextGap(30);
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        btnKhachHang.setBackground(new java.awt.Color(28, 35, 65));
        btnKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-persons-32.png"))); // NOI18N
        btnKhachHang.setText("Khách Hàng");
        btnKhachHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnKhachHang.setBorderPainted(false);
        btnKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnKhachHang.setIconTextGap(30);
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(28, 35, 65));
        btnThongKe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-statistics-64.png"))); // NOI18N
        btnThongKe.setText("Thống Kê");
        btnThongKe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnThongKe.setBorderPainted(false);
        btnThongKe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThongKe.setIconTextGap(30);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnbanHang.setBackground(new java.awt.Color(28, 35, 65));
        btnbanHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnbanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnbanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-cart-50_1.png"))); // NOI18N
        btnbanHang.setText("Bán Hàng");
        btnbanHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 60, 0, 0, new java.awt.Color(0, 0, 0)));
        btnbanHang.setBorderPainted(false);
        btnbanHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnbanHang.setIconTextGap(30);
        btnbanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbanHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnMenuLayout = new javax.swing.GroupLayout(PnMenu);
        PnMenu.setLayout(PnMenuLayout);
        PnMenuLayout.setHorizontalGroup(
            PnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
            .addComponent(btnTrangChu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBanHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDonHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnbanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PnMenuLayout.setVerticalGroup(
            PnMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnMenuLayout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnbanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PnHeader.setBackground(new java.awt.Color(28, 35, 65));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDate.setText("DATE");
        lblDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtFrom.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtFrom.setForeground(new java.awt.Color(255, 255, 255));
        txtFrom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout PnHeaderLayout = new javax.swing.GroupLayout(PnHeader);
        PnHeader.setLayout(PnHeaderLayout);
        PnHeaderLayout.setHorizontalGroup(
            PnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnHeaderLayout.createSequentialGroup()
                .addGap(375, 375, 375)
                .addComponent(txtFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)))
        );
        PnHeaderLayout.setVerticalGroup(
            PnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnHeaderLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PnHeaderLayout.createSequentialGroup()
                        .addComponent(txtFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setLayout(new java.awt.BorderLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(1178, 780));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1178, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );

        content.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        Container.setLayer(PnMenu, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Container.setLayer(PnHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Container.setLayer(content, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addComponent(PnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PnHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addComponent(PnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanHangActionPerformed
        // TODO add your handling code here:

        DangXuat();
    }//GEN-LAST:event_btnBanHangActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        // TODO add your handling code here:
        OpenKhachHang();
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        // TODO add your handling code here:
        OpenHome();
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void btnSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSanPhamActionPerformed
        // TODO add your handling code here:
        OpenSanPham();
    }//GEN-LAST:event_btnSanPhamActionPerformed

    private void btnDonHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDonHangActionPerformed
        // TODO add your handling code here:
        OpenDonHang();
    }//GEN-LAST:event_btnDonHangActionPerformed

    private void btnGiamGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiamGiaActionPerformed
        // TODO add your handling code here:
        OpenGiamGia();
    }//GEN-LAST:event_btnGiamGiaActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        // TODO add your handling code here:
        OpenTaiKhoan();
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        // TODO add your handling code here:
        OpenThongKe();
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void PnMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnMenuMouseEntered
        // TODO add your handling code here:


    }//GEN-LAST:event_PnMenuMouseEntered

    private void PnMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PnMenuMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_PnMenuMousePressed

    private void btnTrangChuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrangChuMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_btnTrangChuMouseReleased

    private void btnbanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbanHangActionPerformed
        // TODO add your handling code here:
        OpenBanHang();
    }//GEN-LAST:event_btnbanHangActionPerformed

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
            java.util.logging.Logger.getLogger(MainJFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        // FlatMaterialLighterIJTheme.setup();
        //FlatArcOrangeIJTheme.setup();
        FlatCyanLightIJTheme.setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainJFram().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane Container;
    private javax.swing.JPanel PnHeader;
    private javax.swing.JPanel PnMenu;
    private javax.swing.JButton btnBanHang;
    private javax.swing.JButton btnDonHang;
    private javax.swing.JButton btnGiamGia;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnSanPham;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnbanHang;
    private static javax.swing.JPanel content;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel txtFrom;
    // End of variables declaration//GEN-END:variables
}
