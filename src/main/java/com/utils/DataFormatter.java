package com.utils;

public class DataFormatter {
    public static String formatValue(String value) {
        final int SYM_NUMBER = 8;
        if (value.contains(".") || value.contains(",")) {
            if (value.length() > SYM_NUMBER) {
                return value.substring(0, 5).replace(",", ".");
            }
        }
        return value;
    }
}
