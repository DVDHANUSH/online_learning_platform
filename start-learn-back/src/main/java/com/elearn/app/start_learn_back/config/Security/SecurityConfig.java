package com.elearn.app.start_learn_back.config.Security;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // to enable method Level security
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCrypt for password hashing
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
        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole("GUEST", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")

                        .requestMatchers("/client-login-process", "/client-login").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized access\"}");
                        })
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
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