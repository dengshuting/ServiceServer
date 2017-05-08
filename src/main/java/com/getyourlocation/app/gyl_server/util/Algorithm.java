package com.getyourlocation.app.gyl_server.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Localization algorithms.
 */
public class Algorithm {
    private static Random random = new Random();

    /**
     * Triangular positioning algorithm.
     *
     * @return double[0]: x-coordinate
     *         double[1]: y-coordinate
     */
    public static double[] triangular(double alpha, double beta,
                                      double x1, double y1,
                                      double x2, double y2,
                                      double x3, double y3) {
        double a = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
        double b = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
//        double theta = Math.toDegrees(Math.acos((x2 * x2 + y2 * y2 - x1 * x2 - y1 * y2 - x2 * x3 - y2 * y3 + x1 * x3 + y1 * y3) / (a * b)));  // paper
        double theta = Math.toDegrees(Math.acos(((x3 - x2) * (x1 - x2) + (y3 - y2) * (x3 - x2)) / (a * b)));

        double cotA = 1 / Math.tan(Math.toRadians(alpha));
        double sinB = Math.sin(Math.toRadians(beta));
        double sinBT = Math.sin(Math.toRadians(beta + theta));
        double cosBT = Math.cos(Math.toRadians(beta + theta));

        double x0 = (a * b * (sinBT * cotA + cosBT) * (a * sinB * cotA + b * cosBT)) / (Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2));
        double y0 = (a * b * (sinBT * cotA + cosBT) * (b * sinBT - a * sinB)) / (Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2));

        double x = x0 * ((x3 - x2) / a) - y0 * ((y3 - y2) / a) + x2;
        double y = x0 * ((y3 - y2) / a) + y0 * ((x3 - x2) / a) + y2;

        return new double[]{x, y};
    }

    /**
     * Return the distance between two 2-dimension points.
     */
    public static double distance(double[] point1, double[] point2) {
        double dx = point1[0] - point2[0];
        double dy = point1[1] - point2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Return a uniformly distributed variable in the interval [min, max).
     */
    public static double randomDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }
}
