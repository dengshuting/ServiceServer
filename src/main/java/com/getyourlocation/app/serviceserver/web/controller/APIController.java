package com.getyourlocation.app.serviceserver.web.controller;

import com.getyourlocation.app.serviceserver.business.entity.Point;
import com.getyourlocation.app.serviceserver.util.Algorithm;
import com.getyourlocation.app.serviceserver.util.LogUtil;
import com.getyourlocation.app.serviceserver.util.GetShopLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class APIController {
    private static final Logger Log = LoggerFactory.getLogger(APIController.class);
    private static final String DIR_UPLOAD = "uploaded_files";

    @RequestMapping(path = "/sum", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LinkedHashMap<String, Object> sum(
        @RequestParam(value = "x", defaultValue = "0") int x,
        @RequestParam(value = "y", defaultValue = "0") int y,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("ans", x + y);
        return result;
    }

    @RequestMapping(path = "/product", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LinkedHashMap<String, Object> product(
        @RequestParam(value = "x", defaultValue = "0") int x,
        @RequestParam(value = "y", defaultValue = "0") int y,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("ans", x * y);
        return result;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LinkedHashMap<String, Object> upload(
        @RequestParam(value = "file", required = true) MultipartFile[] files,
        @RequestParam(value = "ext", required = false) String ext,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        List<String> fileList = new ArrayList<String>();
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
            fileList.add(request.getContextPath() + "/" + DIR_UPLOAD + "/" + fileName);
        }
        result.put("files", fileList);
        return result;
    }

    @RequestMapping(path = "/shop-location", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LinkedHashMap<String, Object> shopLocation(
        @RequestParam(value = "img", required = true) MultipartFile img,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        byte[] imgByte = new byte[8*1024*1024];
        Point p = null;
        try {
            imgByte = img.getBytes();
            // TODO socket
            p = GetShopLocation.GetShopLocation(imgByte);

        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("x", p.x);
        result.put("y", p.y);
        return result;
    }

    @RequestMapping(path = "/positioning", method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LinkedHashMap<String, Object> positioning(
        @RequestParam(value = "alpha") double alpha,
        @RequestParam(value = "beta") double beta,
        @RequestParam(value = "x1") double x1,
        @RequestParam(value = "y1") double y1,
        @RequestParam(value = "x2") double x2,
        @RequestParam(value = "y2") double y2,
        @RequestParam(value = "x3") double x3,
        @RequestParam(value = "y3") double y3,
        HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(Log, request);
        Point ans = Algorithm.triangular(alpha, beta, new Point(x1, y1), new Point(x2, y2), new Point(x3, y3));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("x", ans.x);
        result.put("y", ans.y);
        return result;
    }
}
