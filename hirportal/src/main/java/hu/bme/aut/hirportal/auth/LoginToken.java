package hu.bme.aut.hirportal.auth;

import java.util.Random;

public class LoginToken {
    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final Random RND = new Random();
    private String token;
    public LoginToken() {
        GenerateToken();
    }
    private void GenerateToken() {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 6; i++)
            sb.append(CHARS[RND.nextInt(CHARS.length)]);
        token = sb.toString();
    }
    public String GetToken() {
        return token;
    }
}

