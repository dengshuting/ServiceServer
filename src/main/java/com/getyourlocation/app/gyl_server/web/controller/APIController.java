package com.getyourlocation.app.gyl_server.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
        LOG.info(request.getMethod() + " " + request.getRequestURI());

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("ans", x + y);
        return result;
    }

    @RequestMapping(path = "/product", method = RequestMethod.POST)
    public LinkedHashMap<String, Object> product(
        @RequestParam(value = "x", defaultValue = "0") int x,
        @RequestParam(value = "y", defaultValue = "0") int y,
        HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("ans", x * y);
        return result;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public List<String> upload(
        @RequestParam(value = "file", required = true) MultipartFile[] files,
        @RequestParam(value = "ext", required = false) String ext,
        HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());

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
}
