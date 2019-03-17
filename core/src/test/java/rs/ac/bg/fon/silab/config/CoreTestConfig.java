package rs.ac.bg.fon.silab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

@Configuration
@ComponentScan(basePackages = { "rs.ac.bg.fon.silab.service", "rs.ac.bg.fon.silab.dao",
        "rs.ac.bg.fon.silab.mapper", "rs.ac.bg.fon.silab.validator" })
@Import(HibernateTestConfig.class)
public class CoreTestConfig {

    @Bean
    @Autowired
    public Validator validator(MessageSource messageSource) {
        return new LocalValidatorFactoryBean() {{
            setValidationMessageSource(messageSource);
        }};
    }

    @Bean
    public MessageSource messageSource() {
        return new ResourceBundleMessageSource() {{
            setBasename("messages");
            setDefaultEncoding("UTF-8");
        }};
    }

}
