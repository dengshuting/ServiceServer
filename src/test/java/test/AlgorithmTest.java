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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AlgorithmTest extends BaseTest {

    private static final Logger Log = LoggerFactory.getLogger(AlgorithmTest.class);
    private int triangularCnt = 1;
    private double disTotal = 0;

    @Test
    public void testTriangular() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Map<String, Double> data = Algorithm.generateData();
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
            testTriangular(alpha, beta, x1, y1, x2, y2, x3, y3, new double[]{x, y});
        }
    }

    private void testTriangular(double alpha, double beta,
                                double x1, double y1,
                                double x2, double y2,
                                double x3, double y3, double[] expect) {
        double[] ans = Algorithm.triangular(alpha, beta, x1, y1, x2, y2, x3, y3);
        Log.info("#" + triangularCnt++);
        Log.info("   ans: (" + ans[0] + ", " + ans[1] + ")");
        Log.info("expect: (" + expect[0] + ", " + expect[1] + ")");
        double dis = Algorithm.distance(ans, expect);
        Log.info("  dist: " + dis);
        if (dis >= 0) disTotal += dis;
        Log.info("  total dis: " + disTotal);
    }

}
