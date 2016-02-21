package vod.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Spring Beans of GUI
 */
@Configuration
@EnableAspectJAutoProxy
public class SpringBeanConfig {
    @Bean
    public JFrame jFrame() {
        return new JFrame();
    }
}
