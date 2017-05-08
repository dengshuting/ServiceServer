package com.getyourlocation.app.gyl_server.business.entity;

import com.getyourlocation.app.gyl_server.util.Algorithm;


public class Point {
    public double x;
    public double y;

    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point rand(double min, double max) {
        return new Point(Algorithm.randDouble(min, max), Algorithm.randDouble(min, max));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
