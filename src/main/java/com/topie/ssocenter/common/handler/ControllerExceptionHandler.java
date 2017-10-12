package com.topie.ssocenter.common.handler;

import com.topie.ssocenter.common.constant.ResponsePages;
import com.topie.ssocenter.common.utils.HttpResponseUtil;
import com.topie.ssocenter.common.utils.RequestUtil;
import com.topie.ssocenter.common.utils.ResponseUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/6 说明：
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    @Value("${security.token.header}")
    private String header;

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(HttpServletResponse response, HttpServletRequest request, Exception e) {
        logger.debug("捕获到异常");
        if (RequestUtil.isAjax(request)) {
            logger.debug("异常来源请求为：{}", "ajax请求");
            ResponseUtil.error(response, e.getMessage());
        } else {
            logger.debug("异常来源请求为：{}", "页面请求");
            String header = response.getHeader(this.header);
            if (StringUtils.isNotEmpty(header)) {
                try {
                    HttpResponseUtil.writeJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                ModelAndView modelAndView = new ModelAndView(ResponsePages.ERROR_500);
                modelAndView.addObject("message", e.getMessage());
                return modelAndView;
            }
        }
        return null;
    }
}
