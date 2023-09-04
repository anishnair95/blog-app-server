package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom JwtAuthenticationFilter
 *
 * This class will execute once per request
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;


    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // get JWT token from http request
        String token = getTokenFromRequest1(request);

        // validate jwt token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // get username from token
            String username = jwtTokenProvider.getUsername(token);

            // load the user associated with the token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //create UsernamePasswordAuthentication object with all the details
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            // set the authentication details from httpservlet request to this object as web source details
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // store the auth in security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    // TODO: apply this function
    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            String tokenArray[] = bearerToken.split("");

            if (tokenArray.length == 2 && tokenArray[1].trim().length() > 0) {
                return tokenArray[1];
            }
        }
        throw new BlogApiException(HttpStatus.BAD_REQUEST, "no bearer token");
    }

    private String getTokenFromRequest1(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
//        throw new BlogApiException(HttpStatus.BAD_REQUEST, "no bearer token");
    }

}
