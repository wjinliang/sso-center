package com.topie.ssocenter.common.exception;

import com.topie.ssocenter.common.utils.PropertiesUtil;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/7 说明：
 */
public abstract class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4793699346343027629L;

    private String err = "";

    public BusinessException(int errCode) {
        this.err = PropertiesUtil.get(getPropertiesPath(), String.valueOf(errCode));
    }

    public BusinessException(String message) {
        super(message);
        this.err = message;
    }

    protected abstract String getPropertiesPath();

    @Override
    public String getMessage() {
        return this.err;
    }
}
