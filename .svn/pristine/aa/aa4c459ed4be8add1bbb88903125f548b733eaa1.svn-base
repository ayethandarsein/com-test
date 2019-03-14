/**
 * CurrentLoggedInUser
 */
package com.panu.competitor.information.common;

import com.panu.competitor.information.pojo.entity.CompetitorUser;
import com.panu.competitor.information.pojo.entity.LogInUser;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author HNDK
 */
@Named
public class CurrentLoggedInUser {

    SecurityContext securityContext;
    LogInUser logInUser;

    public void init() {

        getLogInUserId();
        userAuthorities();
        getCurrentUser();
    }
    /**
     * getLogInUserId
     * get log in userId
     * @return 
     */
    public int getLogInUserId() {
        securityContext = SecurityContextHolder.getContext();
        if (null != securityContext.getAuthentication()) {
            logInUser = (LogInUser) securityContext.getAuthentication().getPrincipal();

            int logInUserId = logInUser.getUser().getUserId();
            return logInUserId;
        } else {
            return 0;
        }
    }
    /**
     * userAuthorities
     * get user authorities
     * @return 
     */
    public List<GrantedAuthority> userAuthorities() {
        securityContext = SecurityContextHolder.getContext();
        if (null != securityContext.getAuthentication()) {
            logInUser = (LogInUser) securityContext.getAuthentication().getPrincipal();
            List<GrantedAuthority> userAuthorities = new ArrayList<>(logInUser.getAuthorities());
            return userAuthorities;
        } else {
            return new ArrayList<>();
        }
    }
    /**
     * getCurrentUser
     * get current log in user data
     * @return 
     */
    public CompetitorUser getCurrentUser() {
        securityContext = SecurityContextHolder.getContext();
        if (null != securityContext.getAuthentication()) {
            logInUser = (LogInUser) securityContext.getAuthentication().getPrincipal();
            return logInUser.getUser();
        } else {
            return new CompetitorUser();
        }
    }
}
