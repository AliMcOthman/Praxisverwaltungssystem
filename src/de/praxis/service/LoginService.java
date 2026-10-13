package de.praxis.service;

public class LoginService {
    public static Boolean loginTest(String username, String passwort){
        if(username.equalsIgnoreCase("Admin")&& passwort.equalsIgnoreCase("12345")){
            return true;
        } else {
            return false;
        }
    }
}
