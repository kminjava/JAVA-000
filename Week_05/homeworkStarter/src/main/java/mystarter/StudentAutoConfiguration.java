package mystarter;

import mystarter.aop.SchoolProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass(School.class)
@EnableConfigurationProperties(SchoolProperties.class)
public class StudentAutoConfiguration {

    /**
     * 注入属性配置类
     */
    @Resource
    private SchoolProperties schoolProperties;

    @Bean
    @ConditionalOnMissingBean(School.class)
    @ConditionalOnProperty(prefix = "student" ,value = "enabled",havingValue = "true")
    public School  school(){
        School school = new School();

        return school;
    }
}
