package com.topie.ssocenter.common.utils;

import java.io.IOException;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/7 说明：
 */
public class PropertiesUtil {
    public static String get(String properties, String key) {
        try {
            //Properties props = PropertiesLoaderUtils.loadAllProperties("jdbc.properties");
            return PropertiesLoaderUtils.loadAllProperties(properties).getProperty(key, "未识别的业务异常");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * business_code.properties
     * @param key
     * @return
     */
    public static String get(String key) {
        try {
           // Properties props = PropertiesLoaderUtils.loadAllProperties("jdbc.properties");
            return PropertiesLoaderUtils.loadAllProperties("business_code.properties").getProperty(key, "未识别的业务异常");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
