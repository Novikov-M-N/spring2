package com.github.novikovmn.spring2.security;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {
    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null && Stream.of(HandlerHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public static CustomPrincipal getPrincipal() {
        return ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
