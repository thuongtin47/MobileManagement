/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Model;

/**
 *
 * @author DINHVU
 */
public class DonHangCT {
    
    private String MaDH;
    private String MaSP;
    private int MaSPCT;
    private String IEMI;
    private double DonGia;

    public DonHangCT() {
    }

    public DonHangCT(String MaDH, String MaSP, int MaSPCT, String IEMI, double DonGia) {
        this.MaDH = MaDH;
        this.MaSP = MaSP;
        this.MaSPCT = MaSPCT;
        this.IEMI = IEMI;
        this.DonGia = DonGia;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String MaDH) {
        this.MaDH = MaDH;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public int getMaSPCT() {
        return MaSPCT;
    }

    public void setMaSPCT(int MaSPCT) {
        this.MaSPCT = MaSPCT;
    }

    public String getIEMI() {
        return IEMI;
    }

    public void setIEMI(String IEMI) {
        this.IEMI = IEMI;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }
    
    
    
    
}
