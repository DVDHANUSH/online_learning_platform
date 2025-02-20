package com.elearn.app.start_learn_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        httpSecurity.authorizeHttpRequests(auth -> {
//            auth.requestMatchers(HttpMethod.GET, "/api/v1/categories")
//                    .permitAll()
//                    .requestMatchers("/client-login-process", "/client-login")
//                    .permitAll()
//                    .requestMatchers("/api/v1/users/**")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated();
//        });
        // we are specifying the access pattern, how
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "api/v1/**").hasRole((AppConstants.ROLE_GUEST))
                    .requestMatchers(HttpMethod.PUT).hasRole(AppConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.POST, "/api/v1/**").hasRole(AppConstants.ROLE_ADMIN)
                    .anyRequest()
                    .authenticated();
            // this means any user who is having role as guest, can only access api calls whose request is "HttpMethod.GET" only
        });
        httpSecurity.formLogin(form -> {
            form.loginPage("/client-login");
            form.usernameParameter("username");
            form.passwordParameter("userpassword");
            form.loginProcessingUrl("/client-login-process");
            // this is the url that gets displayed when successfully logged in, it displays the "success.html" page as the below line suggests spring to redirect to "/success"
            form.successForwardUrl("/success");
            // this is the actual url that gets mapped and sent
//            form.successHandler();
//            form.failureHandler();
        });

        // httpSecurity.httpBasic(Customizer.withDefaults());
//        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.logout(logout -> {

            logout.logoutSuccessUrl("/logout");

        });
        return httpSecurity.build();
    }
}