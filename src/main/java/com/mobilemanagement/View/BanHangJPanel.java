/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mobilemanagement.View;

import com.mobilemanagement.Dao.DanhMucSPDao;
import com.mobilemanagement.Dao.DonHangCTDAO;
import com.mobilemanagement.Dao.DonHangDAO;
import com.mobilemanagement.Dao.GiamGiaDAO;
import com.mobilemanagement.Dao.IEMISPDao;
import com.mobilemanagement.Dao.NhanVienDAO;
import com.mobilemanagement.Dao.SanPhamCTDao;
import com.mobilemanagement.Dao.SanPhamDao;
import com.mobilemanagement.Model.DonHang;
import com.mobilemanagement.Model.DonHangCT;
import com.mobilemanagement.Model.GiamGia;
import com.mobilemanagement.Model.SanPhamCT;
import com.mobilemanagement.Model.TaiKhoanCT;
import static com.mobilemanagement.Model.Test.getDate;
import com.mobilemanagement.Model.UserLogin;
import com.mobilemanagement.Utility.Auth;
import com.mobilemanagement.Utility.MsgBox;
import com.mobilemanagement.Utility.XDate;
import com.mobilemanagement.Utility.XValidate;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DINHVU
 */
public class BanHangJPanel extends javax.swing.JPanel {

    SanPhamDao spDao = new SanPhamDao();
    SanPhamCTDao spctDao = new SanPhamCTDao();
    IEMISPDao iemiDao = new IEMISPDao();
    DanhMucSPDao dmDao = new DanhMucSPDao();
    DonHangDAO dhDao = new DonHangDAO();
    DonHangCTDAO dhctdao = new DonHangCTDAO();
    int row = 0;
    List<Object[]> data = new ArrayList<>();

    UserLogin user = new UserLogin();
    NhanVienDAO nvDao = new NhanVienDAO();
    TaiKhoanCT taiKhoanCT = new TaiKhoanCT();
    GiamGiaDAO salesDao = new GiamGiaDAO();
    private static int stt = 1;

    public BanHangJPanel() {

        initComponents();
        data = spDao.SelectTableSP();
        loadTable(data);
        DefaultTableCellRenderer headerCellRenderer = new DefaultTableCellRenderer();
        headerCellRenderer.setBackground(new Color(192, 227, 149));
        for (int i = 0; i < tblSanPhamBH.getModel().getColumnCount(); ++i) {
            tblSanPhamBH.getColumnModel().getColumn(i).setHeaderRenderer(headerCellRenderer);
        }
        setForm();
    }

    public void setThongTinKhachHang(String tenKhachHang, String maKhachHang) {

        System.out.println("form BanHang " + tenKhachHang + " - " + maKhachHang);
    }

