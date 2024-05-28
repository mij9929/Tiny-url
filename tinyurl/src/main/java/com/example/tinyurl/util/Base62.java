package com.example.tinyurl.util;

import org.springframework.stereotype.Component;

@Component
public class Base62{
    private static final String BASE62_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = BASE62_CHARACTERS.length();
    private static final int SHORT_URL_LENGTH = 7;

    public  String encode(long number) {
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(BASE62_CHARACTERS.charAt((int) (number % BASE)));
            number /= BASE;
        }
        // Ensure short URL is exactly 7 characters long
        while (sb.length() < SHORT_URL_LENGTH) {
            sb.append(BASE62_CHARACTERS.charAt(0)); // Padding with the first character
        }
        return sb.reverse().toString();
    }

    public  long decode(String str) {
        long num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + BASE62_CHARACTERS.indexOf(str.charAt(i));
        }
        return num;
    }
}
