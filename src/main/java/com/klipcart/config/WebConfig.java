package com.klipcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "uploads" ஃபோல்டரில் உள்ள படங்களை பிரவுசரில் பார்க்க அனுமதி வழங்குகிறது
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}