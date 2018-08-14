package com.gxyj.test.conf;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@Configuration
@EnableTransactionManagement
public class MybatisDbConfig {	
	
	@Autowired
    @Qualifier("writeDataSource")
    private DataSource writeDataSource;
	
	
	@Autowired
    @Qualifier("readDataSource")
    private DataSource readDataSource;
	
	
	@Bean
    public SqlSessionFactory writeSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(writeDataSource);
        factoryBean.setTypeAliasesPackage("com.gxyj.test.entity");
        
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        factoryBean.setPlugins(new Interceptor[]{pageHelper});      
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return factoryBean.getObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
	
	@Bean
    public SqlSessionTemplate writeSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(writeSqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }
	
	@Bean
    public SqlSessionFactory readSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(readDataSource);
        factoryBean.setTypeAliasesPackage("com.gxyj.test.entity");        
        
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        
        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(properties);

        //添加插件
        factoryBean.setPlugins(new Interceptor[]{pageHelper});      
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return factoryBean.getObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
	
	@Bean
    public SqlSessionTemplate readSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(readSqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }
	
    @Bean(name = "writeTransactionManager")
    public DataSourceTransactionManager writeTransactionManager(@Qualifier("writeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
	
	
}
