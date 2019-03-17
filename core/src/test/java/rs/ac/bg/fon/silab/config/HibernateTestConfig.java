package rs.ac.bg.fon.silab.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:database.properties")
public class HibernateTestConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername("jdbc.user");
        dataSource.setPassword("jdbc.password");

        return dataSource;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        return new LocalSessionFactoryBean() {{
            setDataSource(dataSource);
            setPackagesToScan(env.getProperty("hibernate.packages.to.scan"));
            setHibernateProperties(hibernateProperties);
        }};
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager() {{
            setSessionFactory(sessionFactory);
        }};
    }

    @Bean
    public Properties hibernateProperties() {
        return new Properties() {{
            setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
            setProperty("hibernate.show_sql", env.getProperty("hibernate.show.sql"));
            setProperty("hibernate.format_sql", env.getProperty("hibernate.format.sql"));
            setProperty("hibernate.connection.CharSet", env.getProperty("hibernate.connection.charset"));
            setProperty("hibernate.connection.characterEncoding", env.getProperty("hibernate.connection.charset"));
            setProperty("hibernate.connection.useUnicode", env.getProperty("hibernate.connection.use.unicode"));
            setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        }};
    }

}
