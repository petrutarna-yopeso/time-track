package com.example.configuration.interceptors;

import com.example.anotations.Authorize;
import com.example.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    private static final String AUTH_HEADER_PARAMETER_BEARER = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Authorize authRequired = handlerMethod.getMethod().getAnnotation(Authorize.class);

        if (Objects.nonNull(authRequired)) {

            String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(auth == null || !authService.validateJwtToken(auth.replace(AUTH_HEADER_PARAMETER_BEARER, ""))) {
                response.reset();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{ message: 'No token provided.' }");
                return false;
            }
        }

        return true;
    }
}
