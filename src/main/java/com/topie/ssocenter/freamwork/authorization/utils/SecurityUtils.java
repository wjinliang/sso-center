package com.topie.ssocenter.freamwork.authorization.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.topie.ssocenter.freamwork.authorization.security.OrangeSideSecurityUser;

/**
 * Created by cgj on 2015/10/26.
 */
public class SecurityUtils {
    public static String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof OrangeSideSecurityUser)
            userName = ((OrangeSideSecurityUser) principal).getUsername();
        return userName;
    }

    public static OrangeSideSecurityUser getCurrentSecurityUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof OrangeSideSecurityUser)
            return (OrangeSideSecurityUser) principal;
        return null;
    }

    public static String encodeString(String character) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(character);
    }

    public static boolean matchString(String character, String encodedCharacter) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(character, encodedCharacter);
    }
}
