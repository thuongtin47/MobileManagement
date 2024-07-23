/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Model;

/**
 *
 * @author DINHVU
 */
public class IEMISP {

    private String IMEI;
    private String XuatXu;
    private int MaSPCT;
    private boolean TrangThai;

    public IEMISP() {
    }

    public IEMISP(String IMEI, String XuatXu, int MaSPCT, boolean TrangThai) {
        this.IMEI = IMEI;
        this.XuatXu = XuatXu;
        this.MaSPCT = MaSPCT;
        this.TrangThai = TrangThai;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String XuatXu) {
        this.XuatXu = XuatXu;
    }

    public int getMaSPCT() {
        return MaSPCT;
    }

    public void setMaSPCT(int MaSPCT) {
        this.MaSPCT = MaSPCT;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

}
