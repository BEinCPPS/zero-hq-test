package it.eng.zerohqt.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import it.eng.zerohqt.dao.mapper.TablesMetaDataMapper;
import it.eng.zerohqt.dao.mapper.TestStationDataMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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
    public javax.sql.DataSource getDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(jdbcUrl);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        SqlSessionFactory factory = sqlSessionFactoryBean.getObject();
        factory.getConfiguration().addMapper(TablesMetaDataMapper.class);
        factory.getConfiguration().addMapper(TestStationDataMapper.class);
        //TODO Add other mappers
        return factory;
    }
    @Bean
    public TablesMetaDataMapper tablesMetaDataMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(TablesMetaDataMapper.class);
    }

    @Bean
    public TestStationDataMapper testStationDataMapper() throws Exception {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sessionTemplate.getMapper(TestStationDataMapper.class);
    }

    //Add other mappers HERE

}
