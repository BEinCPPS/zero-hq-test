package it.eng.zerohqt.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by ascatox on 13/02/17.
 */
@Configuration
@MapperScan("it.eng.zerohqt.dao.mapper")
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName:com.mysql.jdbc.Driver}")
    private String driverClassName;
    @Value("${datasource.initialSize:20}")
    private int initialSize;
    @Value("${datasource.maxActive:30}")
    private int maxActive;
    @Value("${datasource.minIdle:20}")
    private int minIdle;
    @Value("${datasource.transactionTimeoutS:30}")
    private int transactionTimeoutS;


    @Bean
    public javax.sql.DataSource dataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(jdbcUrl);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        //PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

}
