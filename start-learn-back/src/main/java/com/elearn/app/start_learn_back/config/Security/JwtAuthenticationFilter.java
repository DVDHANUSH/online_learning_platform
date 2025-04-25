package com.elearn.app.start_learn_back.config.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// if any hacker wants to access our apis directly, hence we are guarding it with this filter
// okayy now, wat does this "JwtAuthenticationFilter" do, simple it checks whether the request has the "jwtToken" and is it valid enough to send the response.
// if it is authenticated, we are good to send the request to next level.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
   @Autowired
   private JwtUtil jwtUtil;
   @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Autorization - Bearer 2345wfhhfihi - we are sending this along with the header.
       String authorizationHeader = request.getHeader("Authorization");
       System.out.println("the header retrieved - " +authorizationHeader);
       String userName = null;
       String jwtToken = null;
       if(authorizationHeader != null && authorizationHeader.startsWith("Bearer"))
       {
           // sab kuch shi hai karenge ...
           jwtToken= authorizationHeader.substring(7);
           userName = jwtUtil.extractUsername(jwtToken);
           // we are extracting the token means we are validating it. Therefore if we are able to extract the "userName" then we are validating it.
           if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
               // validate
              UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
              if(jwtUtil.validateToken(jwtToken, userDetails.getUsername())){
                  // authentication to security
                  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
              }
           }
//       }else{
//           throw new InsufficientAuthenticationException("Invalid Bearer Token");
//
       }
       filterChain.doFilter(request, response);
    }
}