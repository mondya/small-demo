package com.xhh.smalldemobackend.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 如果主启动类配配置了mapperScan，需要删除启动类中的mapperScan
 */
//@Configuration
//@MapperScan(basePackages = {"com.xhh.smalldemo.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class PrimaryDataSourceConfig {
    
    @Resource
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    /**
     * 创建主数据源
     * ConfigurationProperties：使用spring.datasource.main开头的配置
     * Primary：声明主数据源（默认数据源），多数据源配置时必不可少
     * Qualifier:显示声明选择传入的bean
     * @return
     */
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.main")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 创建主数据源的sqlSessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Primary
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/main/*.xml"));
        // 分页插件需要设置
        sqlSessionFactoryBean.setPlugins(mybatisPlusInterceptor);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务管理器
     * @param dataSource
     * @return
     */
    @Primary
    @Bean(name = "dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
