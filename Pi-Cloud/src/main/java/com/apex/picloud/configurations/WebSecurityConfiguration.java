package com.apex.picloud.configurations;

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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/register","/authentication","api/forums/getAllForums","api/forums/addForum","/api/forums/getForumById/{id}","/api/forums/deleteForum/{id}","/api/forums/updateForum/{id}","/api/forums/topics/posts/addPost","api/forums/topics/posts/deletePost/","/api/forums/topics/posts/getAllPosts","/api/forums/topics/posts/getPostById/{id}","/api/forums/topics/posts/updatePost/","/api/forums/topics/addTopic","/api/forums/topics/deleteTopic/{id}","/api/forums/topics/getAllTopics","/api/forums/topics/getTopicById/{id}","/api/forums/topics/updateTopic/{id}").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/**","api/forums/","/api/forum/posts/")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
