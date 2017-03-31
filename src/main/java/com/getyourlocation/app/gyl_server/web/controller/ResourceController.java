package com.getyourlocation.app.gyl_server.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;


@RestController
@RequestMapping("/")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        return "Hello World!";
    }

    @RequestMapping(path = "/json", method = RequestMethod.GET)
    public LinkedHashMap<String, Object> testJson(HttpServletRequest request, HttpServletResponse response) {
        LOG.info(request.getMethod() + " " + request.getRequestURI());
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        result.put("msg", "Hello World!");
        return result;
    }

}
