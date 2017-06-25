package test.algorithm;

import com.getyourlocation.app.gylserver.business.entity.Point;
import com.getyourlocation.app.gylserver.util.Algorithm;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Ignore
public class TriangularTest extends BaseTest {
    private static final Logger Log = LoggerFactory.getLogger(TriangularTest.class);
    private static final int RANDOM_TEST_COUNT = 1000;
    private static final double MIN_RADIUS = 1;
    private static final double MAX_RADIUS = 1000;
    private static final double ORIGIN_OFFSET = 50;

    @Test
    public void testFixedData() throws Exception {
        Log.info("Fixed data:");
        runTriangular(1, new Point(0, 1), 45, 45, new Point(-1, 0), new Point(0, -1), new Point(1, 0));
        runTriangular(2, new Point(0, 1), 60, 60, new Point(-1.732, 0), new Point(0, -1), new Point(1.732, 0));
        runTriangular(3, new Point(-1, 1), 45, 45, new Point(-1, -1), new Point(1, -1), new Point(1, 1));
        runTriangular(4, new Point(0, 3), 90, 53, new Point(-4, 0), new Point(0, 0), new Point(4, 3));
        runTriangular(5, new Point(1.6, 4), 25.43, 76.9, new Point(-1.8, 3.8), new Point(1, 0.5), new Point(2.5, 0.8));
        runTriangular(6, new Point(0, 0), 45, 45, new Point(10, 0), new Point(10, 10), new Point(0, 10));
    }

    @Test
    public void testRandomData() throws Exception {
        Log.info("Random data:");
        double totDist = 0;
        Point p = new Point(ORIGIN_OFFSET, ORIGIN_OFFSET);
        for (int i = 0; i < RANDOM_TEST_COUNT; i++) {
            double theta = Algorithm.randDouble(0, 360);
            Point p1 = new Point(Algorithm.randDouble(MIN_RADIUS, MAX_RADIUS), theta, true);
            p1.addOffset(ORIGIN_OFFSET);
            double beta = Algorithm.randDouble(0, 180);
            theta += beta;
            Point p2 = new Point(Algorithm.randDouble(MIN_RADIUS, MAX_RADIUS), theta, true);
            p2.addOffset(ORIGIN_OFFSET);
            double alpha = Algorithm.randDouble(0, 180);
            theta += alpha;
            Point p3 = new Point(Algorithm.randDouble(MIN_RADIUS, MAX_RADIUS), theta, true);
            p3.addOffset(ORIGIN_OFFSET);
            double R1R2 = Point.dist(p1, p2);
            double R2R3 = Point.dist(p2, p3);
            double PR1 = Point.dist(p, p1);
            double PR2 = Point.dist(p, p2);
            double PR3 = Point.dist(p, p3);
            totDist += runTriangular(i + 1, p, alpha, beta, p1, p2, p3);
        }
        Log.info("Avg dist: " + totDist / RANDOM_TEST_COUNT);
    }

    private double runTriangular(int cnt, Point expect, double alpha, double beta, Point p1, Point p2, Point p3) {
        Point ans = Algorithm.triangular(alpha, beta, p1, p2, p3);
        double dist = Point.dist(ans, expect);
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
