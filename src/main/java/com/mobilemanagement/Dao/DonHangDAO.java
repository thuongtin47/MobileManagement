/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Dao;

import com.mobilemanagement.Model.DonHang;
import com.mobilemanagement.Utility.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class DonHangDAO extends abstractDAO<DonHang, String> {

    final String INSERT_SQL = "INSERT INTO [dbo].[DonHang]([MaDH],[NgayLap],[MaKH],[MaNV],[MaGG],[GhiChu]) VALUES (?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE DonHang SET NgayLap = ?, MaKH = ?, MaNV = ?, MaGG = ?, GhiChu = ? WHERE MaDH = ?;";
    final String DELETE_SQL = "DELETE FROM DonHang WHERE MaDH = ?;";
    final String SELECT_ALL_SQL = "select * from DonHang;";
    final String Select_BYID_SQL = "SELECT * FROM DonHang WHERE MaDH = ?;";
    final String Select_BYID_GG = "SELECT MaGG FROM GiamGia;";

    @Override
    public void insert(DonHang entity) {
        JDBC.update(INSERT_SQL, entity.getMaDH(), entity.getNgayLap(), entity.getMaKH(), entity.getMaNV(), entity.getMaGG(), entity.getGhiChu());
    }

    @Override
    public void update(DonHang entity) {
        JDBC.update(UPDATE_SQL, entity.getNgayLap(), entity.getMaKH(), entity.getMaNV(), entity.getMaGG(), entity.getGhiChu(), entity.getMaDH());
    }

    @Override
    public void delete(String id) {
        JDBC.update(DELETE_SQL, id);
    }

    @Override
    public List<DonHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DonHang selectById(String id) {
        List<DonHang> list = selectBySql(Select_BYID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<DonHang> selectMaGG() {
        return selectBySql(Select_BYID_GG);
    }

    @Override
    public List<DonHang> selectBySql(String sql, Object... args) {
        List<DonHang> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                DonHang entity = new DonHang();
                entity.setMaDH(rs.getString("MaDH"));
                entity.setNgayLap(rs.getDate("NgayLap"));

                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaKH(rs.getString("MaKH"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaGG(rs.getString("MaGG"));
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
  
  public List<Object[]> selectTableDonHang() {
    String sql = "{CALL SelectTableDonHang}";
    String[] cols = {"MaDH", "HoTen", "SDT", "TenNV", "MaGG", "Giatri", "NgayLap", "TongDonHang", "GhiChu"};

    return getListArray(sql, cols);
}
  
  }

