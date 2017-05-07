package test;

import com.getyourlocation.app.gyl_server.util.Algorithm;
import com.getyourlocation.app.gyl_server.web.controller.ViewController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AlgorithmTest extends BaseTest {

    private static final Logger Log = LoggerFactory.getLogger(AlgorithmTest.class);
    private int triangularCnt = 1;

    @Test
    public void testTriangular() throws Exception {
        testTriangular(45, 45, -1, 0, 0, -1, 1, 0, new double[]{0, 1});
        testTriangular(60, 60, -1.732, 0, 0, -1, 1.732, 0, new double[]{0, 1});
        testTriangular(45, 45, -1, -1, 1, -1, 1, 1, new double[]{-1, 1});
        testTriangular(90, 53, -4, 0, 0, 0, 4, 3, new double[]{0, 3});
        testTriangular(25.43, 76.9, -1.8, 3.8, 1, 0.5, 2.5, 0.8, new double[]{1.6, 4});
    }

    private void testTriangular(double alpha, double beta,
                                double x1, double y1,
                                double x2, double y2,
                                double x3, double y3, double[] expect) {
        double[] ans = Algorithm.triangular(alpha, beta, x1, y1, x2, y2, x3, y3);
        Log.info("#" + triangularCnt++);
        Log.info("   ans: (" + ans[0] + ", " + ans[1] + ")");
        Log.info("expect: (" + expect[0] + ", " + expect[1] + ")");
        Log.info("  dist: " + Algorithm.distance(ans, expect));
    }
}
