package com.getyourlocation.app.serviceserver.util;

import com.getyourlocation.app.serviceserver.business.entity.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


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
        double x = 0, y = 0;

        {   // ToTal algorithm: http://www.telecom.ulg.ac.be/triangulation
            double tmpX1 = x1 - x2;
            double tmpY1 = y1 - y2;
            double tmpX3 = x3 - x2;
            double tmpY3 = y3 - y2;
            double t12 = cot(beta);
            double t23 = cot(alpha);
            double t31 = (1 - t12 * t23) / (t12 + t23);
            double tmpX12 = tmpX1 + t12 * tmpY1;
            double tmpY12 = tmpY1 - t12 * tmpX1;
            double tmpX23 = tmpX3 - t23 * tmpY3;
            double tmpY23 = tmpY3 + t23 * tmpX3;
            double tmpX31 = tmpX3 + tmpX1 + t31 * (tmpY3 - tmpY1);
            double tmpY31 = tmpY3 + tmpY1 - t31 * (tmpX3 - tmpX1);
            double tmpK31 = tmpX1 * tmpX3 + tmpY1 * tmpY3 + t31 * (tmpX1 * tmpY3 - tmpX3 * tmpY1);
            double d = (tmpX12 - tmpX23) * (tmpY23 - tmpY31) - (tmpY12 - tmpY23) * (tmpX23 - tmpX31);
            x = x2 + tmpK31 * (tmpY12 - tmpY23) / d;
            y = y2 + tmpK31 * (tmpX23 - tmpX12) / d;
        }

        {   // Sextant Algorithm
//            double a = Point.dist(p2, p3);
//            double b = Point.dist(p2, p1);
//            double theta = Math.toDegrees(Math.acos((x2 * x2 + y2 * y2 - x1 * x2 - y1 * y2 -
//                x2 * x3 - y2 * y3 +x1 * x3 + y1 * y3) / (a * b)));
//            double cotA = cot(alpha);
//            double sinB = Math.sin(Math.toRadians(beta));
//            double sinBT = Math.sin(Math.toRadians(beta + theta));
//            double cosBT = Math.cos(Math.toRadians(beta + theta));
//            double denominator = Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2);
//            double x0 = a * b * (sinBT * cotA + cosBT) * (a * sinB * cotA + b * cosBT) / denominator;
//            double y0 = a * b * (sinBT * cotA + cosBT) * (b * sinBT - a * sinB) / denominator;
//            x = x0 * ((x3 - x2) / a) - y0 * ((y3 - y2) / a) + x2;
//            y = x0 * ((y3 - y2) / a) + y0 * ((x3 - x2) / a) + y2;
        }

        return new Point(x, y);
    }

    /**
     * Return a uniformly distributed variable in the interval [min, max).
     */
    public static double randDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * Return the cotangent value.
     *
     * @param x The angle in degrees.
     */
    public static double cot(double x) {
        return 1.0 / Math.tan(Math.toRadians(x));
    }
}
