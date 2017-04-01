package com.getyourlocation.app.gyl_server.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;


@RestController
@RequestMapping("/test")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @RequestMapping(path = "/sum", method = RequestMethod.POST)
    public LinkedHashMap<String, Object> testCompute(
        @RequestParam(value = "x", defaultValue = "0") int x,
        @RequestParam(value = "y", defaultValue = "0") int y,
        HttpServletRequest request, HttpServletResponse response) {

        LOG.info(request.getMethod() + " " + request.getRequestURI());

        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("answer", x + y);
        return result;

    }

}
