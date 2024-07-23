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
public class DonHang {
    private String MaDH;
    private Date NgayLap;
    
    private String GhiChu;
    private String MaKH;
    private String MaNV;
    private String MaGG;

    public DonHang() {
    }

    public DonHang(String MaDH, Date NgayLap, String GhiChu, String MaKH, String MaNV, String MaGG) {
        this.MaDH = MaDH;
        this.NgayLap = NgayLap;
        this.GhiChu = GhiChu;
        this.MaKH = MaKH;
        this.MaNV = MaNV;
        this.MaGG = MaGG;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String MaDH) {
        this.MaDH = MaDH;
    }

    public Date getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(Date NgayLap) {
        this.NgayLap = NgayLap;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMaGG() {
        return MaGG;
    }

    public void setMaGG(String MaGG) {
        this.MaGG = MaGG;
    }

   
       
}
