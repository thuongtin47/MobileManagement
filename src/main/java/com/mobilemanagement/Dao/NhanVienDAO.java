/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Dao;

import com.mobilemanagement.Model.TaiKhoanCT;
import com.mobilemanagement.Utility.JDBC;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class NhanVienDAO extends abstractDAO<TaiKhoanCT, String> {

    final String INSERT_SQL = "INSERT INTO TaiKhoanCT (MaNV, HoTen, GioiTinh, NamSinh, DiaChi, Email, SDT, HinhAnh, ChucVu) VALUES (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE TaiKhoanCT set HoTen = ?, GioiTinh = ?, NamSinh = ?, DiaChi = ?, Email = ?,SDT = ?,HinhAnh = ?,ChucVu =?  where MaNV = ?";
    final String DELETE_SQL = "delete from TaiKhoanCT where MaNV = ?";
    final String SELECT_ALL_SQL = "select * from TaiKhoanCT";
    final String SELECT_BY_ID_SQL = "select * from TaiKhoanCT where MaNV = ?";
    final String SELECT_BY_MA_US_SQL = "SELECT * FROM TaiKhoanCT WHERE MaNV = ?";

    @Override
    public void insert(TaiKhoanCT entity) {
        JDBC.update(INSERT_SQL, entity.getMaNV(), entity.getHoTen(), entity.isGioiTinh(), entity.getNamSinh(), entity.getDiaChi(), entity.getEmail(), entity.getSDT(), entity.getHinhAnh(), entity.getChucVu());
    }

    @Override
    public void update(TaiKhoanCT entity) {
        JDBC.update(UPDATE_SQL, entity.getHoTen(), entity.isGioiTinh(), entity.getNamSinh(), entity.getDiaChi(), entity.getEmail(), entity.getSDT(), entity.getHinhAnh(), entity.getChucVu(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        JDBC.update(DELETE_SQL, id);
    }

    @Override
    public List<TaiKhoanCT> selectAll() {
        return selectBySql(SELECT_ALL_SQL);

    }

    @Override
    public TaiKhoanCT selectById(String id) {
        List<TaiKhoanCT> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public List<TaiKhoanCT> selectKhoaHocByUserLogin(String maID) {
        return selectBySql(SELECT_BY_MA_US_SQL, maID);
    }
    
    
    

    @Override
    public List<TaiKhoanCT> selectBySql(String sql, Object... args) {
        List<TaiKhoanCT> list = new ArrayList<>();
        try {
            ResultSet rs = JDBC.query(sql, args);
            while (rs.next()) {
                TaiKhoanCT entity = new TaiKhoanCT();
                entity.setMaNV(rs.getString("MaNV"));               
                entity.setHoTen(rs.getString("HoTen"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setChucVu(rs.getString("ChucVu"));
                entity.setNamSinh(rs.getDate("NamSinh"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setEmail(rs.getString("Email"));
                entity.setSDT(rs.getString("SDT"));
                entity.setHinhAnh(rs.getString("HinhAnh"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<String> selectDistinctMaNV() {
        return selectDistinctValues("MaNV");
    }
    
    private List<String> selectDistinctValues(String columnName) {
        String query = "SELECT DISTINCT " + columnName + " FROM UserLogin;";
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


