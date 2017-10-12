package com.topie.ssocenter.freamwork.authorization.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topie.ssocenter.freamwork.authorization.model.UserAccount;

/**
 * Created by cgj on 2015/10/30.
 */
public class UserVO extends UserAccount {
    @Override
    @JsonIgnore
    public String getPassword() {
        return "";
    }
}
