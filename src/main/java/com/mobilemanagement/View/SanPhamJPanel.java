/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mobilemanagement.View;

import com.mobilemanagement.Dao.DanhMucSPDao;
import com.mobilemanagement.Dao.IEMISPDao;
import com.mobilemanagement.Dao.SanPhamCTDao;
import com.mobilemanagement.Dao.SanPhamDao;
import com.mobilemanagement.Model.DanhMucSP;
import com.mobilemanagement.Model.IEMISP;
import com.mobilemanagement.Model.SanPham;
import com.mobilemanagement.Model.SanPhamCT;
import com.mobilemanagement.Utility.Auth;
import com.mobilemanagement.Utility.MsgBox;
import com.mobilemanagement.Utility.XFile;
import com.mobilemanagement.Utility.XImage;
import com.mobilemanagement.Utility.XValidate;
import java.awt.Color;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DINHVU
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    JFileChooser fileChooser = new JFileChooser();
    SanPhamDao spDao = new SanPhamDao();
    SanPhamCTDao spctDao = new SanPhamCTDao();
    IEMISPDao iemiDao = new IEMISPDao();
    DanhMucSPDao dmDao = new DanhMucSPDao();
    int row = 0;
    List<Object[]> data = new ArrayList<>();
    List<Object[]> datatable = new ArrayList<>();
    List<DanhMucSP> danhMucList = dmDao.selectAll();
    DefaultTableCellRenderer tblCenter = new DefaultTableCellRenderer();

    /**
     * Creates new form SanPhamJPanel
     */
    public SanPhamJPanel() {
        initComponents();
        // fillTable();

        init();
    }

    void init() {
        fillComboBoxes();

        data = spDao.SelectTableSP();
        loadTable(data);

        datatable = spDao.SelectTableDSSP();
        LoadTable2(datatable);
        DefaultTableCellRenderer headerCellRenderer = new DefaultTableCellRenderer();
        headerCellRenderer.setBackground(new Color(192, 227, 149));
        for (int i = 0; i < tblSanPham.getModel().getColumnCount(); ++i) {
            tblSanPham.getColumnModel().getColumn(i).setHeaderRenderer(headerCellRenderer);
        }
        for (int i = 0; i < tblDSSP.getModel().getColumnCount(); ++i) {
            tblDSSP.getColumnModel().getColumn(i).setHeaderRenderer(headerCellRenderer);
        }
//          updateStatus();

    }

    void loadTable(List<Object[]> data) {

        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        tblCenter.setHorizontalAlignment(SwingConstants.CENTER); // Thiết lập căn giữa
        // Áp dụng renderer cho từng cột trong bảng
        for (int i = 0; i < tblSanPham.getColumnCount(); i++) {
            tblSanPham.getColumnModel().getColumn(i).setCellRenderer(tblCenter);

        }
        try {
            List<Object[]> list = data;
           
            for (Object[] row : list) {

                Object[] rows = {
                    row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], XValidate.formatCurrency(row[8])
                };
                model.addRow(rows);
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblSanPham.getRowCount() - 1;
        
        btnThemSP.setEnabled(!edit);
        btnEditSP.setEnabled(edit);
        btnDeleteSP.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPre.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void fillComboBoxes() {
        fillComboBox(cboHDH, spDao.selectDistinctHDH());
        fillComboBox(cboCPU, spDao.selectDistinctCPU());
        fillComboBox(cboCamera, spDao.selectDistinctCamera());
        fillComboBox(cboPin, spDao.selectDistinctPin());
        fillComboBox(cboHangSX, spDao.selectDistinctHangSX());
        fillComboBox(cboManHinh, spDao.selectDistinctManHinh());
        fillComboBox(cboTinhTrang, spDao.selectDistinctTinhTrang());
        fillComboBox(cboRom, spctDao.selectDistinctRom());
        fillComboBox(cboRam, spctDao.selectDistinctRam());
        fillComboBox(cboMauSac, spctDao.selectDistinctMausac());
        fillComboBox(cboXuatXu, iemiDao.selectDistinctXuatXu());
        fillComboBox(cboDanhMuc, dmDao.selectDistinctTenDM());
        fillComboBox(cboTenDM, dmDao.selectDistinctTenDM());

        // Repeat this for other combo boxes
    }

    void fillComboBox(JComboBox<String> comboBox, List<String> data) {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
        model.removeAllElements();

        try {
            if (!data.isEmpty()) {
                for (String value : data) {
                    model.addElement(value);
                }
            } else {
                MsgBox.alert(this, "Danh sách trống.");
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu: " + e.getMessage());
        }
    }

    void edit() {
        int selectedRow = tblSanPham.getSelectedRow();

        // Kiểm tra xem có dòng nào được chọn không
        if (selectedRow != -1) {
            // Lấy giá trị từ JTable
            String MaSP = (String) tblSanPham.getValueAt(selectedRow, 0);
//            String TenSP = tblSanPham.getValueAt(selectedRow, 1).toString();
            String Ram = tblSanPham.getValueAt(selectedRow, 3).toString();
            String Rom = tblSanPham.getValueAt(selectedRow, 4).toString();
//            String Pin = tblSanPham.getValueAt(selectedRow, 5).toString();
            String MauSac = tblSanPham.getValueAt(selectedRow, 6).toString();
            String giaBanStr = tblSanPham.getValueAt(selectedRow, 8).toString().replace(",", "");

            // Chuyển đổi chuỗi thành số
            double GiaBan = Double.parseDouble(giaBanStr);

            SanPham sp = spDao.selectById(MaSP);
            String MaDM = sp.getMaDM();
            System.out.println(MaDM);
            this.setFrom(sp);

            SanPhamCT spct = spctDao.selectMaSPCTByConditions(MauSac, GiaBan, Ram, Rom);
            Integer MaSPCT = spct.getMaSPCT();
            System.out.println("MaSPCT edit : " + MaSPCT);
            this.setFromSPCT(spct);

            DanhMucSP dmsp = dmDao.selectById(MaDM);
            System.out.println("MaDM" + dmsp);
            this.setFromDanhMuc(dmsp);

            IEMISP iemi = iemiDao.selectById(MaSPCT);

            this.setFromIEMI(iemi);
            // this.updateStatus();
        }
    }

    void setFrom(SanPham sp) {
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
        cboHDH.setSelectedItem(sp.getHDH());
        cboCPU.setSelectedItem(sp.getCPU());
        cboCamera.setSelectedItem(sp.getCamera());
        cboPin.setSelectedItem(sp.getPin());
        cboHangSX.setSelectedItem(sp.getHangSX());
        cboManHinh.setSelectedItem(sp.getManHinh());
        cboTinhTrang.setSelectedItem(sp.getTinhTrang());
        cboTenDM.setSelectedItem(sp.getMaDM());

    }

    void setFromSPCT(SanPhamCT spct) {
        cboRom.setSelectedItem(spct.getRom());
        cboRam.setSelectedItem(spct.getRam());
        cboMauSac.setSelectedItem(spct.getMausac());

        String hinhAnh = spct.getHinhAnh();

        if (hinhAnh != null) {
            lblHinhAnh.setIcon(XImage.readWithDefaultSize(hinhAnh));

            // Lấy tên file từ đường dẫn đầy đủ
            String tenFile = new File(hinhAnh).getName();
            lblHinhAnh.setToolTipText(tenFile);
        }
        txtGiaBan.setText(XValidate.formatCurrency(spct.getGiaBan()));
    }

    void setFromIEMI(IEMISP iemi) {
//        cboIEMI.setSelectedItem(iemi.getIMEI());
        cboXuatXu.setSelectedItem(iemi == null ? "" : iemi.getXuatXu());
    }

    void setFromDanhMuc(DanhMucSP dm) {
        cboTenDM.setSelectedItem(dm.getTenDM());
    }

    public void showDetails() {
        if (row > -1) {
            tblSanPham.setRowSelectionInterval(row, row);
        }
    }

    private void first() {
        this.row = 0;
        this.edit();
        this.showDetails();
    }

    private void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
        this.showDetails();
    }

    private void next() {
        if (this.row < tblSanPham.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
        this.showDetails();
    }

    private void last() {
        this.row = tblSanPham.getRowCount() - 1;
        this.edit();
        this.showDetails();
        
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Lưu ảnh với kích thước đã cho
            XImage.saveImageWithDefaultSize(file);

            // Đọc ảnh với kích thước đã cho và hiển thị
            ImageIcon icon = XImage.readWithDefaultSize(file.getName());
            lblHinhAnh.setIcon(icon);
            lblHinhAnh.setToolTipText(file.getName());
        }
    }

//    void openBtnIEMI() {
//        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            File selectFile = fileChooser.getSelectedFile();
////            XFile.openFile();
//            List<String> imeiList = XFile.loadExcelData(selectFile);
//
//            // Clear existing items in the combo box
//            cboIEMI.removeAllItems();
//
//            // Add the loaded IMEI numbers to the combo box
//            for (String imei : imeiList) {
//                cboIEMI.addItem(imei);
//            }
//        }
//    }
    private String findMaDanhMucByTenDanhMuc(String tenDanhMuc) {
        // Lặp qua danh sách danh mục và tìm mã tương ứng
        for (DanhMucSP danhMuc : danhMucList) {
            if (danhMuc.getTenDM().equals(tenDanhMuc)) {
                return danhMuc.getMaDM();
            }
        }
        // Nếu không tìm thấy, có thể trả về một giá trị mặc định hoặc thông báo lỗi
        return "MaDMMacDinh";
    }

    SanPham getFrom() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setHDH((String) cboHDH.getSelectedItem());
        sp.setCPU((String) cboCPU.getSelectedItem());
        sp.setCamera((String) cboCamera.getSelectedItem());
        sp.setPin((String) cboPin.getSelectedItem());
        sp.setHangSX((String) cboHangSX.getSelectedItem());
        sp.setManHinh((String) cboManHinh.getSelectedItem());
        sp.setTinhTrang((String) cboTinhTrang.getSelectedItem());
        // Lấy tên danh mục được chọn
        String tenDanhMuc = (String) cboTenDM.getSelectedItem();

        // Tìm mã danh mục tương ứng từ danh sách danh mục (đã có sẵn)
        String maDanhMuc = findMaDanhMucByTenDanhMuc(tenDanhMuc);

        // Gắn mã danh mục vào đối tượng SanPham
        sp.setMaDM(maDanhMuc);
        return sp;
    }

    private Integer getMaSPCT() {
        int selectedRow = tblSanPham.getSelectedRow();

        if (selectedRow != -1) {
            // Lấy giá trị từ JTable
            String Ram = tblSanPham.getValueAt(selectedRow, 4).toString();
            String Rom = tblSanPham.getValueAt(selectedRow, 5).toString();
            String MauSac = tblSanPham.getValueAt(selectedRow, 7).toString();
            String giaBanStr = tblSanPham.getValueAt(selectedRow, 9).toString().replace(",", "");

            // Chuyển đổi chuỗi thành số
            double GiaBan = Double.parseDouble(giaBanStr);

            System.out.println("Debug: Ram=" + Ram + ", Rom=" + Rom + ", MauSac=" + MauSac + ", GiaBan=" + GiaBan);

            // Sử dụng spctDao để lấy MaSPCT dựa trên các điều kiện
            SanPhamCT spct = spctDao.selectMaSPCTByConditions(MauSac, GiaBan, Ram, Rom);

            if (spct != null) {
                // In ra để kiểm tra giá trị của MaSPCT
                System.out.println("Debug: MaSPCT found=" + spct.getMaSPCT());

                // Trả về MaSPCT nếu tìm thấy
                return spct.getMaSPCT();
            }
        }

        // Trả về -1 nếu không tìm thấy
        return -1;
    }

    SanPhamCT getFromSPCT() {
        SanPhamCT spct = new SanPhamCT();

        spct.setRom((String) cboRom.getSelectedItem());
        spct.setRam((String) cboRam.getSelectedItem());
        spct.setMausac((String) cboMauSac.getSelectedItem());
        spct.setHinhAnh(lblHinhAnh.getToolTipText());
        double giaban = Double.parseDouble(txtGiaBan.getText().replace(",", ""));
        spct.setGiaBan(giaban);
        spct.setMaSP(txtMaSP.getText());

        int MaSPCT = getMaSPCT();
        spct.setMaSPCT(MaSPCT);
        System.out.println("MaSPCT getFromSPCT " + MaSPCT);

        return spct;
    }

    // Thêm tham số để truyền giá trị được chọn từ ComboBox
    IEMISP getFromIEMI() {
        IEMISP iemi = new IEMISP();

        String imei = txtIEMI.getText().trim();

        // Kiểm tra xem imei có đúng là một chuỗi số không
        if (!imei.matches("\\d+")) {
            // Hiển thị thông báo hoặc xử lý lỗi nếu không phải là số
           
            MsgBox.alert(this, "Phải nhập là số có độ dài = 15");
            return null; // hoặc xử lý theo nhu cầu của bạn
        }

        // Kiểm tra xem imei có độ dài là 15 không
        if (imei.length() != 15) {
            // Hiển thị thông báo hoặc xử lý lỗi nếu độ dài không đúng
            
            MsgBox.alert(this, "Phải nhập là số có độ dài = 15");
            return null; 
        }

        iemi.setIMEI(imei);
        iemi.setXuatXu((String) cboXuatXu.getSelectedItem());

        int maSPCT = getMaSPCT();
        iemi.setMaSPCT(maSPCT);

        return iemi;
    }

    void clearFrom() {
        SanPham sp = new SanPham();
        SanPhamCT spct = new SanPhamCT();
        IEMISP iemi = new IEMISP();
        this.setFrom(sp);
        this.setFromSPCT(spct);
        this.setFromIEMI(iemi);
        this.row = -1;
//        this.updateStatus();
    }

    void insert() {
        if (getFrom() != null) {
            SanPham sp = getFrom();
            try {
                spDao.insert(sp);
            } catch (Exception e) {
                e.printStackTrace();

               
            }
        }
        if (getFromSPCT() != null) {
            SanPhamCT spct = getFromSPCT();
            try {
                spctDao.insert(spct);
                data = spDao.SelectTableSP();
                this.loadTable(data);
                MsgBox.alert(this, "Thêm sản phẩm thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm sản phẩm không thành công");
                e.printStackTrace();
            }
        }

    }

    void insertIEMI() {
        if (getFromIEMI() != null) {
            IEMISP iemi = getFromIEMI();
            try {
                iemiDao.insert(iemi);
                MsgBox.alert(this, "Thêm IEMI thành công ");
                data = spDao.SelectTableSP();
                this.loadTable(data);
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm IEMI thất bại");
                e.printStackTrace();
            }
        }
    }

    void update() {

        SanPham sp = getFrom();
        try {
            spDao.update(sp);
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhập thất bại !");
            e.printStackTrace();
        }

        SanPhamCT spct = getFromSPCT();
        try {
            spctDao.update(spct);

            data = spDao.SelectTableSP();
            this.loadTable(data);
            MsgBox.alert(this, "Cập nhập thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhập thất bại");
            e.printStackTrace();
        }

    }

    void Delete() {
        int row = tblSanPham.getSelectedRow();
        String MaSP = (String) tblSanPham.getValueAt(row,0);
        int SoLuong = (int) tblSanPham.getValueAt(row, 7);
        if (row == -1) {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }

        if (SoLuong >= 1) {
            MsgBox.alert(this, "Sản phẩm có số lượng tồn: " + SoLuong + " nên không thể xóa ");

        } else if (SoLuong == 0) {
            if (MsgBox.confirm(this, "Bạn muốn xóa sản phẩm ?")) {
                try {
                    int maSPCT = getMaSPCT();
                    spctDao.delete(maSPCT);
                    data = spDao.SelectTableSP();
                    this.loadTable(data);
                    fillComboBoxes();
                    MsgBox.alert(this, "Xóa thành công");

                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");
                    e.printStackTrace();
                }
            }
        }

    }

    void timKiemTenSP() {
        String tenSP = txtTimKiem.getText();

        // Gọi hàm tìm kiếm từ DAO hoặc Service
        List<Object[]> searchResult = spDao.searchProductByName(tenSP);

        // Xóa dữ liệu hiện tại của bảng
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        // Thêm dữ liệu mới từ kết quả tìm kiếm
        for (Object[] row : searchResult) {
            model.addRow(row);
        }
    }
    
      void finDanhMuc() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

//        DanhMucSP dm  = (DanhMucSP) cboDanhMuc.getSelectedItem();
        String tenDM = (String) cboDanhMuc.getSelectedItem();
        List<Object[]> list = spDao.findDanhMuc(tenDM);
        for (Object[] row : list) {
            model.addRow(new Object[]{
                row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], XValidate.formatCurrency(row[8])
            });
        }
    }
    //==================================TAB DSSP==================================================
    void LoadTable2(List<Object[]> datatable) {
        DefaultTableModel model = (DefaultTableModel) tblDSSP.getModel();
        model.setRowCount(0);
        try {
            for (Object[] row : datatable) {
                Object[] rows = {
                    row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10]};
                model.addRow(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuPanel = new javax.swing.JTabbedPane();
        pnSanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        lblHinhAnh = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        btnIEMI = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cboDanhMuc = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboHDH = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboManHinh = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboCPU = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboRom = new javax.swing.JComboBox<>();
        cboRam = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cboCamera = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cboPin = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboXuatXu = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboHangSX = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboTenDM = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        cboTinhTrang = new javax.swing.JComboBox<>();
        btnThemSP = new javax.swing.JButton();
        btnEditSP = new javax.swing.JButton();
        btnDeleteSP = new javax.swing.JButton();
        btnNewSP = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JFormattedTextField();
        txtIEMI = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        pnDSSP = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDSSP = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(1178, 780));
        setPreferredSize(new java.awt.Dimension(1178, 780));

        MenuPanel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        pnSanPham.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Danh Mục :");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        tblSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(28, 35, 65)));
        tblSanPham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Tình Trạng", "Ram", "Rom", "Pin", "Màu Sắc", "Số Lượng", "Giá Bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class
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
        tblSanPham.setRowHeight(30);
        tblSanPham.setSelectionBackground(new java.awt.Color(28, 35, 65));
        tblSanPham.setShowGrid(false);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
        }

        lblHinhAnh.setToolTipText("Click để chọn ảnh");
        lblHinhAnh.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(28, 35, 65)));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhAnhMousePressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Tên sản phẩm:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Giá Bán :");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("IMEI:");

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTenSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        btnIEMI.setBackground(new java.awt.Color(28, 35, 65));
        btnIEMI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-edit-64.png"))); // NOI18N
        btnIEMI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnIEMIMousePressed(evt);
            }
        });
        btnIEMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIEMIActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Tìm kiếm :");

        cboDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboDanhMuc.setMaximumRowCount(100);
        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Hệ điều hành");

        cboHDH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Màn hình");

        cboManHinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboManHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboManHinhActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("CPU");

        cboCPU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("ROM");

        cboRom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        cboRam.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("RAM");

        cboCamera.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Camera");

        cboMauSac.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Màu sắc");

        cboPin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Pin");

        cboXuatXu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Xuất xứ");

        cboHangSX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cboHangSX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHangSXActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Hãng sản xuất");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Danh mục");

        cboTenDM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Tình trạng");

        cboTinhTrang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnThemSP.setBackground(new java.awt.Color(28, 35, 65));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-plus-50.png"))); // NOI18N
        btnThemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSPMouseClicked(evt);
            }
        });
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnEditSP.setBackground(new java.awt.Color(28, 35, 65));
        btnEditSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-edit-text-file-50.png"))); // NOI18N
        btnEditSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditSPMouseClicked(evt);
            }
        });
        btnEditSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSPActionPerformed(evt);
            }
        });

        btnDeleteSP.setBackground(new java.awt.Color(28, 35, 65));
        btnDeleteSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-delete-50.png"))); // NOI18N
        btnDeleteSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteSPMouseClicked(evt);
            }
        });
        btnDeleteSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSPActionPerformed(evt);
            }
        });

        btnNewSP.setBackground(new java.awt.Color(28, 35, 65));
        btnNewSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-new-50.png"))); // NOI18N
        btnNewSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewSPMouseClicked(evt);
            }
        });
        btnNewSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSPActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(28, 35, 65));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-double-Down50.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPre.setBackground(new java.awt.Color(28, 35, 65));
        btnPre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-back-50.png"))); // NOI18N
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(28, 35, 65));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-forward-50.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(28, 35, 65));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mobilemanagement/Icon/icons8-double-up-50.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("VNĐ");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Mã sản phẩm:");

        txtMaSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMaSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        txtGiaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtGiaBan.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtIEMI.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtIEMI.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnSanPhamLayout = new javax.swing.GroupLayout(pnSanPham);
        pnSanPham.setLayout(pnSanPhamLayout);
        pnSanPhamLayout.setHorizontalGroup(
            pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSanPhamLayout.createSequentialGroup()
                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSanPhamLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditSP, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteSP, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNewSP, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSanPhamLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 12, Short.MAX_VALUE))
                            .addGroup(pnSanPhamLayout.createSequentialGroup()
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cboManHinh, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboHDH, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboCPU, 0, 125, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboRom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboCamera, 0, 125, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboRam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboMauSac, 0, 125, Short.MAX_VALUE)
                                    .addComponent(cboPin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTenDM, 0, 127, Short.MAX_VALUE)
                                    .addComponent(cboTinhTrang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboXuatXu, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboHangSX, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel28)
                    .addGroup(pnSanPhamLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnSanPhamLayout.createSequentialGroup()
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtIEMI)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnSanPhamLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSanPhamLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(btnIEMI, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnSanPhamLayout.setVerticalGroup(
            pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSanPhamLayout.createSequentialGroup()
                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnSanPhamLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSanPhamLayout.createSequentialGroup()
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(txtGiaBan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnIEMI, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(txtIEMI))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnSanPhamLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                        .addGap(14, 14, 14)
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboTenDM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboPin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboHDH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboHangSX, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnSanPhamLayout.createSequentialGroup()
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(34, 34, 34)))
                        .addGap(37, 37, 37)
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnSanPhamLayout.createSequentialGroup()
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboManHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboRom, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboRam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 49, Short.MAX_VALUE)
                        .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEditSP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDeleteSP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewSP, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12))))
        );

        MenuPanel.addTab("Sản Phẩm", pnSanPham);

        pnDSSP.setBackground(new java.awt.Color(255, 255, 255));

        tblDSSP.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(28, 35, 65)));
        tblDSSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblDSSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Điện Thoại", "Mã IEMI", "Xuất Xứ", "HĐH", "Rom", "Ram", "Màu Sắc", "Pin", "Tình Trạng", "Danh Mục", "Giá Bán"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSSP.setRowHeight(30);
        tblDSSP.setSelectionBackground(new java.awt.Color(28, 35, 65));
        jScrollPane2.setViewportView(tblDSSP);
        if (tblDSSP.getColumnModel().getColumnCount() > 0) {
            tblDSSP.getColumnModel().getColumn(0).setResizable(false);
            tblDSSP.getColumnModel().getColumn(0).setPreferredWidth(170);
            tblDSSP.getColumnModel().getColumn(1).setResizable(false);
            tblDSSP.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblDSSP.getColumnModel().getColumn(2).setResizable(false);
            tblDSSP.getColumnModel().getColumn(3).setResizable(false);
            tblDSSP.getColumnModel().getColumn(4).setResizable(false);
            tblDSSP.getColumnModel().getColumn(5).setResizable(false);
            tblDSSP.getColumnModel().getColumn(6).setResizable(false);
            tblDSSP.getColumnModel().getColumn(7).setResizable(false);
            tblDSSP.getColumnModel().getColumn(8).setResizable(false);
            tblDSSP.getColumnModel().getColumn(9).setResizable(false);
            tblDSSP.getColumnModel().getColumn(10).setResizable(false);
        }

        javax.swing.GroupLayout pnDSSPLayout = new javax.swing.GroupLayout(pnDSSP);
        pnDSSP.setLayout(pnDSSPLayout);
        pnDSSPLayout.setHorizontalGroup(
            pnDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1178, Short.MAX_VALUE)
        );
        pnDSSPLayout.setVerticalGroup(
            pnDSSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDSSPLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                .addGap(61, 61, 61))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDSSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDSSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MenuPanel.addTab("Danh Sách Sản Phẩm", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuPanel)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnEditSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSPActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnEditSPActionPerformed

    private void btnDeleteSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteSPActionPerformed

    private void btnNewSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNewSPActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void cboHangSXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHangSXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHangSXActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblSanPham.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_tblSanPhamMousePressed

    private void lblHinhAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMousePressed
        // TODO add your handling code here:
        chonAnh();
    }//GEN-LAST:event_lblHinhAnhMousePressed

    private void btnThemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseClicked
        // TODO add your handling code here:
         if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền sử dụng chức năng");

        } else{
             this.insert();
         }
        
    }//GEN-LAST:event_btnThemSPMouseClicked

    private void btnDeleteSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteSPMouseClicked
        // TODO add your handling code here:
         if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền sử dụng chức năng");

        } else{
            this. Delete();
         }
    }//GEN-LAST:event_btnDeleteSPMouseClicked

    private void btnEditSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditSPMouseClicked
        // TODO add your handling code here:
          if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền sử dụng chức năng");

        } else{
               this.update();
         }
      
    }//GEN-LAST:event_btnEditSPMouseClicked

    private void btnNewSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewSPMouseClicked
        // TODO add your handling code here:
         
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền sử dụng chức năng");

        } else{
              this. clearFrom();
         }
    }//GEN-LAST:event_btnNewSPMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        timKiemTenSP();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnIEMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIEMIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIEMIActionPerformed

    private void btnIEMIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIEMIMousePressed
        // TODO add your handling code here:
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền sử dụng chức năng");

        } else{
              insertIEMI();
         }
    }//GEN-LAST:event_btnIEMIMousePressed

    private void cboManHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboManHinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboManHinhActionPerformed

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        // TODO add your handling code here:
        finDanhMuc();
    }//GEN-LAST:event_cboDanhMucActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MenuPanel;
    private javax.swing.JButton btnDeleteSP;
    private javax.swing.JButton btnEditSP;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnIEMI;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNewSP;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JComboBox<String> cboCPU;
    private javax.swing.JComboBox<String> cboCamera;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboHDH;
    private javax.swing.JComboBox<String> cboHangSX;
    private javax.swing.JComboBox<String> cboManHinh;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboPin;
    private javax.swing.JComboBox<String> cboRam;
    private javax.swing.JComboBox<String> cboRom;
    private javax.swing.JComboBox<String> cboTenDM;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JComboBox<String> cboXuatXu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JPanel pnDSSP;
    private javax.swing.JPanel pnSanPham;
    private javax.swing.JTable tblDSSP;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JFormattedTextField txtGiaBan;
    private javax.swing.JTextField txtIEMI;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
