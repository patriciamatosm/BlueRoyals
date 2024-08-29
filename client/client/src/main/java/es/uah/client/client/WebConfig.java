package es.uah.client.client;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.slf4j.Logger;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("Configurando ResourceHandler para uploads");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/Users/matos/Desktop/TFM/client/client/uploads/")
                .setCachePeriod(0);
    }
}