    void moFormKhachHang() {
        khachHangDH khachHangForm = new khachHangDH((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true);
        khachHangForm.setVisible(true);

        // Lấy dữ liệu từ form con
        Object[] dataKhachHang = khachHangForm.layDuLieuKhachHang();

        if (dataKhachHang != null) {
            // Hiển thị dữ liệu trên form cha
            String tenKhachHang = dataKhachHang[1].toString();
            String maKhachHang = dataKhachHang[0].toString();
            txtTenKH.setText(tenKhachHang);
            lblMaKH.setText(maKhachHang);

        }
    }

    void loadTable(List<Object[]> data) {

        DefaultTableModel model = (DefaultTableModel) tblSanPhamBH.getModel();
        model.setRowCount(0);

        try {
            List<Object[]> list = data;

            for (Object[] row : list) {

                Object[] rows = {
                    row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], XValidate.formatCurrency(row[8])};
                model.addRow(rows);
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Integer getMaSPCT() {
        int selectedRow = tblSanPhamBH.getSelectedRow();

        if (selectedRow != -1) {
            // Lấy giá trị từ JTable
            String Ram = tblSanPhamBH.getValueAt(selectedRow, 3).toString();
            String Rom = tblSanPhamBH.getValueAt(selectedRow, 4).toString();
            String MauSac = tblSanPhamBH.getValueAt(selectedRow, 6).toString();
            String giaBanStr = tblSanPhamBH.getValueAt(selectedRow, 8).toString().replace(",", "");

            // Chuyển đổi chuỗi thành số
            double GiaBan = Double.parseDouble(giaBanStr);

            System.out.println("SPCT: Ram=" + Ram + ", Rom=" + Rom + ", MauSac=" + MauSac + ", GiaBan=" + GiaBan);

            // Sử dụng spctDao để lấy MaSPCT dựa trên các điều kiện
            SanPhamCT spct = spctDao.selectMaSPCTByConditions(MauSac, GiaBan, Ram, Rom);

            if (spct != null) {
                // In ra để kiểm tra giá trị của MaSPCT
                System.out.println("MaSPCT getMaSPCT=" + spct.getMaSPCT());

                // Trả về MaSPCT nếu tìm thấy
                return spct.getMaSPCT();
            }
        }

        // Trả về -1 nếu không tìm thấy
        return -1;
    }

    String getTenNV() {
        String tenNV = Auth.user.getMaID();
        taiKhoanCT = nvDao.selectById(tenNV);
        if (taiKhoanCT != null) {
            System.out.println("Ten NV : " + taiKhoanCT.getHoTen());
            return taiKhoanCT.getHoTen();
        }
        return "Khong tim thay";
    }

    void setForm() {
        String MaHD = HDSoNgauNhien(); // Gọi hàm để tạo mã hóa đơn
        lblMaHD.setText(MaHD); // Hiển thị mã hóa đơn trên JLabel
        lbltenNV.setText(getTenNV());
        txtGiamGia.setText(null);
    }

    void tinhTien() {
        try {
            double tienKhachDua = 0;
            String tienKhachDuaStr = txtTienKhach.getText().trim();
            if (!tienKhachDuaStr.isEmpty()) {
                tienKhachDua = Double.parseDouble(tienKhachDuaStr.replace(",", ""));
            }

            double tienThanhToan = Double.parseDouble(lblThanhToan.getText().replace(",", ""));

            double tienTraKhach = tienKhachDua - tienThanhToan;

            lblTienThua.setText(XValidate.formatCurrency(tienTraKhach));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
//    
//    void tinhTongTien() {
//    int selectedRow = tblDonHang.getSelectedRow();
//    DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
//    double tongTien = 0;
//
//    for (int i = 0; i < model.getRowCount(); i++) {
//        double thanhTien = Double.parseDouble(model.getValueAt(i, 4).toString());
//        tongTien += thanhTien;
//    }
//
//    lblTongTien.setText(XValidate.formatCurrency(tongTien));
//
//    double giamGia = 0;
//    String giamGiaStr = lblGiamGia.getText().trim();
//    if (!giamGiaStr.isEmpty()) {
//        giamGia = Double.parseDouble(giamGiaStr.replace(",", ""));
//    }
//    double khachDua = Double.parseDouble(txtTienKhach.getText());
//    double thanhToan = tongTien - giamGia;
//    lblThanhToan.setText(XValidate.formatCurrency(thanhToan));
//    double tienGuiLaiKhach = khachDua - thanhToan;
//    lblTienThua.setText(XValidate.formatCurrency(tienGuiLaiKhach));
//
//    tinhTien();
//}

    private String HDSoNgauNhien() {
        // Sử dụng đối tượng Random để tạo số ngẫu nhiên
        Random random = new Random();

        // Tạo số ngẫu nhiên có 8 chữ số
        int soNgauNhien = random.nextInt(90000000) + 10000000;

        // Ghép với tiền tố "HD"
        return "HD" + soNgauNhien;
    }

    // Trong class BanHangJPanel.java
    private void showIEMIDialog(int selectedRow) {
        Object[] rowData = getRowData(selectedRow);

        int maSPCT = getMaSPCT();
        IEMILDH selectIEMIForm = new IEMILDH((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true, maSPCT);

        // Thiết lập tham chiếu đến BanHangJPanel trong IEMILDH
        selectIEMIForm.setBanHangJPanel(this);

        selectIEMIForm.setVisible(true);
    }

    private Object[] getRowData(int selectedRow) {
        DefaultTableModel model = (DefaultTableModel) tblSanPhamBH.getModel();
        Object[] rowData = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            rowData[i] = model.getValueAt(selectedRow, i);
        }
        return rowData;
    }

    public void capNhatTblDonHang(String cot2, String cot3) {
        // Cập nhật tblDonHang với dữ liệu được cung cấp
        int selectedRow = tblSanPhamBH.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        if (!kiemTraTrungIEMI(cot2)) {
            String Ram = tblSanPhamBH.getValueAt(selectedRow, 3).toString();
            String Rom = tblSanPhamBH.getValueAt(selectedRow, 4).toString();
            String MauSac = tblSanPhamBH.getValueAt(selectedRow, 6).toString();
            String MaSP = tblSanPhamBH.getValueAt(selectedRow, 0).toString();
            String tenSP = tblSanPhamBH.getValueAt(selectedRow, 1).toString();
            String thongTin = String.join(" - ", tenSP, Rom, Ram, MauSac);
            String giaBanStr = tblSanPhamBH.getValueAt(selectedRow, 8).toString().replace(",", "");

            // Chuyển đổi chuỗi thành số
            double GiaBan = Double.parseDouble(giaBanStr);
            Object[] rowData = {getMaSPCT(), MaSP, cot2, thongTin, GiaBan};
            model.addRow(rowData);
            tinhTongTien();

        } else {
            MsgBox.alert(this, "Trùng IEMI tại đơn hàng !");
        }

    }

    private boolean kiemTraTrungIEMI(String IEMI) {
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingIEMI = tblDonHang.getValueAt(i, 2).toString();
            if (IEMI.equals(existingIEMI)) {
                // Nếu IEMI đã tồn tại, trả về true
                return true;
            }
        }
        // Nếu IEMI không tồn tại, trả về false
        return false;
    }

    void tinhTongTien() {
        int selectedRow = tblDonHang.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        double tongTien = 0;

        double khachDua = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            // Lấy giá trị từ cột "Thành Tiền"
            double thanhTien = Double.parseDouble(model.getValueAt(i, 4).toString());
            tongTien += thanhTien;
        }

        // Hiển thị tổng tiền
        lblTongTien.setText(XValidate.formatCurrency(tongTien));

        double giamGia = 0;
        String giamGiaStr = lblGiamGia.getText().trim();
        if (!giamGiaStr.isEmpty()) {
            giamGia = Double.parseDouble(giamGiaStr.replace(",", ""));
        }

        double thanhToan = tongTien - giamGia;
        lblThanhToan.setText(XValidate.formatCurrency(thanhToan));
        double tienGuiLaiKhach = khachDua - thanhToan;
        lblTienThua.setText(XValidate.formatCurrency(tienGuiLaiKhach));
        tinhTien();
    }

    void getMaGiamGia() {
        String maGG = txtGiamGia.getText();
        if (!maGG.isEmpty()) {
            GiamGia giamGia = salesDao.selectById(maGG);

            if (giamGia != null) {
                lblGiamGia.setText(XValidate.formatCurrency(giamGia.getGiatri()));
            } else {
                lblGiamGia.setText(XValidate.formatCurrency(0));
            }
        }
        tinhTongTien();
    }

    public static Date getDate() {
        String date_s = XDate.getCurrentDateFormat();

        // Sử dụng mẫu "dd/MM/yyyy HH:mm:ss"
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date date = dt.parse(date_s);
            return date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            // Xử lý nếu có lỗi khi chuyển đổi
            return null;
        }
    }

    DonHang getForm() {
        DonHang dh = new DonHang();
        Date date = getDate();
        dh.setMaDH(lblMaHD.getText());

        if (date != null) {
            dh.setNgayLap(date);
            System.out.println("NgayLap: " + date);
        } else {
            System.out.println("Lỗi thời gian !");
        }
        dh.setMaKH(lblMaKH.getText());
        dh.setMaNV(Auth.user.getMaID());
        
        String maGG = txtGiamGia.getText();
    if (maGG == null || maGG.trim().isEmpty()) {
        maGG = ""; // Set it to an empty string
    }
    dh.setMaGG(maGG);
        dh.setGhiChu(txtghiChu.getText());
        return dh;
    }

    DonHangCT getFormDHCT(int rowIndex) {
        DonHangCT dhct = new DonHangCT();
        dhct.setMaDH(lblMaHD.getText());

        dhct.setMaSP(tblDonHang.getValueAt(rowIndex, 1).toString());
        dhct.setMaSPCT((int) tblDonHang.getValueAt(rowIndex, 0));
        dhct.setIEMI(tblDonHang.getValueAt(rowIndex, 2).toString());

        String giaBanStr = tblDonHang.getValueAt(rowIndex, 4).toString().replace(",", "");
        double giaBan = Double.parseDouble(giaBanStr);
        dhct.setDonGia(giaBan);

        return dhct;
    }

    void insert() {
        if (getForm() == null || tblDonHang.getRowCount() == 0) {
            MsgBox.alert(this, "Dữ liệu đơn hàng rỗng");
            return;
        }

        DonHang dh = getForm();
        try {
            dhDao.insert(dh); // Insert thông tin đơn hàng

            for (int i = 0; i < tblDonHang.getRowCount(); i++) {
                DonHangCT dhct = getFormDHCT(i);
                try {
                    dhctdao.insert(dhct); // Insert thông tin đơn hàng chi tiết từng dòng
                    try {
                        iemiDao.updateTrangThai(dhct.getIEMI());
                        loadTable(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgBox.alert(this, "Lỗi IEMI");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Lỗi ở DHCT - Dòng " + (i + 1));
                    // Nếu có lỗi ở dòng i, có thể quyết định rollback hoặc xử lý tùy thuộc vào yêu cầu
                }
            }

            MsgBox.alert(this, "Thêm đơn hàng thành công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm đơn hàng thất bại !");
        }
    }

    void clearForm() {
        double tien = 0;
        DonHang dh = new DonHang();
        DonHangCT dhct = new DonHangCT();
        lblMaHD.setText(HDSoNgauNhien());
        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
        lblTongTien.setText(XValidate.formatCurrency(tien));
        lblGiamGia.setText(XValidate.formatCurrency(tien));
        lblThanhToan.setText(XValidate.formatCurrency(tien));
        lblTienThua.setText(XValidate.formatCurrency(tien));
        txtGiamGia.setText(null);
        txtTienKhach.setText(XValidate.formatCurrency(tien));
        lblMaKH.setText("");
        txtTenKH.setText("");
        txtghiChu.setText("");

// Xóa tất cả các hàng trong table
        model.setRowCount(0);
    }

    void timKiemTenSP() {
        String tenSP = txtTimKiem.getText();

        // Gọi hàm tìm kiếm từ DAO hoặc Service
        List<Object[]> List = spDao.searchProductByName(tenSP);

        // Xóa dữ liệu hiện tại của bảng
        DefaultTableModel model = (DefaultTableModel) tblSanPhamBH.getModel();
        model.setRowCount(0);

        // Thêm dữ liệu mới từ kết quả tìm kiếm
        for (Object[] row : List) {
            model.addRow(row);
        }
    }

    void xoadulieuDonhang() {
        int selectedRow = tblDonHang.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
            model.removeRow(selectedRow);

        }
    }

//    public void xoaDongTrenTblIEMI(int selectedRow) {
//        DefaultTableModel model = (DefaultTableModel) tblDonHang.getModel();
//        model.removeRow(selectedRow);
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPhamBH = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDonHang = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lblTongTien = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lbltenNV = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtghiChu = new javax.swing.JTextArea();
        txtTienKhach = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        lblMaKH = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1178, 780));
        setPreferredSize(new java.awt.Dimension(1178, 780));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1178, 780));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tìm kiếm :");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblSanPhamBH.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(28, 35, 65)));
        tblSanPhamBH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblSanPhamBH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Loại", "Ram", "Rom", "Pin", "MauSac", "Sl", "Giá Bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPhamBH.setRowHeight(30);
        tblSanPhamBH.setSelectionBackground(new java.awt.Color(28, 35, 65));
        tblSanPhamBH.setShowGrid(false);
        tblSanPhamBH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamBHMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamBHMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPhamBH);
        if (tblSanPhamBH.getColumnModel().getColumnCount() > 0) {
            tblSanPhamBH.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblSanPhamBH.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblSanPhamBH.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblSanPhamBH.getColumnModel().getColumn(4).setPreferredWidth(30);
            tblSanPhamBH.getColumnModel().getColumn(7).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SPCT", "Mã Sản Phẩm", "IMEI", "Thông Tin Sản Phẩm", "Giá Bán "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDonHang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblDonHang.setRowHeight(30);
        tblDonHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDonHangKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblDonHang);
        if (tblDonHang.getColumnModel().getColumnCount() > 0) {
            tblDonHang.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblDonHang.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        jButton4.setText("Xóa ");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn Hàng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(204, 0, 51))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Mã Hóa Đơn");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nhân Viên:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Khách Hàng:");

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTenKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Tổng tiền : ");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 51));
        jLabel10.setText("Giảm giá:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 51));
        jLabel11.setText("Thanh Toán :");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setText("Tiền Thừa:");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 51));
        jButton1.setText("Thanh Toán");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 255));
        jButton2.setText("Hủy");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        lblTongTien.setBackground(new java.awt.Color(255, 51, 51));
        lblTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 0, 51));

        lblThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThanhToan.setForeground(new java.awt.Color(255, 0, 51));

        lblGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblGiamGia.setForeground(new java.awt.Color(255, 0, 51));

        lblTienThua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTienThua.setForeground(new java.awt.Color(51, 51, 255));

        lblMaHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaHD.setForeground(new java.awt.Color(51, 51, 255));
        lblMaHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        lbltenNV.setBackground(new java.awt.Color(255, 255, 255));
        lbltenNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltenNV.setForeground(new java.awt.Color(0, 0, 204));
        lbltenNV.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 204));
        jLabel16.setText("Tiền khách đưa:");

        txtGiamGia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtGiamGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiamGiaKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Mã Giảm giá:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Ghi Chú :");

        txtghiChu.setColumns(20);
        txtghiChu.setRows(5);
        jScrollPane3.setViewportView(txtghiChu);

        txtTienKhach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTienKhach.setForeground(new java.awt.Color(0, 0, 255));
        txtTienKhach.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));
        txtTienKhach.setToolTipText("Nhập số tiền khách đưa ");
        txtTienKhach.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTienKhach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Mã KH:");

        lblMaKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaKH.setForeground(new java.awt.Color(0, 0, 255));

        jButton3.setBackground(new java.awt.Color(28, 35, 65));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-plus-50.png"))); // NOI18N
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbltenNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblMaKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(37, 37, 37)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblGiamGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTienThua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(59, 59, 59))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTienKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        timKiemTenSP();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblSanPhamBHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamBHMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int selectedRow = tblSanPhamBH.getSelectedRow();
            getMaSPCT();
            System.out.println(selectedRow);
            if (selectedRow != -1) {
                showIEMIDialog(selectedRow);

            }
        }

    }//GEN-LAST:event_tblSanPhamBHMouseClicked

    private void tblSanPhamBHMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamBHMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamBHMousePressed

    private void txtTienKhachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachKeyReleased
        // TODO add your handling code here:
        tinhTien();
    }//GEN-LAST:event_txtTienKhachKeyReleased

    private void txtGiamGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiamGiaKeyReleased
        // TODO add your handling code here:
        getMaGiamGia();
    }//GEN-LAST:event_txtGiamGiaKeyReleased

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        moFormKhachHang();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        String tienThuaStr = lblTienThua.getText();
        double tienTraKhach = Double.parseDouble(tienThuaStr.replace(",", ""));

        if (tienTraKhach >= 0) {
            if (MsgBox.confirm(this, "Xác nhận thanh toán ?")) {
                insert();
            }
        } else {
            MsgBox.alert(this, "Số tiền trả khách phải >= 0 mới được thanh toán ");
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        if (MsgBox.confirm(this, "Bạn muốn hủy đơn hàng ?")) {
            clearForm();
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void tblDonHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDonHangKeyReleased
        // TODO add your handling code here:
        tinhTongTien();

    }//GEN-LAST:event_tblDonHangKeyReleased

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        xoadulieuDonhang();
        tinhTongTien();
    }//GEN-LAST:event_jButton4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lbltenNV;
    private javax.swing.JTable tblDonHang;
    private javax.swing.JTable tblSanPhamBH;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JFormattedTextField txtTienKhach;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextArea txtghiChu;
    // End of variables declaration//GEN-END:variables
}
