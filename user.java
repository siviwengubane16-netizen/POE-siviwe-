
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Student
 */

public class user {
 private String username;
    private String password;
    private String cellphone;

    public user(String username, String password, String cellphone) {
        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
    }

    // Username validation
    public static boolean validateUsername(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Password validation
    public static boolean validatePassword(String password) {
        if (password == null) return false;
        if (password.length()<8) return false;
        boolean hasUpper = false; //= password.matches(".[A-Z].");
        boolean hasDigit = false; //= password.matches(".\\d.");
        boolean hasSpecial = false; //= password.matches(".[!@#$%^&(),.?\":{}|<>].*");
        for (char c : password.toCharArray()){
        if (Character.isUpperCase(c))hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }
    // SA cellphone validation
    public boolean validateCellphone(String phone) {
        if (cellphone == null) return false;
        String saPattern = "^\\+27\\d{9}$";
        return cellphone.matches(saPattern);
        //return phone != null && phone.matches("^\\+27\\d{9}$");

    }
    

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getCellphone() { return cellphone; }
}
