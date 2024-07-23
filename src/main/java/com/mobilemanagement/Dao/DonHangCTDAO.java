/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Dao;

import com.mobilemanagement.Model.DonHangCT;
import com.mobilemanagement.Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class DonHangCTDAO extends abstractDAO<DonHangCT, Integer> {
    
    final String INSERT_SQL = "INSERT INTO [dbo].[DonHangCT]([MaDH],[MaSP],[MaSPCT],[IEMI],[DonGia]) VALUES (?, ?, ?, ?, ?);";
    final String UPDATE_SQL = "UPDATE DonHangCT SET  MaSP = ?, MaSPCT = ?, IMEI = ?, DonGia = ? WHERE MaDHCT = ?;";
    final String DELETE_SQL = "DELETE FROM DonHangCT WHERE MaDHCT = ?;";
    final String SELECT_ALL_SQL = "SELECT * FROM DonHangCT;";
    final String SELECT_BY_ID_SQL = "SELECT * FROM DonHangCT WHERE MaDHCT = ?;";
    
    @Override
    public void insert(DonHangCT entity) {
        JDBC.update(INSERT_SQL, entity.getMaDH(), entity.getMaSP(), entity.getMaSPCT(), entity.getIEMI(), entity.getDonGia());
    }
    
    @Override
    public void update(DonHangCT entity) {
        JDBC.update(UPDATE_SQL, entity.getMaSP(), entity.getMaSPCT(), entity.getIEMI(), entity.getDonGia(), entity.getMaSPCT());
    }
    
    @Override
    public void delete(Integer id) {
        JDBC.update(DELETE_SQL, id);
    }
    
    @Override
    public List<DonHangCT> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }
    
    @Override
    public DonHangCT selectById(Integer id) {
        List<DonHangCT> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public DonHangCT selectByMaHD(String id) {
        List<DonHangCT> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public List<DonHangCT> selectBySql(String sql, Object... args) {
        List<DonHangCT> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                DonHangCT entity = new DonHangCT();
                //;
                entity.setMaSPCT(rs.getInt("MaDHCT"));
                entity.setMaDH(rs.getString("MaDH"));
                entity.setMaSP(rs.getString("MaSP"));
                entity.setMaSPCT(rs.getInt("MaSPCT"));
                entity.setIEMI(rs.getString("IMEI"));
                entity.setDonGia(rs.getDouble("DonGia"));
                
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi truy vấn dữ liệuDAO: " + e.getMessage());
        }
        return list;
    }
    
    private List<Object []> getListArray(String sql, String[] cols, Object...args){
        try {
            List<Object []> list = new ArrayList<>();
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {                
                Object [] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
   public List<Object[]> GetDonHangCTDetails(String maDH) {
    String sql = "{CALL GetDonHangCTDetails(?)}";
    String[] cols = {"MaDHCT", "MaDH", "MaSP", "ThongTin", "IEMI", "DonGia"};
    return getListArray(sql, cols, maDH);
}
}
