package test.algorithm;

import com.getyourlocation.app.gyl_server.util.Algorithm;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.BaseTest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class TriangularTest extends BaseTest {
    private static final Logger Log = LoggerFactory.getLogger(TriangularTest.class);
    private static final int RANDOM_TEST_COUNT = 100;
    private static final double MIN_COORD = 0;
    private static final double MAX_COORD = 100;
    private double totDist = 0;

    @Test
    public void fixData() throws Exception {
        Log.info("Test fix data:");
        runTriangular(1, new double[]{0, 1}, 45, 45, -1, 0, 0, -1, 1, 0);
        runTriangular(2, new double[]{0, 1}, 60, 60, -1.732, 0, 0, -1, 1.732, 0);
        runTriangular(3, new double[]{-1, 1}, 45, 45, -1, -1, 1, -1, 1, 1);
        runTriangular(4, new double[]{0, 3}, 90, 53, -4, 0, 0, 0, 4, 3);
        runTriangular(5, new double[]{1.6, 4}, 25.43, 76.9, -1.8, 3.8, 1, 0.5, 2.5, 0.8);
    }

    @Test
    public void randomData() throws Exception {
        Log.info("Test random data:");
        for (int i = 0; i < RANDOM_TEST_COUNT; i++) {
            Map<String, Double> data = genData();
            double x1 = data.get("x1");
            double y1 = data.get("y1");
            double x2 = data.get("x2");
            double y2 = data.get("y2");
            double x3 = data.get("x3");
            double y3 = data.get("y3");
            double x = data.get("x");
            double y = data.get("y");
            double alpha = data.get("alpha");
            double beta = data.get("beta");
            totDist += runTriangular(i + 1, new double[]{x, y}, alpha, beta, x1, y1, x2, y2, x3, y3);
        }
        Log.info("Avg dist: " + totDist / RANDOM_TEST_COUNT);
    }

    private double runTriangular(int cnt, double[] expect,
                                 double alpha, double beta,
                                 double x1, double y1,
                                 double x2, double y2,
                                 double x3, double y3) {
        double[] ans = Algorithm.triangular(alpha, beta, x1, y1, x2, y2, x3, y3);
        double dist = Algorithm.distance(ans, expect);
        Log.info("#" + cnt);
        Log.info("   ans: (" + ans[0] + ", " + ans[1] + ")");
        Log.info("expect: (" + expect[0] + ", " + expect[1] + ")");
        Log.info("  dist: " + dist);
        return dist;
    }

    private static Map<String, Double> genData() {
        Map<String, Double> data = new HashMap<String, Double>();
        double x1 = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double y1 = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double x2 = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double y2 = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double x3 = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double y3 = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double x = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
        double y = Algorithm.randomDouble(MIN_COORD, MAX_COORD);
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
