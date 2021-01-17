package com.model;

import lombok.Getter;
import lombok.Setter;


public class Point
{
    private String x;
    private String y;
    private String r;

    public Point(String x, String y, String r)
    {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public void setX(String x)
    {
        this.x = x;
    }

    public void setY(String y)
    {
        this.y = y;
    }

    public void setR(String r)
    {
        this.r = r;
    }

    public String getX()
    {
        return x;
    }

    public String getY()
    {
        return y;
    }

    public String getR()
    {
        return r;
    }
}
