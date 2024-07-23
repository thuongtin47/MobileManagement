/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Dao;

import com.mobilemanagement.Model.IEMISP;
import com.mobilemanagement.Utility.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DINHVU
 */
public class IEMISPDao extends abstractDAO<IEMISP, Integer> {

    final String INSERT_SQL = "INSERT IMEISP(IMEI,XuatXu,MaSPCT) VALUES(?,?,?);";
    final String UPDATE_SQL = "UPDATE IMEISP SET XuatXu = ? ,MaSPCT = ? WHERE IMEI = ?;";
    final String DELETE_SQL = "DELETE From IMEISP WHERE IMEI = ?;";
    final String SELECT_ALL_SQL = "SELECT * FROM IMEISP;";
    final String Select_BYID_SQL = "SELECT * FROM IMEISP WHERE MaSPCT = ?";
    final String Select_BYIEMI_SQL = "SELECT * FROM IMEISP WHERE IMEI LIKE '%' + ? + '%'";
    final String Select_IMEI_SQL = "SELECT COUNT(*) FROM IMEISP WHERE IMEI = ?";
    final String Set_trangThai_SQL = "UPDATE IMEISP SET TrangThai = 0 where IMEI = ?;";

    @Override
    public void insert(IEMISP entity) {
        JDBC.update(INSERT_SQL, entity.getIMEI(), entity.getXuatXu(), entity.getMaSPCT());
    }

    @Override
    public void update(IEMISP entity) {
        JDBC.update(UPDATE_SQL, entity.getXuatXu(), entity.getMaSPCT(), entity.getIMEI());
    }
    
    public void updateTrangThai(String iemi) {
        JDBC.update(Set_trangThai_SQL,  iemi);
    }

    @Override
    public void delete(Integer id) {
        JDBC.update(DELETE_SQL, id);
    }

    @Override
    public List<IEMISP> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

//    public boolean checkIMEI(String imei) {
//        ResultSet rs = null;
//
//        try {
//            // Thực hiện truy vấn kiểm tra IMEI trong cơ sở dữ liệu
//            rs = JDBC.query(Select_IMEI_SQL, imei);
//
//            if (rs.next()) {
//                int count = rs.getInt(1);
//                return count > 0; // Trả về true nếu IMEI đã tồn tại
//            }
//        } catch (Exception e) {
//            // Xử lý ngoại lệ
//            e.printStackTrace();
//        } finally {
//            // Đóng ResultSet
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return false; // Trả về false nếu có lỗi hoặc không có bản ghi
//    }
    @Override
    public IEMISP selectById(Integer id) {
        List<IEMISP> list = selectBySql(Select_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<IEMISP> selectByIEMI(String IEMI) {

        return selectBySql(Select_BYIEMI_SQL, IEMI);
    }

    public List<IEMISP> selectByMaSPCT(int maSPCT) {
        String SELECT_BY_MA_SPCT_SQL = "SELECT * FROM IMEISP WHERE MaSPCT = ?";
        return selectBySql(SELECT_BY_MA_SPCT_SQL, maSPCT);
    }

    @Override
    public List<IEMISP> selectBySql(String sql, Object... args) {
        List<IEMISP> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                IEMISP entity = new IEMISP();
                entity.setIMEI(rs.getString(1));
                entity.setXuatXu(rs.getString(2));
                entity.setMaSPCT(rs.getInt(3));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> selectDistinctXuatXu() {
        return selectDistinctValues("XuatXu");
    }

    private List<String> selectDistinctValues(String columnName) {
        String query = "SELECT DISTINCT " + columnName + " FROM IMEISP;";
        return executeSelectDistinctQuery(query);
    }

    private List<String> executeSelectDistinctQuery(String query) {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(query);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
