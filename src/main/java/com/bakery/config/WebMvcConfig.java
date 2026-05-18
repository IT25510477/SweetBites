package com.bakery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

/**
 * Configures Spring MVC to serve uploaded product images from the
 * persistent uploads/ directory located in the project root.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve uploaded images at /uploads/**
        // Pointing to the "uploads" directory in the project root.
        String absolutePath = Paths.get("uploads").toAbsolutePath().toString();
        
        // Ensure the path ends with a separator and starts with file:
        // On Windows, this will be file:C:/path/to/uploads/
        if (!absolutePath.endsWith(File.separator)) {
            absolutePath += File.separator;
        }
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + absolutePath.replace("\\", "/"));
    }
}
