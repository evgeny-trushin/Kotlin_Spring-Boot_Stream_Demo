package com.example.springboot.kotlin.filters

import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletResponse
import javax.servlet.ServletRequest
import org.springframework.web.filter.GenericFilterBean


class CustomFilter : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
            request: ServletRequest,
            response: ServletResponse,
            chain: FilterChain) {
        chain.doFilter(request, response)
    }
}
