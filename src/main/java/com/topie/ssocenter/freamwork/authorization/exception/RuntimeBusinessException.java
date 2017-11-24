package com.topie.ssocenter.freamwork.authorization.exception;

import com.topie.ssocenter.common.exception.BusinessException;

/**
 * Created by cgj on 2016/4/10.
 */
public class RuntimeBusinessException extends BusinessException {
    @Override
    protected String getPropertiesPath() {
        return "/config/properties/business_code.properties";
    }

    public RuntimeBusinessException(int errCode) {
        super(errCode);
    }

    public RuntimeBusinessException(String message) {
        super(message);
    }
}
