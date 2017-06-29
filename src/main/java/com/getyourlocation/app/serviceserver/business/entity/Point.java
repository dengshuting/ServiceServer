package com.getyourlocation.app.serviceserver.business.entity;

import com.getyourlocation.app.serviceserver.util.PositionUtil;


/**
 * Point in rectangular coordinate.
 */
public class Point {
    public double x;
    public double y;

    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        this(x, y, false);
    }

    /**
     * Initialize a Point. If @param polar is true, the two arguments
     * are the radius and angle (in degrees) in polar coordinate.
     * Otherwise they are the x-coordinate and y-coordinate in rectangular
     * coordinate.
     */
    public Point(double arg1, double arg2, boolean polar) {
        if (Double.isNaN(arg1) || Double.isNaN(arg2)) {
            this.x = this.y = 0;
        } else{
            if (polar) {
                this.x = arg1 * Math.cos(Math.toRadians(arg2));
                this.y = arg1 * Math.sin(Math.toRadians(arg2));
            } else {
                this.x = arg1;
                this.y = arg2;
            }
        }
    }

    /**
     * Add offset to two coordinates.
     */
    public void addOffset(double offset) {
        x += offset;
        y += offset;
    }

    /**
     * Return the distance between two points.
     */
    public static double dist(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Generate a point with random coordinates.
     */
    public static Point rand(double min, double max) {
        return new Point(PositionUtil.randDouble(min, max), PositionUtil.randDouble(min, max));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
