package com.niit.TodoList.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //type casting servlet request/response to Http request/response bcz we use Http request and response.
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)  servletResponse;
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        String header=httpServletRequest.getHeader("authorization");
        //it will fetch the token and complete header
        System.out.println(header);
        if (header==null || !header.startsWith("Bearer")){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            outputStream.println("Missing or Invalid Token");
            outputStream.close();
        } else {
            String jwtToken = header.substring(7); //here length of bearer is 6 and space getting the token and storing.
            String username = Jwts.parser().setSigningKey("authenticator").parseClaimsJws(jwtToken).getBody().getSubject();
            //parser to check the token signature with the same key we used to sign it.
            httpServletRequest.setAttribute("email", username);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
