/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Dao;

import com.mobilemanagement.Utility.JDBC;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            try (ResultSet rs = JDBC.query(sql, args)) {
                while (rs.next()) {
                    Object[] vals = new Object[cols.length];
                    for (int i = 0; i < cols.length; i++) {
                        vals[i] = rs.getObject(cols[i]);
                    }
                    list.add(vals);
                }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> thongKeSanPham() {
        String sql = "{CALL ThongKeSoLuongHienCoSanPhamTheoIMEI}";
        String[] cols = {"MaSP", "TenSP", "SoLuongHienCo"};
        return getListOfArray(sql, cols);
    }

//    public List<Object[]> thongKeDoanhThu() {
//        String sql = "{CALL ThongKeDoanhThu}";
//        String[] cols = {"TenSanPham", "SoLuongBan", "TongDoanhThu"};
//        return getListOfArray(sql, cols);
//    }
    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT year(NgayLap) Year FROM DonHang ORDER BY Year DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

//    public List<Integer> selectMonth() {
//        String sql = "SELECT DISTINCT\n"
//                + "    YEAR(NgayLap) AS Years,\n"
//                + "    MONTH(NgayLap) AS Month\n"
//                + "FROM \n"
//                + "    DonHang\n"
//                + "ORDER BY \n"
//                + "    Years DESC, Month DESC;";
//        List<Integer> list = new ArrayList<>();
//        try {
//            ResultSet rs = JDBC.query(sql);
//            while (rs.next()) {
//                list.add(rs.getInt(1));
//            }
//            rs.getStatement().getConnection().close();
//            return list;
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

    public List<Object[]> getDoanhThuTheoNam(int nam) {
        String sql = "{CALL sp_ThongKeDoanhThuTheoNam(?)}";
        String[] cols = {"TenSanPham", "DoanhThuTongCong"};
        return this.getListOfArray(sql, cols, nam);
    }

//    public List<Object[]> getDoanhThuTheoThangNam(int thang, int nam) {
//        String sql = "{CALL sp_ThongKeDoanhThuTheoThangNam(?, ?)}";
//        String[] cols = {"MaDonHang","NgayLapDon","TenSanPham", "DoanhThuTongCong"};
//        return this.getListOfArray(sql, cols, thang, nam);
//    }

}
