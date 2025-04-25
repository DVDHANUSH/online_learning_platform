package com.elearn.app.start_learn_back.config.Security;

import com.elearn.app.start_learn_back.dtos.CustomMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // to enable method Level security
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCrypt for password hashing
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Using BCrypt for password hashing
    }
    //    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        // service ke andar create user
//        userDetailsManager.createUser(User
//                .withDefaultPasswordEncoder()
//                .username("dvd")
//                .password("dvd")
//                .roles("ADMIN")
//                .build()
//        );
//        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
//                .username("shyam")
//                .password("shyam123")
//                .roles("USER")
//                .build());
//        return userDetailsManager;
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.cors(AbstractHttpConfigurer::disable);
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        // we are specifying the access pattern, how
//        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/api/v1/**").hasRole("GUEST")  // Fix role name
//                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")  // Fix role name
//                .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")
////                .requestMatchers("/client-login-process", "/client-login")
////                .permitAll()
//                .anyRequest().authenticated());
//        httpSecurity.formLogin(form -> {
//            form.loginPage("/client-login");
//            form.usernameParameter("username");
//            form.passwordParameter("userpassword");
//            form.loginProcessingUrl("/client-login-process");
//            // this is the url that gets displayed when successfully logged in, it displays the "success.html" page as the below line suggests spring to redirect to "/success"
//            form.successForwardUrl("/success");
//            // this is the actual url that gets mapped and sent
////            form.successHandler();
////            form.failureHandler();
//        });
//        // httpSecurity.httpBasic(Customizer.withDefaults());
//       // httpSecurity.formLogin(Customizer.withDefaults());
//        httpSecurity.logout(logout -> {
//
//            logout.logoutSuccessUrl("/logout");
//        });
//        return httpSecurity.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.exceptionHandling(e ->
                e.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            CustomMessage customMessage = new CustomMessage();
                            customMessage.setMessage("You dont have permission to perform this operations !! " + accessDeniedException.getMessage());
                            customMessage.setSuccess(false);
                            String stringMessage = new ObjectMapper().writeValueAsString(customMessage);
                            response.getWriter().println(stringMessage);
                        })
        );

        httpSecurity
                // .cors(AbstractHttpConfigurer::disable)
                .cors(cor -> {
                    CorsConfiguration config = new CorsConfiguration();
//            config.addAllowedOrigin("http://localhost:5173");
         //           config.addAllowedOriginPattern("*");
                    config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:4200"));
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    config.setAllowCredentials(true);
                    config.setMaxAge(300L);
                    UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
                    configurationSource.registerCorsConfiguration("/**", config);

                    cor.configurationSource(configurationSource);
                })
                .csrf(AbstractHttpConfigurer::disable)
                // making it stateless means, it doesn't store anything in the server.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/doc.html","/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/api/v1/auth/login", "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("GUEST", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")

                        //  .requestMatchers("/client-login-process", "/client-login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized access\"}");
                        })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // to handle a case where a person is authenticated, not having few roles required will have to throw a exception
//                .httpBasic(Customizer.withDefaults()); // ✅ Enables Basic Authentication
//        httpSecurity.formLogin(form -> {

//            form.loginPage("/client-login");
//            form.usernameParameter("username");
//            form.passwordParameter("userpassword");
//            form.loginProcessingUrl("/client-login-process");
//            // this is the url that gets displayed when successfully logged in, it displays the "success.html" page as the below line suggests spring to redirect to "/success"
//            form.successForwardUrl("/success");
//            // this is the actual url that gets mapped and sent
////            form.successHandler();
////            form.failureHandler();
//        });
        return httpSecurity.build();
    }
}