/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Model;

import com.mobilemanagement.Dao.SanPhamDao;
import com.mobilemanagement.Utility.XDate;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author DINHVU //
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static Date getDate() {
        try {
            String date_s = XDate.getCurrentDateFormat();

            // Chuyển đổi chuỗi thành đối tượng Date
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return dt.parse(date_s);
        } catch (ParseException ex) {
            // Handle the exception or print the stack trace
            ex.printStackTrace();
            return null; // or throw a new RuntimeException("Failed to parse date", ex);
        }
    }

    public static void main(String[] args) {
        DonHang dh = new DonHang();
        Date date = getDate();

        if (date != null) {
            dh.setNgayLap(date);
            System.out.println("NgayLap: " + date);
        } else {
            System.out.println("Failed to get a valid date.");
        }

        // Lấy ngày giờ hiện tại
    }
}

