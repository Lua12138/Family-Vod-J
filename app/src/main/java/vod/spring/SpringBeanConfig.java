package vod.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by forDream on 2015-12-27.
 */
@Configuration
@EnableAspectJAutoProxy
public class SpringBeanConfig {
    @Bean
    public JFrame jFrame() {
        return new JFrame();
    }

    @Bean
    public ResourceBundle resourceBundle() {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
        return bundle;
    }
}
