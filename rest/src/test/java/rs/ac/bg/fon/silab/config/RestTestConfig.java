package rs.ac.bg.fon.silab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = { "rs.ac.bg.fon.silab.controller", "rs.ac.bg.fon.silab.exception.handler",
        "rs.ac.bg.fon.silab.response.builder" })
@EnableWebMvc
@Import(CoreTestConfig.class)
public class RestTestConfig {}
