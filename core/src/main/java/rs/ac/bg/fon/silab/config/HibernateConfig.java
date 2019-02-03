package rs.ac.bg.fon.silab.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:database.properties")
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initial.pool.size"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.min.pool.size"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.max.pool.size"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.max.idle.time"));

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
        }};
    }

    private Integer getIntProperty(String propertyName) {
        return Integer.parseInt(env.getProperty(propertyName));
    }

}
