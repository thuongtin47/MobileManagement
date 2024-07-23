 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Model;

/**
 *
 * @author DINHVU
 */
public class UserLogin {
    private String MaID;
    private  String UserName;
    private String PASSWORD;
    private boolean Vaitro;

    public UserLogin() {
    }

    public UserLogin(String MaID, String UserName, String PASSWORD, boolean Vaitro) {
        this.MaID = MaID;
        this.UserName = UserName;
        this.PASSWORD = PASSWORD;
        this.Vaitro = Vaitro;
    }

    public String getMaID() {
        return MaID;
    }

    public void setMaID(String MaID) {
        this.MaID = MaID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public boolean isVaitro() {
        return Vaitro;
    }

    public void setVaitro(boolean Vaitro) {
        this.Vaitro = Vaitro;
    }

    @Override
    public String toString() {
        return "UserLogin{" + "MaID=" + MaID + ", UserName=" + UserName + ", PASSWORD=" + PASSWORD + ", Vaitro=" + Vaitro + '}';
    }
    
    
}
