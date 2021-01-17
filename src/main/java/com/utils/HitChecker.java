package com.utils;

public class HitChecker {
    public static boolean isInArea(double x, double y, double r) {
        return ((x > -r / 2.0 && x < 0 && y > -r && y < 0)
                || (x > 0 && y <= 0 && x * x + y * y < r / 2.0 * r / 2.0)
                || (x > 0 && y <= 0 && y > 0.5 * x - r));
    }
}
