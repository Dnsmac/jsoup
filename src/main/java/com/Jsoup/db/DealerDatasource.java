package com.Jsoup.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = DealerDatasource.PACKAGE, sqlSessionTemplateRef  = "dealer")
public class DealerDatasource {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.Jsoup.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";
    
    
    @Bean(name = "dealerBeanDatasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource textDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dealerSqlSessionFactory")
    @Primary
    public SqlSessionFactory textSessionFactory(@Qualifier("dealerBeanDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DealerDatasource.MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "dealerTransactionManager")
    @Primary
    public DataSourceTransactionManager textTransactionManager(@Qualifier("dealerBeanDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dealer")
    @Primary
    public SqlSessionTemplate textSessionTemplat(@Qualifier("dealerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
      

    
}
