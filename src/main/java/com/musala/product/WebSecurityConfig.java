package com.musala.product;

import com.musala.product.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                mvcMatchers(HttpMethod.GET, "/productapi/products/**", "/index",
                        "/showGetProduct", "/productDetails").permitAll().
//                hasAnyRole("USER", "ADMIN").
             //  mvcMatchers(HttpMethod.POST, "/getProduct").hasAnyRole("USER", "ADMIN").
               // mvcMatchers(HttpMethod.GET, "/createProduct", "/showCreateProduct", "/createResponse").hasAnyRole("ADMIN").
                mvcMatchers(HttpMethod.POST, "/productapi/products", "/createProduct","/createProduct", "/showCreateProduct", "/createResponse").permitAll().
//                .hasAnyRole("ADMIN").
                mvcMatchers("/", "/login", "/logout", "/showReg", "/registerUser").permitAll();
//                anyRequest().denyAll().and().logout().logoutSuccessUrl("/");

        http.csrf(csrfCustomizer -> {

            csrfCustomizer.ignoringAntMatchers("/productapi/products/**");
            RequestMatcher requestMatcher = new RegexRequestMatcher("/productapi/products/", "POST");
            requestMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(),"/getProduct");
            csrfCustomizer.ignoringRequestMatchers(requestMatcher);
        });

        http.cors(corsCustomizer -> {
            CorsConfigurationSource corsConfigurationSource = request ->{
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedMethod("*");
                configuration.addAllowedHeader("*");
                configuration.setAllowedOrigins(List.of("http://localhost:3000"));
                return configuration;
            };
            corsCustomizer.configurationSource(corsConfigurationSource);
        });
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
