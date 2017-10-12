package com.topie.ssocenter.freamwork.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import static com.topie.ssocenter.common.constant.ResponsePages.*;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/7 说明：
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/403")
    public String pageDenied(HttpServletResponse response) throws Exception {
        return ERROR_403;
    }

    @RequestMapping("/404")
    public String pageNotFound(HttpServletResponse response) throws Exception {
        return ERROR_404;
    }

    @RequestMapping("/500")
    public String internalServerError(HttpServletResponse response) throws Exception {
        return ERROR_500;
    }

    @RequestMapping("/400")
    public String paramError(HttpServletResponse response) throws Exception {
        return ERROR_400;
    }

    @RequestMapping("/401")
    public String unAuthError(HttpServletResponse response) throws Exception {
        return ERROR_401;
    }
}
