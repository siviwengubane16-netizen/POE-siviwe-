/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Student
 */
public class login {
      private static user registeredUser = null;

    // Registration logic
    public static boolean registerUser(String username, String password, String phone) {
        user us = new user(username, password, phone);
        if (!us.validateUsername(username) || !us.validatePassword(password) || !us.validateCellphone(phone)) {
            return false;
        }
        registeredUser = new user(username, password, phone);
        return true;
    }

    // Login verification logic
    public static boolean loginuser(String username, String password) {
        if (registeredUser == null) {
            return false;
        }
        return registeredUser.getUsername().equals(username) && registeredUser.getPassword().equals(password);
    }

    public static user getRegisteredUser() {
        return registeredUser;
    }
}