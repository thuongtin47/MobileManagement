/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobilemanagement.Utility;

import java.text.DecimalFormat;

/**
 *
 * @author DINHVU
 */
public class XValidate {
   

    public static String formatCurrency(Object amount) {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(amount);
    }

}
