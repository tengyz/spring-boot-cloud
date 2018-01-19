package cn.zhangxd.svcb;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import com.alibaba.druid.pool.DruidDataSource;
 
 

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
@EnableOAuth2Client
public class ServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBApplication.class, args);
    }
    
    
    @Bean(name = "beetlSqlScannerConfigurer")
    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer(@Qualifier("sqlManagerFactoryBean") SqlManagerFactoryBean fb) {
    	System.out.println("===========================getBeetlSqlScannerConfigurer=============================");
    	BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
        conf.setBasePackage("cn.zhangxd.svcb.dao");
        conf.setDaoSuffix("Dao");
        conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");

        SQLManager sql;
        try {
            sql = (SQLManager) fb.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
 
        return conf;
    }

    @Bean(name = "sqlManagerFactoryBean")
    @Primary
    public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
    	System.out.println("===========================getSqlManagerFactoryBean=============================");
        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();

        BeetlSqlDataSource source = new BeetlSqlDataSource();
        //主数据库
        source.setMasterSource(datasource);
//        //从数据库。从数据库是多个
//        DataSource datasources[]=null;
//        datasources[0]=datasource;
//        datasources[1]=datasource;
//        datasources[2]=datasource;
//        source.setSlaves(datasources);
        factory.setCs(source);
        factory.setDbStyle(new MySqlStyle());
        factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
        factory.setNc(new UnderlinedNameConversion());
        factory.setSqlLoader(new ClasspathLoader("/sql"));

        return factory;
    }


    @Bean(name = "datasource")
	public DataSource druidDataSource(Environment env) {
    	 System.out.println("===========================druidDataSource=============================");
		DruidDataSource druidDataSource = new DruidDataSource();
		
		druidDataSource.setDriverClassName(env.getProperty("bbs.driver"));
		druidDataSource.setUrl(env.getProperty("bbs.dbUrl"));
		druidDataSource.setUsername(env.getProperty("bbs.dbUserName"));
		druidDataSource.setPassword(env.getProperty("bbs.dbPassowrd"));
		druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
		druidDataSource.setInitialSize(5);
		druidDataSource.setMaxActive(10);
		return druidDataSource;
	}
}