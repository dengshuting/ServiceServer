package com.getyourlocation.app.gyl_server.util;


import com.getyourlocation.app.gyl_server.business.entity.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Localization algorithms.
 */
public class Algorithm {
    private static final Logger Log = LoggerFactory.getLogger(Algorithm.class);
    private static Random random = new Random();

    /**
     * Triangular localization algorithm.
     *
     * @return The user's position
     */
    public static Point triangular(double alpha, double beta, Point p1, Point p2, Point p3) {
        double x1 = p1.x, y1 = p1.y;
        double x2 = p2.x, y2 = p2.y;
        double x3 = p3.x, y3 = p3.y;

        double a = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
        double b = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double theta = Math.toDegrees(Math.acos((x2 * x2 + y2 * y2 - x1 * x2 - y1 * y2 - x2 * x3 - y2 * y3 + x1 * x3 + y1 * y3) / (a * b)));
//        double theta = Math.toDegrees(Math.acos(((x3 - x2) * (x1 - x2) + (y3 - y2) * (x3 - x2)) / (a * b)));  // paper

        double cotA = 1 / Math.tan(Math.toRadians(alpha));
        double sinB = Math.sin(Math.toRadians(beta));
        double sinBT = Math.sin(Math.toRadians(beta + theta));
        double cosBT = Math.cos(Math.toRadians(beta + theta));

        double x0 = (a * b * (sinBT * cotA + cosBT) * (a * sinB * cotA + b * cosBT)) / (Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2));
        double y0 = (a * b * (sinBT * cotA + cosBT) * (b * sinBT - a * sinB)) / (Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2));

        double x = x0 * ((x3 - x2) / a) - y0 * ((y3 - y2) / a) + x2;
        double y = x0 * ((y3 - y2) / a) + y0 * ((x3 - x2) / a) + y2;

        return new Point(x, y);
    }

    /**
     * Return the distance between two 2-dimension points.
     */
    public static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Return a uniformly distributed variable in the interval [min, max).
     */
    public static double randDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }
}
