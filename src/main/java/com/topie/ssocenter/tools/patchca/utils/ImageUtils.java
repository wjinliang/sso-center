package com.topie.ssocenter.tools.patchca.utils;

import com.topie.ssocenter.tools.patchca.background.SingleColorBackgroundFactory;
import com.topie.ssocenter.tools.patchca.color.SingleColorFactory;
import com.topie.ssocenter.tools.patchca.encoder.EncoderHelper;
import com.topie.ssocenter.tools.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.topie.ssocenter.tools.patchca.service.Captcha;
import com.topie.ssocenter.tools.patchca.service.ConfigurableCaptchaService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;

/**
 * Created by cgj on 2016/4/11.
 */
public class ImageUtils {
    public static String getPatchcaString(HttpServletResponse response) throws IOException {
        return getCaptcha(response).getChallenge();
    }

    public static Captcha getCaptcha(HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
        cs.setBackgroundFactory(new SingleColorBackgroundFactory(new Color(221, 227, 236)));
        Captcha captcha = cs.getCaptcha();
        ImageIO.write(captcha.getImage(), "png", response.getOutputStream());
        return captcha;
    }
}
