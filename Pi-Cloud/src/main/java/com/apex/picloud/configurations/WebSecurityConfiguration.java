package com.apex.picloud.configurations;

import com.apex.picloud.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

@Autowired
private JwtRequestFilter requestFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
         http.cors().and().csrf().disable()
                .authorizeHttpRequests()

                .requestMatchers("/api/marketplace/products/addProduct", "/api/marketplace/products/getProductById/{id}", "/api/marketplace/products/getAllProducts", "/api/marketplace/products/updateProduct/{id}", "/api/marketplace/products/deleteProduct/{id}","/api/marketplace/categories/getAllCategories", "/api/marketplace/categories/getCategoryById/{id}","/api/marketplace/categories/addCategory" ,
                        "/api/marketplace/categories/deleteCategory/{id}","/api/cart-items"  , "/api/cart-items/{id}" , "/api/orders", "/api/orders/{id}" , "/api/customers", "/api/cart/add-item",
                        "/**","user/**","user/verify-account/**","user/current-user",
                        "/register",
                        "/authentication",
                        "/forgot-password",
                        "/set-password",
                        "/ws/**",
                        "/ws/info",
                        "/messages/**",
                        // resources for swagger to work properly
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/webjars/**",
                        "/swagger-ui/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/roletest/hello","/forgot-password").hasRole("USER").and()
                .authorizeHttpRequests().requestMatchers("/api/**", "/api/marketplace/products", "/api/marketplace/categories", "/api/cart-items","/api/orders", "/api/customers", "/api/cart" )
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
         //houni staamlna token
                ;
        return http.httpBasic().and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
