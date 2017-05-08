package com.getyourlocation.app.gyl_server.util;


import java.util.HashMap;
import java.util.Map;

/**
 * Localization algorithms.
 */
public class Algorithm {

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
//W        double theta = Math.toDegrees(Math.acos((x2 * x2 + y2 * y2 - x1 * x2 - y1 * y2 - x2 * x3 - y2 * y3 + x1 * x3 + y1 * y3) / (a * b)));  // paper
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

    public static Map<String, Double> generateData() {
        Map<String, Double> data = new HashMap<String, Double>();
        double x1 = 10 * Math.random();
        double y1 = 10 * Math.random();
        double x2 = 10 * Math.random();
        double y2 = 10 * Math.random();
        double x3 = 10 * Math.random();
        double y3 = 10 * Math.random();
        double x = 10 * Math.random();
        double y = 10 * Math.random();
        double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double b = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
        double c = Math.sqrt((x - x3) * (x - x3) + (y - y3) * (y - y3));
        double d = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
        double e = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        double alpha = Math.toDegrees(Math.acos((c * c + e * e - b * b) / (2 * c * e)));
        double beta = Math.toDegrees(Math.acos((d * d + e * e - a * a) / (2 * d * e)));
        data.put("x1", x1);
        data.put("y1", y1);
        data.put("x2", x2);
        data.put("y2", y2);
        data.put("x3", x3);
        data.put("y3", y3);
        data.put("x", x);
        data.put("y", y);
        data.put("alpha", alpha);
        data.put("beta", beta);
        return data;
    }
}
