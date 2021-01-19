package com.controllers;

import java.util.HashMap;

public class Validator
{
    public static HashMap<String, Integer> xr = new HashMap<>();

    static {
        for(int i = -4; i <= 4; i++) {
            xr.put(String.valueOf(i), i);
        }
    }

    public static double validateX(String x)
    {
        if (xr.containsKey(x)) {
            return xr.get(x);
        } else {
            throw new RuntimeException("X is not an integer in range [-4 .. 4]");
        }
    }

    public static double validateR(String r)
    {
        if (xr.containsKey(r)) {
            return xr.get(r);
        } else {
            throw new RuntimeException("R is not an integer in range [-4 .. 4]");
        }
    }


    public static double validateY(String y)
    {
        try
        {
            y = y.replace(',', '.');
            Double.parseDouble(y);

            if (y.length() > 5)
            {
                y = y.substring(0, 5);
            }

            double val = Double.parseDouble(y);

            if (val <= -3 || val >= 3)  {
                throw new RuntimeException("Y is not in range of (-3; 3)");
            } else {
                return val;
            }
        }
        catch (NumberFormatException e)
        {
            throw new RuntimeException("Y is not a number");
        }
    }
}
