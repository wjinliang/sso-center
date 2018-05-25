package com.topie.ssocenter.tools.patchca.utils;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.topie.ssocenter.tools.patchca.background.SingleColorBackgroundFactory;
import com.topie.ssocenter.tools.patchca.color.SingleColorFactory;
import com.topie.ssocenter.tools.patchca.filter.predefined.RippleFilterFactory;
import com.topie.ssocenter.tools.patchca.service.Captcha;
import com.topie.ssocenter.tools.patchca.service.ConfigurableCaptchaService;
import com.topie.ssocenter.tools.patchca.word.AdaptiveRandomWordFactory;

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
        AdaptiveRandomWordFactory  rw= new AdaptiveRandomWordFactory();
        rw.setCharacters("acdefhjkmnpstuwxy34578");
        cs.setWordFactory(rw);
        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setFilterFactory(new RippleFilterFactory());
        cs.setBackgroundFactory(new SingleColorBackgroundFactory(new Color(221, 227, 236)));
        Captcha captcha = cs.getCaptcha();
        ImageIO.write(captcha.getImage(), "png", response.getOutputStream());
        return captcha;
    }
}
