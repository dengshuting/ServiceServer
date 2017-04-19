package com.getyourlocation.app.gyl_server.web.controller;

import com.getyourlocation.app.gyl_server.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/")
public class ViewController {

    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        return "index";
    }

    @RequestMapping(path = "/api", method = RequestMethod.GET)
    public String api(HttpServletRequest request, HttpServletResponse response) {
        LogUtil.logReq(LOG, request);
        return "api";
    }

}
