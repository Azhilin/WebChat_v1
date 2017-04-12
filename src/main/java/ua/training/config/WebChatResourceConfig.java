package ua.training.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * Created by Adrien on 23.02.2017.
 */
@Configuration
@PropertySource("classpath:reg.properties")
public class WebChatResourceConfig {
    @Bean(name = "messageSource")
    public MessageSource getMessageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("db", "reg");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("en"));
        cookieLocaleResolver.setCookieMaxAge(10_000);
        return cookieLocaleResolver;
    }
}
