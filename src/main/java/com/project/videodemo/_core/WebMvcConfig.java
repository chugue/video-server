package com.project.videodemo._core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/videolocation/**")
                .addResourceLocations("file:./videolocation/")
                .setCachePeriod(60 * 60)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler("/static/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(60 * 60)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/upload")
                .allowedOrigins("http://localhost:7000", "http://localhost:8080")
                .allowedMethods("POST")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/videos")
                .allowedOrigins("http://localhost:7000", "http://localhost:8080")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/videolocation/**")
                .allowedOrigins("http://localhost:7000", "http://localhost:8080")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

};