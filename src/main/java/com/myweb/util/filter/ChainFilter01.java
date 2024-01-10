package com.myweb.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

// 1. filter를 상속받음
// 2. 필터요청을 등록(어노테이션 vs web.xml방법)
//@WebFilter("/*")
public class ChainFilter01 implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("체인필터1 실행됨");
		
		chain.doFilter(request, response); // 다음필터로 연결 or 컨트롤러 연결
		
		
	}
	
}
