/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mylib;

import dao.CustomerDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.SalePerson;

/**
 *
 * @author datho
 */
public class Validation {

    public static String normalize(String param) {
        return param == null ? "" : param.trim();
    }

    public static Integer parseInt(String year) {
        try {
            return (year == null || year.isEmpty()) ? null : Integer.parseInt(year);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Long parseLong(String longData) {
        try {
            return (longData == null || longData.isEmpty()) ? null : Long.parseLong(longData);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean doesExistCustomer(String name, long phone) {
        CustomerDAO dao = new CustomerDAO();
        if (dao.isExistCustomer(name, phone)) {
            return true;
        }
        return false;
    }

    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        if (!phone.matches("[0-9]+")) {
            return false;
        }
        if (phone.length() >= 10 && phone.length() <= 15) {
            return true;
        }

        return false;
    }

    public static boolean isValidFullName(String fullname) {
        if (fullname == null || fullname.trim().isEmpty()) {
            return false;
        }
        String name = fullname.trim().replaceAll("\\s{2,}", " ");

        if (name.length() < 2 || name.length() > 100) {
            return false;
        }
        if (!name.contains(" ")) {
            return false;
        }
        // \p{L} matches any kind of letter from any language
        // \p{M} matches a character intended to be combined with another character (e.g. accents)
        // \p{Pd} matches any kind of hyphen or dash
        // \p{Zs} matches a space separator (for allowing spaces)
        // \' allows for apostrophes in names like O'Connor
        String regex = "^[\\p{L}\\p{M}\\p{Pd}\\\'\\s]+$";
        return name.matches(regex);
    }
    
    public static SalePerson validateSalePersonSession(HttpServletRequest request, HttpServletResponse response,HttpSession session)throws IOException{
        if(session == null) return null;
        SalePerson salePerson = (SalePerson) session.getAttribute("SALEPERSON");
        if(salePerson == null) return null;
        
        return salePerson;
    }

    public static boolean isEmptyString(ArrayList<String> list){
        for (String string : list) {
            if(string.isEmpty()) return true;
        }
        return false;
    }
}
