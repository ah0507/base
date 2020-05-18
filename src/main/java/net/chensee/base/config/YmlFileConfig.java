package net.chensee.base.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class YmlFileConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer yaml() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("sysExceptionConfig.yml"),new ClassPathResource("customExceptionConfig.yml"),new ClassPathResource("fileConfig.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
