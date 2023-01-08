package com.example.aspect;

import com.example.auth.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthorizeAspect {
    @Autowired
    private AuthService authService;

    private static final String AUTH_HEADER_PARAMETER_BEARER = "Bearer ";

    @Around("@annotation(com.example.anotations.Authorize)")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {

        //BEFORE METHOD EXECUTION

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(auth == null || !authService.validateJwtToken(auth.replace(AUTH_HEADER_PARAMETER_BEARER, ""))) {
                throw new AuthenticationException("Bad JWT token");
            }

        //This is where ACTUAL METHOD will get invoke
        Object result = joinPoint.proceed();

        // AFTER METHOD EXECUTION
        System.out.println(result);
        return result;
    }

}
