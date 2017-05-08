package test.algorithm;

import com.getyourlocation.app.gyl_server.business.entity.Point;
import com.getyourlocation.app.gyl_server.util.Algorithm;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class TriangularTest extends BaseTest {
    private static final Logger Log = LoggerFactory.getLogger(TriangularTest.class);
    private static final int RANDOM_TEST_COUNT = 3;
    private static final double MIN_COORD = 0;
    private static final double MAX_COORD = 100;

//    @Test
    public void testFixedData() throws Exception {
        Log.info("Fixed data:");
        runTriangular(1, new Point(0, 1), 45, 45, new Point(-1, 0), new Point(0, -1), new Point(1, 0));
        runTriangular(2, new Point(0, 1), 60, 60, new Point(-1.732, 0), new Point(0, -1), new Point(1.732, 0));
        runTriangular(3, new Point(-1, 1), 45, 45, new Point(-1, -1), new Point(1, -1), new Point(1, 1));
        runTriangular(4, new Point(0, 3), 90, 53, new Point(-4, 0), new Point(0, 0), new Point(4, 3));
        runTriangular(5, new Point(1.6, 4), 25.43, 76.9, new Point(-1.8, 3.8), new Point(1, 0.5), new Point(2.5, 0.8));
    }

    @Test
    public void testRandomData() throws Exception {
        Log.info("Random data:");
        double totDist = 0, center = (MIN_COORD + MAX_COORD) / 2;
        Point p = new Point(center, center);
        for (int i = 0; i < RANDOM_TEST_COUNT; i++) {
            Point p1 = Point.rand(MIN_COORD, MAX_COORD);
            Point p2 = Point.rand(MIN_COORD, MAX_COORD);
            Point p3 = Point.rand(MIN_COORD, MAX_COORD);
            double R1R2 = Algorithm.distance(p1, p2);
            double R2R3 = Algorithm.distance(p2, p3);
            double PR1 = Algorithm.distance(p, p1);
            double PR2 = Algorithm.distance(p, p2);
            double PR3 = Algorithm.distance(p, p3);
            double alpha = Math.toDegrees(Math.acos((PR3 * PR3 + PR2 * PR2 - R2R3 * R2R3) / (2 * PR3 * PR2)));
            double beta = Math.toDegrees(Math.acos((PR1 * PR1 + PR2 * PR2 - R1R2 * R1R2) / (2 * PR1 * PR2)));
            totDist += runTriangular(i + 1, p, alpha, beta, p1, p2, p3);
        }
        Log.info("Avg dist: " + totDist / RANDOM_TEST_COUNT);
    }

    private double runTriangular(int cnt, Point expect, double alpha, double beta, Point p1, Point p2, Point p3) {
        Point ans = Algorithm.triangular(alpha, beta, p1, p2, p3);
        double dist = Algorithm.distance(ans, expect);
        Log.info("#" + cnt);
        Log.info(" alpha: " + alpha);
        Log.info("  beta: " + beta);
        Log.info("point1: " + p1.toString());
        Log.info("point2: " + p2.toString());
        Log.info("point3: " + p3.toString());
        Log.info("   ans: " + ans.toString());
        Log.info("expect: " + expect.toString());
        Log.info("  dist: " + dist);
        return dist;
    }
}
