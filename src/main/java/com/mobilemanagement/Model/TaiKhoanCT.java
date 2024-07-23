/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Model;

import java.util.Date;

/**
 *
 * @author DINHVU
 */
public class TaiKhoanCT {
    private String MaNV;
    private String HoTen;
    private boolean GioiTinh;
    private Date  NamSinh;
    private String DiaChi;
    private String Email;
    private String SDT;
    private String HinhAnh;
    private String ChucVu;

    public TaiKhoanCT() {
    }

    public TaiKhoanCT(String MaNV, String HoTen, boolean GioiTinh, Date NamSinh, String DiaChi, String Email, String SDT, String HinhAnh, String ChucVu) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.NamSinh = NamSinh;
        this.DiaChi = DiaChi;
        this.Email = Email;
        this.SDT = SDT;
        this.HinhAnh = HinhAnh;
        this.ChucVu = ChucVu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public Date getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(Date NamSinh) {
        this.NamSinh = NamSinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }
 
    
    
    
    
}
