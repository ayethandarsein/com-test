/*
 * URLSuccessHandler
 */
package com.panu.competitor.information.infra.security;


import com.panu.competitor.information.common.ConstantCommon;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 *
 * @author HNDK
 */
public class URLSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * onAuthenticationSuccess
     * 
     * main redirect function of the systems by Spring Security
     * @param request
     * @param response
     * @param authentication
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(ConstantCommon.ROLE_ADMIN)) {
            response.sendRedirect(request.getContextPath()
                    + "/view/admin/user/CompetitorUserDataTable.xhtml");
        }
        else if (roles.contains(ConstantCommon.ROLE_MARKETING)) {
            response.sendRedirect(request.getContextPath()
                    + "/view/marketing/map/DashboardMap.xhtml");
        }
        else if (roles.contains(ConstantCommon.ROLE_SALES)) {
            response.sendRedirect(request.getContextPath()
                    + "/view/marketing/map/DashboardMap.xhtml");
        }
    }
}
