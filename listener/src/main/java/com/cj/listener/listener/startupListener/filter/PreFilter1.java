package com.cj.listener.listener.startupListener.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//方法1, 直接让自己成为一个BEAN.
@Component
@Slf4j
public class PreFilter1 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("@@@PreFilter1 init...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("@@@PreFilter1 doFilter...");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.info("@@@PreFilter1 destroy...");
    }
}
