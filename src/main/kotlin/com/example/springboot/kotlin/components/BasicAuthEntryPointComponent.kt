package com.example.springboot.kotlin.components

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class BasicAuthEntryPointComponent: BasicAuthenticationEntryPoint() {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        response?.apply {
            addHeader("WWW-Authenticate", "Basic realm=\"$realmName\"")
            status = HttpServletResponse.SC_UNAUTHORIZED
            writer.println("HTTP Status 401")
        }
    }

    override fun afterPropertiesSet() {
        realmName = "Test"
        super.afterPropertiesSet()
    }
}
