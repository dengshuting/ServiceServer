package com.getyourlocation.app.gyl_server.web.controller;

import com.getyourlocation.app.gyl_server.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class APIController {

    private static final String DIR_UPLOAD = "upload_files";
    private static final Logger LOG = LoggerFactory.getLogger(APIController.class);

    @RequestMapping(path = "/sum", method = RequestMethod.GET)
    public LinkedHashMap<String, Object> sum(
        @RequestParam(value = "x", defaultValue = "0") int x,
        @RequestParam(value = "y", defaultValue = "0") int y,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("ans", x + y);
        return result;
    }

    @RequestMapping(path = "/product", method = RequestMethod.POST)
    public LinkedHashMap<String, Object> product(
        @RequestParam(value = "x", defaultValue = "0") int x,
        @RequestParam(value = "y", defaultValue = "0") int y,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("ans", x * y);
        return result;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public List<String> upload(
        @RequestParam(value = "file", required = true) MultipartFile[] files,
        @RequestParam(value = "ext", required = false) String ext,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        List<String> result = new ArrayList<String>();
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().equals("")) {
                continue;
            }
            String contentType = file.getContentType();
            String extension = contentType.substring(contentType.indexOf("/") + 1);
            if (extension.equals("octet-stream")) {
                extension = ext;
            }
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
            String path = request.getSession().getServletContext().getRealPath(DIR_UPLOAD);
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            try {
                file.transferTo(targetFile);  // Save to disk
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.add(request.getContextPath() + "/" + DIR_UPLOAD + "/" + fileName);
        }
        return result;
    }

    @RequestMapping(path = "/localization", method = RequestMethod.GET)
    public LinkedHashMap<String, Object> localization(
            //** case 1: alpha=45, beta=45, (x1,y1)=(-1,0), (x2,y2)=(0,-1), (x3,y3)=(1,0), expected:P(0,1),actual:P(0,0.4142)
            // case 2: alpha=60, beta=60, (x1,y1)=(-1.732,0), (x2,y2)=(0,-1), (x3,y3)=(1.732,0), expected:P(0,1),actual:P(~0,0.99)/(0.211,1.095)(original)
            // case 3: alpha=45, beta=45, (x1,y1)=(-1,-1), (x2,y2)=(1,-1), (x3,y3)=(1,1), expected:P(-1,1), actual:P(-1,1)
            // case 4: alpha=90, beta=53, (x1,y1)=(-4,0), (x2,y2)=(0,0), (x3,y3)=(4,3), expected:P(0,3), actual:P(0.010,2.999)
            // case 4: alpha=25.43, beta=76.9, (x1,y1)=(-1.8,3.8), (x2,y2)=(1,0.5), (x3,y3)=(2.5,0.8), expected:P(1.6,4), actual:P(1.599,4.00)
        @RequestParam(value = "alpha", defaultValue = "25.43") double alpha,
        @RequestParam(value = "beta", defaultValue = "76.9") double beta,
        @RequestParam(value = "x1", defaultValue = "-1.8") double x1,
        @RequestParam(value = "y1", defaultValue = "3.8") double y1,
        @RequestParam(value = "x2", defaultValue = "1") double x2,
        @RequestParam(value = "y2", defaultValue = "0.5") double y2,
        @RequestParam(value = "x3", defaultValue = "2.5") double x3,
        @RequestParam(value = "y3", defaultValue = "0.8") double y3,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

        // TODO 定位
        double a = Math.sqrt((x3-x2)*(x3-x2) + (y3-y2)*(y3-y2));
        double b = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
        //double theta = Math.toDegrees(Math.acos((x2*x2 + y2*y2 - x1*x2 - y1*y2 - x2*x3 - y2*y3 + x1*x3 + y1*y3) / (a*b)));  // paper
        double theta = Math.toDegrees(Math.acos(((x3-x2)*(x1-x2)+(y3-y2)*(x3-x2))/(a*b)));

        double cotA = 1 / Math.tan(Math.toRadians(alpha));
        double sinB = Math.sin(Math.toRadians(beta));
        double sinBT = Math.sin(Math.toRadians(beta + theta));
        double cosBT = Math.cos(Math.toRadians(beta + theta));

        double x0 = (a * b * (sinBT * cotA + cosBT) * (a * sinB * cotA + b * cosBT)) / (Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2));
        double y0 = (a * b * (sinBT * cotA + cosBT) * (b * sinBT - a * sinB)) / (Math.pow(b * sinBT - a * sinB, 2) + Math.pow(b * cosBT + a * sinB * cotA, 2));

        float x = (float)(x0 * ((x3-x2)/a) - y0 * ((y3-y2)/a) + x2);
        float y = (float)(x0 * ((y3-y2)/a) + y0 * ((x3-x2)/a) + y2);

        result.put("x", x);
        result.put("y", y);

        return result;
    }
}
